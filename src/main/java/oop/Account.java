package oop;

import java.util.Arrays;

public class Account {
	private static final int TRANSACTIONS_NUM = 5;
	
	private int id;
	private Transaction[] transactions;
	private int transactionIndex;
	
	{
		transactions = new Transaction[TRANSACTIONS_NUM];
	}
	
	public Account(int id) {
		this.id = id;
	}

	public void send(Account to, double amount) {
		if (to == null) {
			return;
		}
		
		if (amount <= 0) {
			return;
		}
		
		addTransaction(new Transaction(this, to, amount, AccountOperation.MONEY_TRANSFER_SEND));
		receive(this, amount);
	}
	
	public void withdrawMoney(double amount) {
		addTransaction(new Transaction(this, null, amount, AccountOperation.WITHDRAW));		
	}
	
	public Transaction[] getTransactions() {
	    return transactions;
	}
	
	private void addTransaction(Transaction transaction) {
		if (transaction == null) {
			return;
		}
		
		if (transactions.length <= transactionIndex) {
			transactions = Arrays.copyOf(transactions, transactions.length * 2);
		}
		
		transactions[transactionIndex++] = transaction;
	}
	
	private void receive(Account from, double amount) {
		if (from == null) {
			return;
		}
		
		if (amount <= 0) {
			return;
		}
		
		addTransaction(new Transaction(from, this, amount, AccountOperation.MONEY_TRANSFER_RECEIVE));
	}
	
	public static class Transaction {
        private Account from;
        private Account to;
        private double amount;
        private AccountOperation operation;
		
        public Transaction(Account from, Account to, double amount, AccountOperation operation) {
			this.from = from;
			this.to = to;
			this.amount = amount;
			this.operation = operation;
		}
	}
}