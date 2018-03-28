package bll.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import bll.model.TicketModel;
import dao.dbmodel.TicketDto;
import dao.repository.ITicketRepository;
import dao.repository.TicketRepository;

public class TicketService implements ITicketService{
	
	private final ITicketRepository repository;
	private ModelMapper myMapper;

	public TicketService() {
		
		repository = new TicketRepository();
		myMapper = new ModelMapper();
	}
	
	@Override
	public TicketModel findById(int id) {
		
		TicketDto ticket = repository.findById(id);
		TicketModel result = myMapper.map(ticket, TicketModel.class);
		
		return result;
		
	}

	@Override
	public List<TicketModel> findAll() {
		List<TicketDto> tickets = repository.findAll();
		List<TicketModel> result = new ArrayList<TicketModel>();
		
		for(TicketDto t: tickets) {
			
			TicketModel m = myMapper.map(t, TicketModel.class);
			result.add(m);
			
		}
		
		return result;
	}

	@Override
	public void insert(TicketModel m) {
		
		TicketDto ticket = myMapper.map(m, TicketDto.class);
		repository.insert(ticket);
		
	}

	@Override
	public boolean delete(int id) {
		
		return repository.delete(id);
		
	}

	@Override
	public void update(int id, TicketModel m) {
		
		TicketDto ticket = myMapper.map(m, TicketDto.class);
		repository.update(id, ticket);
		
	}

	@Override
	public List<TicketModel> ticketsForShow(String title) {
		
		
		List<TicketDto> tickets = repository.ticketsForShow(title);
		List<TicketModel> result = new ArrayList<TicketModel>();
		
		for(TicketDto t: tickets) {
			
			TicketModel m = myMapper.map(t, TicketModel.class);
			result.add(m);
			
		}
		
		return result;
		
	}
	
}
