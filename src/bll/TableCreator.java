package bll;

import java.lang.reflect.Field;
import java.util.List;
import javax.swing.JTable;
/**
 * 
 * Utility class that contains only one static method.
 * @author Carcu Bogdan
 * @since May 2017
 *
 */
public class TableCreator {
	
	private TableCreator() {
		
	}
	
	/**
	 * @param objects A list of objects whose properties must be extracted and converted into a Swing JTable through reflection
	 * @return A JTable containing all passed information
	 */
	public static <T> JTable createTable(List<T> objects) {
		
		if(objects.size() == 0)
			return null;
		
		JTable result;	
		String[] headers = new String[objects.get(0).getClass().getDeclaredFields().length];
		Object[][] map = new Object[objects.size()][objects.get(0).getClass().getDeclaredFields().length];
		int i = 0, j = 0;
		
		for(int c = 0; c < headers.length; ++c) 
			headers[c] = objects.get(0).getClass().getDeclaredFields()[c].getName();
		
		for(Object o : objects) {
			for(Field field : o.getClass().getDeclaredFields()) {
				
				field.setAccessible(true);
				Object value;
				
				try{
					value = field.get(o);
					map[i][j++] = value;
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}	
			i++;
			j = 0;
		}
				
		result = new JTable(map, headers);
		return result;	
	}
	

}