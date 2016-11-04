package ru.norgorn.transactionsVault.web;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

import ru.norgorn.transactionsVault.TestTransactionsFactory;
import ru.norgorn.transactionsVault.data.TransactionDTO;
import ru.norgorn.transactionsVault.persistence.Transaction;
import ru.norgorn.transactionsVault.persistence.TransactionRepository;
import ru.norgorn.transactionsVault.persistence.TransactionRepositoryImplInMemory;
import ru.norgorn.transactionsVault.service.TransactionService;

@Configuration
public class WebTestConfiguration {
	
	final static Transaction defaultTransaction = createDefaultTransaction();
	final static String defaultTransactionJson = new Gson().toJson(defaultTransaction.toDTO());
	
	final static Transaction defaultChildTransaction = createChildTransaction();
	
	@Bean
	public TransactionRepository repository(){
		TransactionRepository repository = mock(TransactionRepositoryImplInMemory.class);
		when(repository.get(anyLong())).thenReturn(Optional.empty());
		when(repository.get(defaultTransaction.getId())).thenReturn(Optional.of(defaultTransaction));
		when(repository.getByType(anyString())).thenReturn(Arrays.asList());
		when(repository.getByType(defaultTransaction.getType())).thenReturn(Arrays.asList(defaultTransaction));
		doCallRealMethod().when(repository).put(any());
		return repository;
	}
	

	@SuppressWarnings("unchecked")
	@Bean
	@Autowired
	public TransactionService service(TransactionRepository repository){
		TransactionService service = mock(TransactionService.class);
		when(service.countSum(100500)).thenThrow(NoSuchElementException.class);
		when(service.countSum(defaultTransaction.getId())).thenReturn(150.0);
		return service;
	}
	
	private static Transaction createDefaultTransaction() {
		return TestTransactionsFactory.createTransactionWithIdAndType(1);
	}
	
	private static Transaction createChildTransaction() {
		TransactionDTO data = TestTransactionsFactory.createTransactionDataWithIdAndType(2);
		data.parent_id = 1;
		return new Transaction(2, data);
	}
}
