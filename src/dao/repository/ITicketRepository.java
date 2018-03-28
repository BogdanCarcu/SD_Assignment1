package dao.repository;

import java.util.List;

import dao.dbmodel.TicketDto;

public interface ITicketRepository {

	public TicketDto findById(int id);
	
	public List<TicketDto> findAll();
	
	public void insert(TicketDto ticket);
	
	public boolean delete(int id);
	
	public void update(int id, TicketDto ticket);
	
	public List<TicketDto> ticketsForShow(String title);
	
}
