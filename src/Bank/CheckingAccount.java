package Bank;

import java.util.Date;

public class CheckingAccount extends BankAccount {
	private int freeTransactions;
	private double transactionFee;

	public CheckingAccount(int accountNumber, double balance, Date dateCreated, Customer owner) {
		super(accountNumber, balance, dateCreated, owner);
		this.freeTransactions = 2;
		this.transactionFee = 10.0;
	}

	@Override
	public void makeWithdrawal(double amount) {
		if (amount <= getBalance() + 5000.0) {
			super.makeWithdrawal(amount);
		} else {
			System.out.println("Withdrawal limit exceeded.");
		}
	}

	private void chargeTransactionFee() {
		setBalance(getBalance() - transactionFee);
	}

	public double getTransactionFees() {
		int usedTransactions = 2 - freeTransactions; 
		return usedTransactions * transactionFee;
	}

}
