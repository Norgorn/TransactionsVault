package ru.norgorn.transactionsVault.persistence;

import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import ru.norgorn.transactionsVault.TestTransactionsFactory;
import ru.norgorn.transactionsVault.persistence.Transaction;
import ru.norgorn.transactionsVault.persistence.TransactionRepository;

@RunWith(SpringRunner.class)
public class TransactionRepositoryTest {

	TransactionRepository sut;
	Transaction expectedTransactionById;
	Transaction newTransaction;

	@Test
	public void getById(){
		givenTransactionServiceWithTestData();
		
		Optional<Transaction> notNullTransaction = sut.get(1);
		Optional<Transaction> nullTransaction = sut.get(100500);
		
		assertEquals("Got wrong transaction by id", notNullTransaction.get(), expectedTransactionById);
		assertFalse("Expected non-existing transaction",nullTransaction.isPresent());
	}
	
	@Test
	//@Repeat(100)
	public void getByType(){
		givenTransactionServiceWithTestData();
		
		Collection<Transaction> someTransactions = sut.getByType("1");
		Collection<Transaction> noTransactions = sut.getByType("non-existing type!");
		
		assertArrayEquals("Got wrong transactions by type"
				, new Object[] {expectedTransactionById}
				, someTransactions.toArray(new Object[someTransactions.size()])
			);
		assertTrue("Expected no transactions for non-existing type",noTransactions.isEmpty());
	}

	@Test
	public void put(){
		givenNewTransactionWithId(100);
		givenTransactionServiceWithTestData();
		
		sut.put(newTransaction);
		
		assertEquals("Got wrong transaction after put", newTransaction, sut.get(newTransaction.getId()).get());
	}

	
	void givenTransactionServiceWithTestData() {
		sut = createRepositoryWithTestData();
	}

	void givenNewTransactionWithId(long newId) {
		this.newTransaction = TestTransactionsFactory.createTransactionWithIdAndType(newId);
	}
	
	public TransactionRepository createRepositoryWithTestData() {
		TransactionRepository repo = new TransactionRepositoryImplInMemory();
		expectedTransactionById = TestTransactionsFactory.createTransactionWithIdAndType(1); 
		repo.put(expectedTransactionById);
		return repo;
	}
}
