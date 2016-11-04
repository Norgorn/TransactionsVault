package ru.norgorn.transactionsVault.web;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.norgorn.transactionsVault.data.TransactionDTO;
import ru.norgorn.transactionsVault.persistence.Transaction;
import ru.norgorn.transactionsVault.persistence.TransactionRepository;
import ru.norgorn.transactionsVault.service.TransactionService;

@RestController
@RequestMapping(value="/transactionservice")
public class TransactionController {
	
	
	TransactionRepository repository;
	TransactionService transactionService;
	
	@Autowired
	public TransactionController(TransactionRepository repository
			, TransactionService transactionService){
		this.repository = repository;
		this.transactionService = transactionService;
	}
	
	@RequestMapping(value = "/transaction/{transactionId}", method = {RequestMethod.PUT})
	public void put(@PathVariable long transactionId, @RequestBody TransactionDTO newTransaction){
		repository.put(new Transaction(transactionId, newTransaction));
	}
	
	@RequestMapping("/transaction/{transactionId}")
	public ResponseEntity<TransactionDTO> getById(@PathVariable long transactionId){		
		Optional<Transaction> nullableTransaction = repository.get(transactionId);
		if(nullableTransaction.isPresent())
			return ResponseEntity.ok(nullableTransaction.get().toDTO());
		else
			return ResponseEntity.status(404).body(null);
	}
	
	@RequestMapping(value="/types/{transactionType}", produces="application/json")
	public ResponseEntity<long[]> getByType(@PathVariable String transactionType){		
		Collection<Transaction> transactions = repository.getByType(transactionType);
		return ResponseEntity.ok(transactionsToIds(transactions));
	}
	
	@RequestMapping("/sum/{transactionId}")
	public ResponseEntity<SumDTO> countSum(@PathVariable long transactionId){
		try {
			SumDTO sum = new SumDTO();
			sum.sum = transactionService.countSum(transactionId);
			return ResponseEntity.ok(sum);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body(null);
		}
	}

	long[] transactionsToIds(Collection<Transaction> transactions) {
		long[] result = new long[transactions.size()];
		AtomicInteger ind = new AtomicInteger(0);
		transactions.stream().forEach(transaction -> result[ind.getAndIncrement()] = transaction.getId());
		return result;
	}
	
	class SumDTO{
		public double sum;
	}
}
