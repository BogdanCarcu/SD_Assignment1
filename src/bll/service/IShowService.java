package bll.service;

import java.util.List;

import bll.model.ShowModel;

public interface IShowService {

	public ShowModel findById(int id);
	
	public List<ShowModel> findAll();
	
	public void insert(ShowModel m);
	
	public boolean delete(int id);
	
	public void update(int id, ShowModel m);
	
}
