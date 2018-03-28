package bll.exporter;

import java.util.List;

import bll.model.TicketModel;

public interface WriterInterface {

	public void write(List<TicketModel> tickets, String path);
	
}
