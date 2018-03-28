package presentation;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bll.TheaterSystem;

@SuppressWarnings("serial")
public class LoginView extends JFrame{
	
	//Services
	private TheaterSystem theater;
	
	//UI
	private JPanel p1, p2, p3, p4, pFinal;
	private JLabel user, pass;
	private JTextField userText;
	private JPasswordField passText;
	
	private JButton submit;
	
	public LoginView() {
		
		super("Login");
		
		theater = new TheaterSystem();
		
		setLayout(new FlowLayout());
		p1 = new JPanel(); p2 = new JPanel(); p3 = new JPanel(); p4 = new JPanel(); pFinal = new JPanel();
		pFinal.setLayout(new BoxLayout(pFinal, BoxLayout.PAGE_AXIS));
		userText = new JTextField(20); passText = new JPasswordField(20); 
		user = new JLabel("Username: "); pass = new JLabel("Password:  ");
		submit = new JButton("Login");
		
		setLoginListener();
	
		p1.add(new JLabel(new ImageIcon("C:\\CodeBase\\NationalTheater\\theater.jpg")));
		p2.add(user); p2.add(userText);
		p3.add(pass); p3.add(passText);
		p4.add(submit);
		pFinal.add(p1); pFinal.add(p2); pFinal.add(p3); pFinal.add(p4);
		
		add(pFinal);
		setSize(500, 470);
		setResizable(false);
		setVisible(true);
	}
	
	private void setLoginListener() {
		
		LoginView view = this;
		submit.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
					String username = userText.getText();
					String password = String.valueOf(passText.getPassword());
					String response = theater.login(username, password);
					
					switch(response) {
				
					case "admin": new AdminView();
					view.dispose();
					break;
					
					case "cashier": new CashierView();
					view.dispose();
					break;
					
					default: sendErrorMsg("Invalid details!");
					
					}
					
				}
				
		});
		
	}
	
	private void sendErrorMsg(String msg){
		
		JOptionPane.showMessageDialog(null, msg, "Oops!", JOptionPane.ERROR_MESSAGE);
	}

}
