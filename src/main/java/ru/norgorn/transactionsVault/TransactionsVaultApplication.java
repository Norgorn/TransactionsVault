package ru.norgorn.transactionsVault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ru.norgorn.transactionsVault.conf", "ru.norgorn.transactionsVault.web"})
public class TransactionsVaultApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionsVaultApplication.class, args);
	}
}
