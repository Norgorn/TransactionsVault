package ru.norgorn.transactionsVault.service;

import org.junit.Test;
import static org.junit.Assert.*;

import ru.norgorn.transactionsVault.TestTransactionsFactory;
import ru.norgorn.transactionsVault.data.TransactionDTO;
import ru.norgorn.transactionsVault.persistence.Transaction;
import ru.norgorn.transactionsVault.persistence.TransactionRepository;
import ru.norgorn.transactionsVault.persistence.TransactionRepositoryImplInMemory;

public class TransactionServiceTest {
	
	TransactionService sut;
	
	@Test
	public void countSum_NonZeroExpected(){
		givenTransactionServiceWithTestData();
		
		double actualSum = sut.countSum(1);
		
		assertEquals("Wrong sum", 1800, actualSum, 0.0);
		
	}
	
	@Test
	public void countSum_ZeroExpected(){
		givenTransactionServiceWithTestData();
		
		double zeroSum = sut.countSum(4);
		
		assertEquals("Wrong sum", 0, zeroSum, 0.0);
	}

	private void givenTransactionServiceWithTestData() {
		TransactionRepository repo = new TransactionRepositoryImplInMemory();
		repo.put(TestTransactionsFactory.createTransactionWithIdAndType(1));
		
		TransactionDTO newData = TestTransactionsFactory.createTransactionDataWithIdAndType(2);
		newData.parent_id = 1;
		newData.amount = 1000;
		repo.put(new Transaction(2,newData));
		
		newData = TestTransactionsFactory.createTransactionDataWithIdAndType(3);
		newData.parent_id = 1;
		newData.amount = 500;
		repo.put(new Transaction(3,newData));
		
		newData = TestTransactionsFactory.createTransactionDataWithIdAndType(4);
		newData.parent_id = 2;
		newData.amount = 300;
		repo.put(new Transaction(4,newData));
		
		sut = new TransactionsServiceImpl(repo);
	}
}
