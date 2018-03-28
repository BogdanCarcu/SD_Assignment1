package bll.service;

import java.util.List;

import bll.model.UserModel;

public interface IUserService {
	
	public UserModel findById(int id);
	
	public List<UserModel> findAll();
	
	public void insert(UserModel m);
	
	public boolean delete(int id);
	
	public void update(int id, UserModel m);
	
	public UserModel findByName(String name);

}
