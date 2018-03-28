package bll.exporter;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import bll.model.TicketModel;

public class XmlWriter implements WriterInterface{

	@Override
	public void write(List<TicketModel> tickets, String path) {
		
		 try {
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.newDocument();
	         
	         // root element
	         Element rootElement = doc.createElement("tickets");
	         doc.appendChild(rootElement);
	         
	         for(TicketModel t: tickets) {
	        	  
		         Element ticket = doc.createElement("ticket");
		         rootElement.appendChild(ticket);
		         
		         Attr attr = doc.createAttribute("ticket_id");
		         attr.setValue(String.valueOf(t.getTicket_id()));
		         ticket.setAttributeNode(attr);
		        
		         Element showId = doc.createElement("show_id");
		 		 showId.appendChild(doc.createTextNode(String.valueOf(t.getShow_id())));
		 		 ticket.appendChild(showId);

		 	   	 Element seatRow = doc.createElement("seat_row");
				 seatRow.appendChild(doc.createTextNode(String.valueOf(t.getSeat_row())));
				 ticket.appendChild(seatRow);
				 
				 Element seatNumber = doc.createElement("seat_number");
				 seatNumber.appendChild(doc.createTextNode(String.valueOf(t.getSeat_number())));
				 ticket.appendChild(seatNumber);
				 
				 Element price = doc.createElement("price");
				 price.appendChild(doc.createTextNode(String.valueOf(t.getPrice())));
				 ticket.appendChild(price);
	        	 
	         }
	         
	         // write the content into xml file
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         DOMSource source = new DOMSource(doc);
	         StreamResult result = new StreamResult(new File(path));
	         transformer.transform(source, result);
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
			

}
