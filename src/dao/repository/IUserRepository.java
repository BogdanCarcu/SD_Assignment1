package dao.repository;

import java.util.List;

import dao.dbmodel.UserDto;

public interface IUserRepository {

	public UserDto findById(int id);
	
	public List<UserDto> findAll();
	
	public void insert(UserDto user);
	
	public boolean delete(int id);
	
	public void update(int id, UserDto t);
	
	public UserDto findByName(String name);
	
}
