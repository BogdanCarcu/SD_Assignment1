package bll.exporter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import bll.model.TicketModel;

public class CsvWriter implements WriterInterface{
	
	//Delimiter used in CSV file
	
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	//CSV file header
	private static final String FILE_HEADER = "ticket_id,show_id,seat_row,seat_number,price";

	public void write(List<TicketModel> tickets, String path) {
		
		FileWriter fileWriter = null;
		             
		try {
	
		      fileWriter = new FileWriter(path);
	
		      //Write the CSV file header
		      fileWriter.append(FILE_HEADER.toString());
		
		      //Add a new line separator after the header
		      fileWriter.append(NEW_LINE_SEPARATOR);
		
		      //Write a new student object list to the CSV file
		
		            for (TicketModel ticket : tickets) {
		
		                fileWriter.append(String.valueOf(ticket.getTicket_id()));
		                fileWriter.append(COMMA_DELIMITER);
		                fileWriter.append(String.valueOf(ticket.getShow_id()));
		                fileWriter.append(COMMA_DELIMITER);
		                fileWriter.append(String.valueOf(ticket.getSeat_row()));
		                fileWriter.append(COMMA_DELIMITER);
		                fileWriter.append(String.valueOf(ticket.getSeat_number()));
		                fileWriter.append(COMMA_DELIMITER);
		                fileWriter.append(String.valueOf(ticket.getPrice()));
		                fileWriter.append(NEW_LINE_SEPARATOR);
		            }           
	
		            System.out.println("CSV file was created successfully !!!");
		             
		        } catch (Exception e) {
		
		            System.out.println("Error in CsvWriter !!!");
		            e.printStackTrace();
		        } finally {
	     	
		            try {
		                fileWriter.flush();
		                fileWriter.close();
	
		            } catch (IOException e) {
		                System.out.println("Error while flushing/closing fileWriter !!!");
		                e.printStackTrace();
		            }    
		        }
		    }
		}
	
