package ru.norgorn.transactionsVault.persistence;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CyclicBarrier;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.junit.Test;

import ru.norgorn.transactionsVault.TestTransactionsFactory;

public class TransactionRepositoryTestMultithreading extends TransactionRepositoryTest {
	
	private static final int NUMBER_OF_PARALLEL_THREADS = 100;
	CyclicBarrier barrier = new CyclicBarrier(NUMBER_OF_PARALLEL_THREADS+1);

	@Test
	public void put(){

		givenTransactionServiceWithTestData();
		givenMultipleParallelPutsWithSameId(NUMBER_OF_PARALLEL_THREADS);
		
		try {	barrier.await(); } catch (Exception e) {}
		
		assertEquals("Simultaneous put must keep id's unique", 1, sut.getByType("2").size());
	}

	 void givenMultipleParallelPutsWithSameId(int numberOfParallelPuts) {
		IntStream.range(0, numberOfParallelPuts).forEach(i ->{
			Transaction t = TestTransactionsFactory.createTransactionWithIdAndType(2);
			putInseparateThread(t);
		});
	}

	void putInseparateThread(Transaction transaction) {
		methodInSeparateThread(barrier, transaction, (item) -> {sut.put(item);});
	}

	void methodInSeparateThread(CyclicBarrier barrier, Transaction transaction, Consumer<Transaction> method) {
		Runnable first = () -> {
				try {	barrier.await(); } catch (Exception e) {}
				method.accept(transaction);
			};
		new Thread(first).start();
	}

}
