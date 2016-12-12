package ru.norgorn.transactionsVault.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class TransactionDTO {
	
	//public long id;
	@Getter @Setter
	private double amount;
	public String type;
	public long parent_id;
}
