package ru.norgorn.transactionsVault.data;

public class TransactionDTO {
	
	//public long id;
	public double amount;
	public String type;
	public long parent_id;
	
	@Override
	public String toString(){
		return type+"', amount: "+amount+", parent: "+parent_id;
	}
}
