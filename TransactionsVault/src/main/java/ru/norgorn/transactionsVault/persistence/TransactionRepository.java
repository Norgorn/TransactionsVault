package ru.norgorn.transactionsVault.persistence;

import java.util.Collection;
import java.util.Optional;

public interface TransactionRepository {

	Optional<Transaction> get(long i);
	Collection<Transaction> getByType(String string);

	void put(Transaction newTransaction);
}
