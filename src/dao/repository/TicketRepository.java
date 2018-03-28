package dao.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import dao.ConnectionFactory;
import dao.dbmodel.TicketDto;

public class TicketRepository extends GenericRepository<TicketDto> implements ITicketRepository{

	public List<TicketDto> ticketsForShow(String title) {
		
		
		Connection connection = null;
		PreparedStatement findAllStatement = null;
		ResultSet resultSet = null;
		String query;
		
		query = "SELECT * FROM `ticket` JOIN `show` ON `ticket`.show_id = `show`.show_id WHERE title = ?";
		
		try {
			
			connection = ConnectionFactory.getConnection();
			findAllStatement = connection.prepareStatement(query);
			findAllStatement.setString(1, title);
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
	
	
}
