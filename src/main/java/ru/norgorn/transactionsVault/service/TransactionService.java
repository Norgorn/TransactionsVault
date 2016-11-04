package ru.norgorn.transactionsVault.service;

import java.util.NoSuchElementException;

public interface TransactionService {

	double countSum(long parentId) throws NoSuchElementException;
}
