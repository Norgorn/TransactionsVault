package ru.norgorn.transactionsVault.web;

public abstract class UrlBuilder {
	
	static final String BASE_PREFIX = "/transactionservice";
	static final String TRANSACTION_PREFIX = "/transaction";
	static final String TYPES_PREFIX = "/types";
	static final String SUM_PREFIX = "/sum";

	public static String getTransactionById(long id){
		return BASE_PREFIX+TRANSACTION_PREFIX+"/"+id;
	}
	
	public static String putTransactionById(long id){
		return BASE_PREFIX+TRANSACTION_PREFIX+"/"+id;
	}
	
	public static String getTransactionByType(String type){
		return BASE_PREFIX+TYPES_PREFIX+"/"+type;
	}
	
	public static String getSum(long parentId){
		return BASE_PREFIX+SUM_PREFIX+"/"+parentId;
	}
}
