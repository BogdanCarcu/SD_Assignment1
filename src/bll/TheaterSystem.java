package bll;

import bll.model.UserModel;
import bll.service.UserService;

public class TheaterSystem {

	public String login(String username, String password) {
		
		UserService service = new UserService();
		
		try {
		
		UserModel user = service.findByName(username);
		
		String encryptedPass = PasswordEncryptor.oneWayEncryption(password);
		if(encryptedPass.equals(user.getPassword())) {
			if(user.getIs_admin() == 1) {
				
				return "admin";
			}
				
			else {
				
				return "cashier";
			}
			
		}
		
		} catch(Exception e) {
			return e.getMessage();
		}
			
		return "";
	}

}
