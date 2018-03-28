package bll.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import bll.model.ShowModel;
import dao.dbmodel.ShowDto;
import dao.repository.IShowRepository;
import dao.repository.ShowRepository;

public class ShowService implements IShowService{
	
	private final IShowRepository repository;
	private ModelMapper myMapper;
	
	public ShowService() {
		
		repository = new ShowRepository();
		myMapper = new ModelMapper();
	}
	
	// JUST FOR MOCKING ONLY!
	public ShowService(IShowRepository repository) {
		
		this.repository = repository;
		myMapper = new ModelMapper();
		
	}
	//

	@Override
	public ShowModel findById(int id) {
		
		ShowDto show = repository.findById(id);
		ShowModel result = myMapper.map(show, ShowModel.class);
		
		return result;
	}

	@Override
	public List<ShowModel> findAll() {
		
		List<ShowDto> shows = repository.findAll();
		List<ShowModel> result = new ArrayList<ShowModel>();
		
		for(ShowDto s: shows) {
			
			ShowModel m = myMapper.map(s, ShowModel.class);
			result.add(m);
			
		}
		
		return result;
		
	}

	@Override
	public void insert(ShowModel m){
		
		ShowDto show = myMapper.map(m, ShowDto.class);
		repository.insert(show);
		
	}

	@Override
	public boolean delete(int id) {
		
		return repository.delete(id);
		
	}

	@Override
	public void update(int id, ShowModel m) {
		
		ShowDto show = myMapper.map(m, ShowDto.class);
		repository.update(id, show);
		
	}
	

}
