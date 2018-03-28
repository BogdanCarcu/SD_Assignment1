package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bll.Admin;
import bll.PasswordEncryptor;
import bll.model.ShowModel;
import bll.model.UserModel;
import bll.TableCreator;

@SuppressWarnings("serial")
public class AdminView extends JFrame {

	private static final String[] options = {"Shows" , "Cashiers"};
	private static final String[] types = {"xml" , "csv"};
	
	private Admin admin;
	private JTable myTable;
	private JComboBox<String> crudOption, fileOption;
	private JPanel pFinal, pTable, p1, p3, p4, p5, p6, pDistrb, pFile;
	private JButton create, retrieve, update, delete, export;
	private JTextField id;
	private JTextField new_username, new_pass; 
	private JTextField title, genre, date, numberTickets; 
	private JTextArea distribution;
	
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	public AdminView() {
		
		super("Admin");
		admin = new Admin();
		
		List<ShowModel> shows = admin.findAllShows();
		myTable = TableCreator.createTable(shows);
		
		crudOption = new JComboBox(options);
		crudOption.setSelectedItem(0);
		
		fileOption = new JComboBox(types);
		fileOption.setSelectedItem(0);
		
		pTable = new JPanel();
		//pTable.add(myTable);
		
		create = new JButton("Insert");
		retrieve = new JButton("Select");
		update = new JButton("Update");
		delete = new JButton("Delete");
		export = new JButton("Export");

		id = new JTextField("Id", 5);
		id.setEditable(false);
		new_username = new JTextField("New Username", 20);
		new_pass = new JTextField("New Password", 20);
		
		title = new JTextField("Title", 50);
		distribution = new JTextArea("Distribution", 6, 20);
		genre = new JTextField("Genre ", 6);
		date = new JTextField("Date", 20);
		numberTickets = new JTextField("Number of tickets", 15);
		
		
		p1 = new JPanel(); p3 = new JPanel(); p4 = new JPanel(); p5 = new JPanel(); p6 = new JPanel();
		pFile = new JPanel();
		pDistrb = new JPanel();
		pFinal = new JPanel();
		pFinal.setLayout(new BoxLayout(pFinal, BoxLayout.PAGE_AXIS));
		p1.add(create); p1.add(retrieve);
		p1.add(update); p1.add(delete);
		p3.add(crudOption); 
		p3.add(id);
		pFile.add(fileOption); pFile.add(export);
		
		selection();
		addFunctionality();
	
	    pFinal.add(p1); 
	    pFinal.add(pTable); 
	    pFinal.add(p3);
		add(pFinal); 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650, 600);
		setResizable(false);
		setVisible(true);
	  
	}

	private void selection() {
		
		crudOption.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				id.setEditable(true);
				
				pFinal.remove(p4); pFinal.remove(pDistrb); pFinal.remove(p5); pFinal.remove(p6); 
				pFinal.remove(pTable);
				p4 = new JPanel(); p5 = new JPanel(); p6 = new JPanel(); pTable = new JPanel();
				
					if(crudOption.getSelectedIndex() == 0) {
						
						List<ShowModel> shows = admin.findAllShows();
						myTable = TableCreator.createTable(shows);
						pTable.add(new JScrollPane(myTable));
						
						p4.add(title); pDistrb.add(distribution); 
						p5.add(genre); p5.add(date); 
						p6.add(numberTickets); 
						
						pFinal.add(pTable);
						pFinal.add(p4); pFinal.add(pDistrb); pFinal.add(p5); pFinal.add(p6);
						pFinal.add(pFile);
						
					} else {
						
						List<UserModel> users = admin.findAllCashiers();
						
						int a = 0;
						for(UserModel user : users) {
							
							if (user.getIs_admin() == 0)
								a++;
							else
								break;
							
						}
						
						users.remove(a);
						
						myTable = TableCreator.createTable(users);
						pTable.add(new JScrollPane(myTable));
						
						p4.add(new_username);
						p5.add(new_pass);
						
					
						pFinal.add(pTable);
						pFinal.add(p4); pFinal.add(p5);
						
						pFinal.remove(pFile);
						
					}
					
					pFinal.revalidate();
					pFinal.repaint();
					
				}
				
		});
		
	}
	
	private void addFunctionality() {
		
		
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(crudOption.getSelectedIndex() == 1) {
					UserModel user = new UserModel();
					
					String gotPass = new_pass.getText();
					String gotUsername = new_username.getText();
					
					if(gotPass.equals("") || gotUsername.equals("")) {
						
						sendErrorMsg("Please fill up all fields for insertion");
						return;
						
					}
						
					try {
					
					user.setIs_admin((byte)0);
					user.setPassword(PasswordEncryptor.oneWayEncryption(gotPass));
					user.setUsername(gotUsername);
					admin.insertCashier(user);
					sendMsg("Cashier inserted!");
					
					} catch (Exception ex) {
						
						sendErrorMsg("Insertion Error!");
						return;
					}
					
					pTable.removeAll();

					List<UserModel> users = admin.findAllCashiers();
					
					int a = 0;
					for(UserModel u: users) {
						
						if (u.getIs_admin() == 0)
							a++;
						else
							break;
						
					}
					users.remove(a);
					
					pTable.removeAll();
					myTable = TableCreator.createTable(users);
				
					pTable.add(new JScrollPane(myTable));
					
					pTable.revalidate();
					pTable.repaint();
					
				} else {
					
					try {
					String showTitle = title.getText();
					String showDistribution = distribution.getText();
					String showGenre = genre.getText();
					Timestamp showDate = Timestamp.valueOf(date.getText());
					int ticketsNumber = Integer.valueOf(numberTickets.getText());
					
					if(showTitle.equals("") || showDistribution.equals("") 
							|| showGenre.equals("") || date.getText().equals("") 
							|| numberTickets.getText().equals("")) {
						
						sendErrorMsg("Please fill up all fields for insertion");
						return;
						
					}
					
					if(!showGenre.toLowerCase().equals("opera") && !showGenre.toLowerCase().equals("ballet")) {
						
						sendErrorMsg("Please select appropiate genre (opera/ballet)");
						return;
					}
		
					ShowModel show = new ShowModel();
					show.setDate(showDate);
					show.setDistribution(showDistribution);
					show.setGenre(showGenre);
					show.setNumber_of_tickets(ticketsNumber);
					show.setTitle(showTitle);
					admin.insertShow(show);
					sendMsg("Show inserted!");
					
					} catch(Exception ex) {
					
						sendErrorMsg("Insertion Error!");
						return;
					}
					
					pTable.removeAll();

					List<ShowModel> shows = admin.findAllShows();
					myTable = TableCreator.createTable(shows);
					
					pTable.add(new JScrollPane(myTable));
					pFinal.revalidate();
					pFinal.repaint();
					
					pTable.revalidate();
					pTable.repaint();
				}
					
				}
				
		});
		
		retrieve.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				
					if(id.getText().equals("Id") || id.getText().equals("")) {
					
						sendErrorMsg("No index selected");
						return;
					}
				
					int gotId = Integer.valueOf(id.getText());
				
					if(crudOption.getSelectedIndex() == 1) {
						
						UserModel user = admin.findCashier(gotId);
						sendMsg("username: " + user.getUsername()
								+"\nencrypted password: " +  user.getPassword());
						
						
					} else {
						
						ShowModel show = admin.findShow(gotId);
						sendMsg("title: " + show.getTitle() +
								"\ndistribution: " + show.getDistribution() +
								"\ngenre: " + show.getGenre() +
								"\ndate: " + show.getDate().toString() +
								"\nnumber of tickets: " + show.getNumber_of_tickets());
						
						
					}
					
				
				}
				
		});
		
		update.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(id.getText().equals("Id") || id.getText().equals("")) {
					
					sendErrorMsg("No index selected");
					return;
				}
				
				int gotId = Integer.valueOf(id.getText());
				
				if(crudOption.getSelectedIndex() == 1) {
					
					 UserModel user = admin.findCashier(gotId);
					 String gotPass = new_pass.getText();
					 String gotUsername = new_username.getText();
						
					 if(gotPass.equals("") || gotUsername.equals("")) {
							
							sendErrorMsg("Please fill up all fields for insertion");
							return;
							
						}
					 
					 	user.setIs_admin((byte)0);
						user.setPassword(PasswordEncryptor.oneWayEncryption(gotPass));
						user.setUsername(gotUsername);
						
						admin.updateCashier(gotId, user);
						sendMsg("Cashier updated!");
						
						//pTable.removeAll();

						List<UserModel> users = admin.findAllCashiers();
						
						int a = 0;
						for(UserModel u: users) {
							
							if (u.getIs_admin() == 0)
								a++;
							else
								break;
							
						}
						users.remove(a);
						
						pTable.removeAll();
						myTable = TableCreator.createTable(users);
						pTable.add(new JScrollPane(myTable));
						
						pTable.revalidate();
						pTable.repaint();
						
					
				} else {
					
					String showTitle = title.getText();
					String showDistribution = distribution.getText();
					String showGenre = genre.getText();
					Timestamp showDate = Timestamp.valueOf(date.getText());
					int ticketsNumber = Integer.valueOf(numberTickets.getText());
					
					if(showTitle.equals("") || showDistribution.equals("") 
							|| showGenre.equals("") || date.getText().equals("") 
							|| numberTickets.getText().equals("")) {
						
						sendErrorMsg("Please fill up all fields for insertion");
						return;
						
					}
					
					ShowModel show = new ShowModel();
					show.setDate(showDate);
					show.setDistribution(showDistribution);
					show.setGenre(showGenre);
					show.setNumber_of_tickets(ticketsNumber);
					show.setTitle(showTitle);
					
					admin.updateShow(gotId, show);
					sendMsg("Show updated!");
					
					pTable.removeAll();

					List<ShowModel> shows = admin.findAllShows();
					myTable = TableCreator.createTable(shows);
					pTable.add(new JScrollPane(myTable));
					
					pTable.revalidate();
					pTable.repaint();
					
					
				}
						
				}
				
		});
		
		delete.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
							
				if(id.getText().equals("Id") || id.getText().equals("")) {
					
					sendErrorMsg("No index selected");
					return;
				}
				
				int gotId = Integer.valueOf(id.getText());
				if(crudOption.getSelectedIndex() == 1) {
					
					boolean success = admin.deleteCashier(gotId);
					if(success)
						sendMsg("Cashier deleted!");
					else
						sendErrorMsg("Failed deletion");
					
					pTable.removeAll();

					List<UserModel> users = admin.findAllCashiers();
					
					pTable.removeAll();
					myTable = TableCreator.createTable(users);
					pTable.add(new JScrollPane(myTable));
					
					pTable.revalidate();
					pTable.repaint();	
					
				} else {
					
					boolean success = admin.deleteShow(gotId);
					
					if (success)
						sendMsg("Show deleted!");
					else
						sendErrorMsg("Failed deletion!");
					
					pTable.removeAll();

					List<ShowModel> shows = admin.findAllShows();
					myTable = TableCreator.createTable(shows);
					pTable.add(new JScrollPane(myTable));
					
					pTable.revalidate();
					pTable.repaint();
				}
					
				}
				
		});
		
		export.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
					
					int x = myTable.getSelectedRow();
					String title = String.valueOf(myTable.getValueAt(x, 1));
					String format = String.valueOf(fileOption.getSelectedItem());
					
					String current;
					try {
						current = new java.io.File( "." ).getCanonicalPath() + "\\" + title + "." + format;
						admin.export(title, current , format);
						sendMsg("File exported!");
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
