package bll.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import bll.model.UserModel;
import dao.dbmodel.UserDto;
import dao.repository.IUserRepository;
import dao.repository.UserRepository;

public class UserService implements IUserService{

	private final IUserRepository repository;
	private ModelMapper myMapper;
	
	public UserService() {
		
		repository = new UserRepository();
		myMapper = new ModelMapper();
	}
	
	@Override
	public UserModel findById(int id) {
		
		UserDto user = repository.findById(id);
		UserModel result = myMapper.map(user, UserModel.class);
		
		return result;
	}

	@Override
	public List<UserModel> findAll() {
		
		List<UserDto> users = repository.findAll();
		List<UserModel> result = new ArrayList<UserModel>();
		
		for(UserDto u : users) {
			
			UserModel m = myMapper.map(u, UserModel.class);
			result.add(m);
			
		}
		
		return result;
	}

	@Override
	public void insert(UserModel m) {
		
		UserDto user = myMapper.map(m, UserDto.class);
		repository.insert(user);
		
	}

	@Override
	public boolean delete(int id) {
		
		return repository.delete(id);
	}

	@Override
	public void update(int id, UserModel m) {
	
		UserDto user = myMapper.map(m, UserDto.class);
		repository.update(id, user);
		
	}

	@Override
	public UserModel findByName(String name) {
		
		UserDto user = repository.findByName(name);
		UserModel result = myMapper.map(user, UserModel.class);
		return result;
		
	}
	
	

}
