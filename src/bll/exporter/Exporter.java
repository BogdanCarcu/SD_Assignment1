package bll.exporter;

import java.util.List;

import bll.model.TicketModel;


public abstract class Exporter {
	
	private WriterInterface writer;
	
	public Exporter() {
		
		writer = createWriter();
		
	}
	
	abstract protected WriterInterface createWriter();
	
	public void export(List<TicketModel> tickets, String path) {
		
		writer.write(tickets, path);
		
	}
}
