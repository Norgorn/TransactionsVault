package ru.norgorn.transactionsVault.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.norgorn.transactionsVault.persistence.TransactionRepository;
import ru.norgorn.transactionsVault.persistence.TransactionRepositoryImplInMemory;
import ru.norgorn.transactionsVault.service.TransactionService;
import ru.norgorn.transactionsVault.service.TransactionsServiceImpl;

@Configuration
public class ApplicationConfig {

	@Bean
	public TransactionRepository repository(){
		return new TransactionRepositoryImplInMemory();
	}
	
	@Bean
	@Autowired
	public TransactionService service(TransactionRepository repository){
		return new TransactionsServiceImpl(repository);
	}
}
