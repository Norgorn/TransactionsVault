package ru.norgorn.transactionsVault.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableSet;

@Repository
public class TransactionRepositoryImplInMemory implements TransactionRepository {
	
	private ConcurrentHashMap<Long, Transaction> transactionsById = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Collection<Transaction>> transactionsByType = new ConcurrentHashMap<>();

	@Override
	public Optional<Transaction> get(long id) {
		return Optional.ofNullable(transactionsById.get(id));
	}

	@Override
	public Collection<Transaction> getByType(String type) {
		Collection<Transaction> collectionByType = transactionsByType.getOrDefault(type, new ArrayList<>(0));
		return ImmutableSet.copyOf(collectionByType);
	}

	@Override
	public void put(Transaction newTransaction) {
		if(transactionsById.putIfAbsent(newTransaction.getId(), newTransaction) == null){
			Collection<Transaction> collectionForType = getOrAddCollectionForType(newTransaction);
			collectionForType.add(newTransaction);
			
			if(transactionsById.containsKey(newTransaction.getParent_id()))
				transactionsById.get(newTransaction.getParent_id()).addDirectChild(newTransaction);
		}
	}

	private Collection<Transaction> getOrAddCollectionForType(Transaction newTransaction) {
		Collection<Transaction> collectionForType = new ArrayList<>();
		Collection<Transaction> previousCollection = transactionsByType.putIfAbsent(newTransaction.getType(), collectionForType);
		if(previousCollection != null)
			collectionForType = previousCollection;
		return collectionForType;
	}

}
