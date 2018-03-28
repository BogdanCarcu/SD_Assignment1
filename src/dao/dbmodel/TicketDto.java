package dao.dbmodel;

public class TicketDto {
	
	private int ticket_id;
	private int show_id;
	private int seat_row;
	private int seat_number;
	private float price;
	
	public TicketDto() {}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}


	public int getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}


	public int getShow_id() {
		return show_id;
	}


	public void setShow_id(int show_id) {
		this.show_id = show_id;
	}


	public int getSeat_row() {
		return seat_row;
	}


	public void setSeat_row(int seat_row) {
		this.seat_row = seat_row;
	}


	public int getSeat_number() {
		return seat_number;
	}


	public void setSeat_number(int seat_number) {
		this.seat_number = seat_number;
	}
	
}
