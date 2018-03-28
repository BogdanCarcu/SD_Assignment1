package dao.repository;

import java.util.List;

import dao.dbmodel.ShowDto;

public interface IShowRepository {

	public ShowDto findById(int id);
	
	public List<ShowDto> findAll();
	
	public void insert(ShowDto show);
	
	public boolean delete(int id);
	
	public void update(int id, ShowDto show);
	
}
