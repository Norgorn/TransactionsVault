package ru.norgorn.transactionsVault.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import ru.norgorn.transactionsVault.persistence.TransactionRepository;
import ru.norgorn.transactionsVault.service.TransactionService;

@ContextConfiguration(classes = {WebTestConfiguration.class, TransactionController.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@EnableWebMvc
public class TransactionControllerTest {
	
	@Autowired
	TransactionRepository repository;
	
	@Autowired
	TransactionService service;
	
	@Autowired
    WebApplicationContext webApplicationContext;
	
	MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void getById_ExpectedOk() throws Exception{
		
		mockMvc.perform(get( UrlBuilder.getTransactionById( WebTestConfiguration.defaultTransaction.getId() )))
        	.andExpect(status().isOk())
        	.andExpect(content().json(WebTestConfiguration.defaultTransactionJson))
        ;
		
		verify(repository, times(1)).get(1);
	}
	
	@Test
	public void getById_ExpectedNotFound() throws Exception{
		
		mockMvc.perform(get( UrlBuilder.getTransactionById( 100500)))
        	.andExpect(status().isNotFound())
        ;
		
		verify(repository, times(1)).get(1);
	}
	
	@Test
	public void getByType_ExpectedOk() throws Exception{
		
		mockMvc.perform(
					get( UrlBuilder.getTransactionByType( WebTestConfiguration.defaultTransaction.getType() ))
				)
			.andDo(print())
        	.andExpect(status().isOk())
        	.andExpect(content().json("["+WebTestConfiguration.defaultTransaction.getId()+"]"))
        ;
		
		verify(repository, times(1)).getByType(WebTestConfiguration.defaultTransaction.getType());
	}
	
	@Test
	public void getByType_ExpectedEmptyArray() throws Exception{
		
		mockMvc.perform(get( UrlBuilder.getTransactionByType( "non-existing type!")))
        	.andExpect(status().isOk())
        	.andExpect(content().json("[]"))
        ;
		
		verify(repository, times(1)).getByType("non-existing type!");
	}
	
	@Test
	public void getSum_ExpectedOk() throws Exception{
		
		mockMvc.perform(get( UrlBuilder.getSum( WebTestConfiguration.defaultChildTransaction.getParent_id() )))
        	.andExpect(status().isOk())
        	.andExpect(content().json("{\"sum\":150}"))
        ;
		
		verify(service, times(1)).countSum(WebTestConfiguration.defaultTransaction.getId());
	}
	
	@Test
	public void getSum_ExpectedNotFound() throws Exception{
		mockMvc.perform(get( UrlBuilder.getSum( 100500 )))
    		.andExpect(status().isNotFound())
    	;
	}
}
