package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import bll.Cashier;
import bll.TableCreator;

@SuppressWarnings("serial")
public class CashierView extends JFrame{

	private Cashier cashier;
	
	private JTextField id, row, number, price, ticket_id;
	private JTable tickets;
	private JButton sell, see, cancel, edit;
	private JPanel pFinal, pTable, p1, p2, p3, p_id;
	private JScrollPane pane;
	
	public CashierView() {
		
		super("Cashier");
		cashier = new Cashier();
		
		id = new JTextField("Show Id", 10);
		row = new JTextField("Row", 10);
		number = new JTextField("Number", 10);
		price = new JTextField("Price", 10);
		ticket_id = new JTextField("Ticket Id", 10);
		
		tickets = new JTable();
		pane = new JScrollPane(tickets);
		
		sell = new JButton("Sell");
		see = new JButton("See tickets for show");
		cancel = new JButton("Cancel reservation");
		edit = new JButton("Edit reservation");
		
		p1 = new JPanel(); p2 = new JPanel(); p3 = new JPanel(); pTable = new JPanel(); p_id = new JPanel();
		pFinal = new JPanel();
		pFinal.setLayout(new BoxLayout(pFinal, BoxLayout.PAGE_AXIS));
		
		addFunctionality();
		
		pTable.add(pane);
		p1.add(row); p1.add(number); p1.add(price); 
		p_id.add(id); p_id.add(ticket_id);
		p2.add(sell); p2.add(see);
		p3.add(cancel); p3.add(edit);
		
		pFinal.add(pTable); pFinal.add(p1); pFinal.add(p_id); pFinal.add(p2); pFinal.add(p3);
		add(pFinal);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650, 600);
		setResizable(false);
		setVisible(true);
		
	}
	
	private void addFunctionality() {
		
		sell.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
				
					int showId = Integer.valueOf(id.getText());
					int rowNumb = Integer.valueOf(row.getText());
					int seatNumb = Integer.valueOf(number.getText());
					float priceN = Float.valueOf(price.getText());
				
					
					if(!cashier.notifyCashier(showId)) {
						
						sendErrorMsg("Number of tickets exceeded!");
						return;
					}
					
					cashier.sell(showId, rowNumb, seatNumb, priceN);
					sendMsg("Ticket sold!");
					
				} catch (IllegalArgumentException ae) {
					
					sendErrorMsg(ae.getMessage());
					
				} catch (Exception ex) {
					
					sendErrorMsg(ex.getMessage());
					ex.printStackTrace();
				}		
					
			}
				
		});
		
		see.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					int showId = Integer.valueOf(id.getText());
					
					tickets = TableCreator.createTable(cashier.seeAllTicketsForShow(showId));
					
					pTable.removeAll();

					pTable.add(new JScrollPane(tickets));
					
					pTable.revalidate();
					pTable.repaint();
					
							
				} catch (Exception ex) {
					
					sendErrorMsg("Unable to find tickets for this show!");
					
				}
					
				}
				
		});
		
		cancel.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					int showId = Integer.valueOf(id.getText());
					int ticketId = Integer.valueOf(ticket_id.getText());
					
					cashier.cancelReservation(showId, ticketId);
					sendMsg("Reservation cancelled!");
					
					
				}catch (Exception ex) {
					
					sendErrorMsg("Unable to cancel reservation");
					
				}
					
					
				}
				
		});
		
		edit.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
					try {
						
						
						int rowNumb = Integer.valueOf(row.getText());
						int seatNumb = Integer.valueOf(number.getText());
						int ticketId = Integer.valueOf(ticket_id.getText());
						
						cashier.editSeat(ticketId, rowNumb, seatNumb);
						sendMsg("Seat edited successfully!");
						
					} catch (IllegalArgumentException ae) {
						
						sendErrorMsg(ae.getMessage());
						
					} catch (Exception ex) {
						
						sendErrorMsg("Unable to edit seat ");
						
					}
					
				}
				
		});
		
	}
	
	private void sendErrorMsg(String msg){
		
		JOptionPane.showMessageDialog(null, msg, "Oops!", JOptionPane.ERROR_MESSAGE);
	}
	
	private void sendMsg(String msg){
		
		JOptionPane.showMessageDialog(null, msg, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
