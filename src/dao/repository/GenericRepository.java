package dao.repository;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.ConnectionFactory;
/**
 * Generic class that uses reflection and query-specific methods
 * in order to implement CRUD operations.
 * 
 * @author Carcu Bogdan
 * @since May 2017
 * 
 */
public abstract class GenericRepository<T> {
	
	private final Class<T> type;
	
	@SuppressWarnings("unchecked")
	public GenericRepository() {
		
		this.type = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		
	}

	/**
	 * @return An array of objects representing the fields of the input's class
	 */
	private Object[] retrieveProperties(Object object) {

		Object[] result = new Object[object.getClass().getDeclaredFields().length];
		int c = 0;
		
		for (Field field : object.getClass().getDeclaredFields()) {
			field.setAccessible(true); // set modifier to public
			Object value;
			try {
				value = field.get(object);
				result[c++] = value;

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		return result;
	}
	
	//Creation of different queries
	private String createSelectQuery(String field) {
		
		return "SELECT * FROM `" + type.getSimpleName().replace("Dto", "`") + " WHERE " + field + " = ?";
	}
	
	private String createInsertQuery() {
		
		String result = "INSERT INTO `" + type.getSimpleName().replace("Dto", "`") + " (";
		
		for(int i = 1; i < type.getDeclaredFields().length; ++i) {
			result += type.getDeclaredFields()[i].getName();
		
				if(i != type.getDeclaredFields().length - 1)
					result += ", ";
		}
		
		result += ") VALUES(";
		for(int i = 1; i < type.getDeclaredFields().length - 1; ++i)
			result += "?,";
		
		result += "?)";
	
		return result;
	}
	
	private String createDeleteQuery(String field) {
		
		return "DELETE FROM `" + type.getSimpleName().replace("Dto", "`") + " WHERE " + field + " = ?";
	}
	
	private String createUpdateQuery(String field, T item) {
		
		String result = "UPDATE `" + type.getSimpleName().replace("Dto", "`") + " SET ";
		
		for(int i = 1; i < type.getDeclaredFields().length; ++i) {
			result += type.getDeclaredFields()[i].getName();	
			result += " = ";
			result += "'" + retrieveProperties(item)[i] + "'";
			if(i != type.getDeclaredFields().length - 1)
				result += ", ";
		}
		result += " WHERE " + field + " = ?";
	
		return result;
	}
	
	
	protected List<T> createObjects(ResultSet resultSet) {
		
		List<T> list = new ArrayList<T>();
		
		try{
				while(resultSet.next()) {
				
				T instance = type.newInstance();
				Object val;
				for(Field field : type.getDeclaredFields()) {
					
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method m = propertyDescriptor.getWriteMethod();
			
					if(!field.getName().equals("is_admin"))
						val = resultSet.getObject(field.getName());
					else
						val = resultSet.getByte((field.getName()));
					
					m.invoke(instance, val);	
							
				}
				list.add(instance);
			}		
				return list;
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	

	public T findById(int id) {
		
		Connection connection = null;
		PreparedStatement findStatement = null;
		ResultSet resultSet = null;
		String query;
		
		String idField = type.getDeclaredFields()[0].getName();
		
		query = createSelectQuery(idField);
		
		try {
			
			connection = ConnectionFactory.getConnection();
			findStatement = connection.prepareStatement(query);
			findStatement.setInt(1, id);
			resultSet = findStatement.executeQuery();			
			return createObjects(resultSet).get(0);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(connection);
			
		}
		return null;
	}
	

	public List<T> findAll() {
		
		Connection connection = null;
		PreparedStatement findAllStatement = null;
		ResultSet resultSet = null;
		String query;
		
		query = "SELECT * FROM `" + type.getSimpleName().replace("Dto", "`");
		
		try {
			
			connection = ConnectionFactory.getConnection();
			findAllStatement = connection.prepareStatement(query);
			resultSet = findAllStatement.executeQuery();
			
			return createObjects(resultSet);
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(findAllStatement);
			ConnectionFactory.close(connection);
			
		}
		return null;
	}

	public void insert(T type) {
		
		Connection connection = null;
		PreparedStatement insertStatement = null;
		
		try {
			
			connection = ConnectionFactory.getConnection();
			insertStatement = connection.prepareStatement(createInsertQuery());
			Field[] fs = type.getClass().getDeclaredFields();
			
			for(int i = 1; i < fs.length; ++i) {
				
				fs[i].setAccessible(true);
				insertStatement.setString(i , fs[i].get(type).toString());
			}
			
			insertStatement.executeUpdate();	
				
		} catch(Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(connection);	
		}
	}

	public boolean delete(int id) {
		
		Connection connection = null;
		PreparedStatement deleteStatement = null;
		String query;
		String idField = type.getDeclaredFields()[0].getName();
		query = createDeleteQuery(idField);
		
		try {
			
			connection = ConnectionFactory.getConnection();
			deleteStatement = connection.prepareStatement(query);
			deleteStatement.setInt(1, id);
			deleteStatement.executeUpdate();
			return true;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(connection);
			
		}
		return false;
	}
	
	public void update(int id, T item) {
		
		Connection connection = null;
		PreparedStatement updateStatement = null;
		String idField = type.getDeclaredFields()[0].getName();
		String query;
		
		query = createUpdateQuery(idField, item);
		
		try {
		
			connection = ConnectionFactory.getConnection();
			updateStatement = connection.prepareStatement(query);
			updateStatement.setString(1, id + "");
			updateStatement.executeUpdate();	
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(connection);
		}
	}
	
}