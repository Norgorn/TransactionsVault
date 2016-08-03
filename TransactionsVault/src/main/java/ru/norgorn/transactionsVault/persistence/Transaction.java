package ru.norgorn.transactionsVault.persistence;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import ru.norgorn.transactionsVault.data.TransactionDTO;

public class Transaction {
	
	long id;
	TransactionDTO data;
	ConcurrentHashMap<Long, Transaction> children = new ConcurrentHashMap<>();
	
	public Transaction(long id, TransactionDTO data) {
		this.data = data;
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public double getAmount() {
		return data.amount;
	}

	public String getType() {
		return data.type;
	}

	public long getParent_id() {
		return data.parent_id;
	}
	
	public Stream<Entry<Long, Transaction>> getChildrenStream(){
		return children.entrySet().stream();
	}

	public void addDirectChild(Transaction newTransaction) {
		children.putIfAbsent(newTransaction.getId(), newTransaction);
	}
	
	@Override
	public String toString(){
		return id+" of type '"+data.toString();
	}

	public TransactionDTO toDTO() {
		return data;
	}
}
