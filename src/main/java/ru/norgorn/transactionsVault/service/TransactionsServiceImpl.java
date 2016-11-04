package ru.norgorn.transactionsVault.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.AtomicDouble;

import ru.norgorn.transactionsVault.persistence.Transaction;
import ru.norgorn.transactionsVault.persistence.TransactionRepository;

@Service
public class TransactionsServiceImpl implements TransactionService {

	TransactionRepository repository;
	
	@Autowired
	public TransactionsServiceImpl(TransactionRepository repository){
		this.repository = repository;
	}

	@Override
	public double countSum(long parentId) throws NoSuchElementException {
		return countSum(new AtomicDouble(0.0), repository.get(parentId).get());
	}
	
	private double countSum(AtomicDouble curentSumm, Transaction curentTransaction) {
		curentTransaction.getChildrenStream()
			.unordered().parallel()
			.forEach( child -> {
				curentSumm.addAndGet(child.getValue().getAmount()) ;
				countSum(curentSumm, child.getValue());
			});
		return curentSumm.get();
	}
}
