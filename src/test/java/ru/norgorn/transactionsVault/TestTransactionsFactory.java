package ru.norgorn.transactionsVault;

import ru.norgorn.transactionsVault.data.TransactionDTO;
import ru.norgorn.transactionsVault.persistence.Transaction;

public abstract class TestTransactionsFactory {

	public static Transaction createTransactionWithIdAndType(long newId) {
		TransactionDTO newData = createTransactionDataWithIdAndType(newId);
		Transaction newTransaction = new Transaction(newId, newData);
		return newTransaction;
	}

	public static TransactionDTO createTransactionDataWithIdAndType(long newId) {
		TransactionDTO newData = new TransactionDTO();
		newData.type = Long.toString(newId);
		return newData;
	}

}
