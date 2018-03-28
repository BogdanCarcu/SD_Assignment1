package bll;

import java.util.List;

import bll.model.ShowModel;
import bll.model.TicketModel;
import bll.service.IShowService;
import bll.service.ITicketService;
import bll.service.ShowService;
import bll.service.TicketService;

public class Cashier {

	private final IShowService showService;
	private final ITicketService ticketService;
	
	public Cashier() {
		
		showService = new ShowService();
		ticketService = new TicketService();
		
	}
	
	private boolean[][] coordinate(int id) {
		
		ShowModel show = showService.findById(id);
		String title = show.getTitle();
		
		List<TicketModel> tickets = ticketService.ticketsForShow(title);
		boolean[][] hall = new boolean[30][30];
		
		for(TicketModel t: tickets) {
			
			int row = t.getSeat_row();
			int seat = t.getSeat_number();
			
			hall[row][seat] = true;
		}
		
		return hall;
		
	}
	
	public void sell(int id, int row, int seat, float price) throws IllegalArgumentException {
		
		//Find show
		ShowModel show = showService.findById(id);
		
		//check is seat is occupied
		boolean[][] positions = coordinate(id);
		
		if(row > 30 || seat > 30 || seat < 0 || row < 0)
			throw new IllegalArgumentException("Non-existent seat!");
		
		if (positions[row][seat])
			throw new IllegalArgumentException("Unavailable seat!");
		
		// get prev number of tickets and decrement it
		int ticketsNumber = show.getNumber_of_tickets();
		ticketsNumber--;
		
		// create new show with same characteristics but with decremented ticket no.
		ShowModel newShow = new ShowModel();
		newShow.setDate(show.getDate());
		newShow.setDistribution(show.getDistribution());
		newShow.setGenre(show.getGenre());
		newShow.setNumber_of_tickets(ticketsNumber);
		newShow.setTitle(show.getTitle());
		showService.update(id, newShow);
		
		// "sell" ticket = create it 
		TicketModel ticket = new TicketModel();
		ticket.setPrice(price);
		ticket.setSeat_number(seat);
		ticket.setSeat_row(row);
		ticket.setShow_id(id);
		
		ticketService.insert(ticket);
		
	}
	
	public boolean notifyCashier(int id) {
		
		ShowModel show = showService.findById(id);
		int showNumberOfTickets = show.getNumber_of_tickets();
		
		if (showNumberOfTickets > 0)
			return true;
		
		return false;
		
	}
	
	public List<TicketModel> seeAllTicketsForShow(int id) {
		
		ShowModel show = showService.findById(id);
		String title = show.getTitle();
		
		List<TicketModel> tickets = ticketService.ticketsForShow(title);
		return tickets;
		
	}
	
	public void cancelReservation(int showId, int ticketId) {
		
				//Find show
				ShowModel show = showService.findById(showId);
				
				ticketService.delete(ticketId);
				
				// get prev number of tickets and decrement it
				int ticketsNumber = show.getNumber_of_tickets();
				ticketsNumber++;
				
				// create new show with same characteristics but with decremented ticket no.
				ShowModel newShow = new ShowModel();
				newShow.setDate(show.getDate());
				newShow.setDistribution(show.getDistribution());
				newShow.setGenre(show.getGenre());
				newShow.setNumber_of_tickets(ticketsNumber);
				newShow.setTitle(show.getTitle());
				showService.update(showId, newShow);		
		
	}
	
	public void editSeat(int id, int row, int seat) throws IllegalArgumentException {
		
		TicketModel oldTicket = ticketService.findById(id);
		TicketModel newTicket = new TicketModel();
		
		//check is seat is occupied
		boolean[][] positions = coordinate(oldTicket.getShow_id());
				
		if(row > 30 || seat > 30 || seat < 0 || row < 0)
			throw new IllegalArgumentException("Non-existent seat!");
		
		if (positions[row][seat])
			throw new IllegalArgumentException("The seat you want to modify to is already taken!");
		
		newTicket.setPrice(oldTicket.getPrice());
		newTicket.setSeat_number(seat);
		newTicket.setSeat_row(row);
		newTicket.setShow_id(oldTicket.getShow_id());
		
		ticketService.update(id, newTicket);
		
	}
	
}
