package dao.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.ConnectionFactory;
import dao.dbmodel.UserDto;

public class UserRepository  extends GenericRepository<UserDto> implements IUserRepository{
	
	public UserDto findByName(String name) {
		
		Connection connection = null;
		PreparedStatement findStatement = null;
		ResultSet resultSet = null;
		String query;
		
		query = "SELECT * FROM `user` WHERE username = ?";
		
		try {
			
			connection = ConnectionFactory.getConnection();
			findStatement = connection.prepareStatement(query);
			findStatement.setString(1, name);
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
	
}
