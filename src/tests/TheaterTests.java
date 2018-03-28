package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import bll.Cashier;
import bll.PasswordEncryptor;
import bll.model.ShowModel;
import bll.service.ShowService;
import dao.dbmodel.ShowDto;
import dao.repository.ShowRepository;

class TheaterTests {

	@Test
	public void numberOfTicketsForShowExceededValidation() {
		
		Cashier cashier = new Cashier();
		
		boolean response = cashier.notifyCashier(1);
		assertEquals(true, response, "There are remaining tickets for show #1");
		
		response = cashier.notifyCashier(2);
		assertEquals(true, response, "There are remaining tickets for show #2");
		
		response = cashier.notifyCashier(3);
		assertEquals(false, response, "There are no remaining tickets for show #3");
		
	}
	
	@Test
	public void passwordShoudBeEncrypted() {
		
		String[] tests = {"MyPassword", "H3r3!!!", "12345zzz54321"};
		
		assertEquals("48503dfd58720bd5ff35c102065a52d7", PasswordEncryptor.oneWayEncryption(tests[0]));
		assertEquals("a1b6ecf8d682aaa6bd9936638323fc2f", PasswordEncryptor.oneWayEncryption(tests[1]));
		assertEquals("2bba68ddf68277011b382092a0a31421", PasswordEncryptor.oneWayEncryption(tests[2]));
		
	}
	
	// mocking
	@Test
	public void testFindShowByIdWithMockito() {
		
		ShowDto inputShow = new ShowDto();
		ShowModel outputShow = new ShowModel();
		
		inputShow.setShow_id(20);
		inputShow.setDate(Timestamp.valueOf("2016-11-16 06:43:19.77"));
		inputShow.setNumber_of_tickets(300);
		inputShow.setTitle("Romeo si Julieta");
		inputShow.setDistribution("Dirijor: Tiberiu Soare\n" + 
				"Julieta:" + 
				"Mihaela Soare\n" + 
				"Romeo:" + 
				"Ovidiu Matei Iancu\n");
		
		inputShow.setGenre("Ballet");
		
		ShowRepository repMockito = Mockito.mock(ShowRepository.class);
		Mockito.when(repMockito.findById(20)).thenReturn(inputShow);
		
		ShowService service = new ShowService(repMockito);
		outputShow = service.findById(20);
		
		assertEquals(inputShow.getTitle(), outputShow.getTitle());
		assertEquals(inputShow.getDate(), outputShow.getDate());
		assertEquals(inputShow.getDistribution(), outputShow.getDistribution());
		assertEquals(inputShow.getGenre(), outputShow.getGenre());
		assertEquals(inputShow.getNumber_of_tickets(), outputShow.getNumber_of_tickets());
		assertEquals(inputShow.getShow_id(), outputShow.getShow_id());
		
	}

}
