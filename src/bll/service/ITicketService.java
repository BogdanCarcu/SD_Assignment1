package bll.service;

import java.util.List;

import bll.model.TicketModel;

public interface ITicketService {
	
	public TicketModel findById(int id);
	
	public List<TicketModel> findAll();
	
	public void insert(TicketModel m);
	
	public boolean delete(int id);
	
	public void update(int id, TicketModel m);
	
	public List<TicketModel> ticketsForShow(String title);

}
