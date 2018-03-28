package bll;

import java.util.List;

import bll.exporter.CsvExporter;
import bll.exporter.Exporter;
import bll.exporter.XmlExporter;
import bll.model.ShowModel;
import bll.model.TicketModel;
import bll.model.UserModel;
import bll.service.IShowService;
import bll.service.ITicketService;
import bll.service.IUserService;
import bll.service.ShowService;
import bll.service.TicketService;
import bll.service.UserService;

public class Admin {

	private final IUserService userService;
	private final IShowService showService;
	private final ITicketService ticketService;
	
	public Admin() {
		
		userService = new UserService();
		showService = new ShowService();
		ticketService = new TicketService();
	}
	
	public UserModel findCashier(int id) {
		
		return userService.findById(id);
		
	}
	
	public boolean deleteCashier(int id) {
		
		return userService.delete(id);
		
	}
	
	public void insertCashier(UserModel m) {
		
		userService.insert(m);
		
	}
	
	public void updateCashier(int id, UserModel m) {
		
		userService.update(id, m);
		
	}
	
	public List<UserModel> findAllCashiers() {
		
		return userService.findAll();
		
	}
	
	public ShowModel findShow(int id) {
		
		return showService.findById(id);
		
	}
	
	public boolean deleteShow(int id) {
		
		return showService.delete(id);
		
	}
	
	public void insertShow(ShowModel m) {
		
		showService.insert(m);
		
	}
	
	public void updateShow(int id, ShowModel m) {
		
		showService.update(id, m);
		
	}
	
	public List<ShowModel> findAllShows() {
		
		return showService.findAll();
		
	}
	
	public void export(String title, String path, String format) {
		
		List<TicketModel> tickets = ticketService.ticketsForShow(title);
		Exporter exporter;
		
		if(format.equals("csv")) 
			exporter = new CsvExporter();
		else
			exporter = new XmlExporter();
		
		exporter.export(tickets, path);
		
	}
	
	
}
