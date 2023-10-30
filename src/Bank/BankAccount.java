package Bank;
import java.util.Date;

public class BankAccount {
	private int accountNumber;
	private double balance;
	private Date dateCreated;
	private Customer owner;
	private double lastTransactionAmount;

	public BankAccount(int accountNumber, double balance, Date dateCreated, Customer owner) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.dateCreated = dateCreated;
		this.owner = owner;
		this.lastTransactionAmount=0;
	}


	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Customer getOwner() {
		return owner;
	}

	public void setOwner(Customer owner) {
		this.owner = owner;
	}


	public void checkBalance() {
		System.out.println("Account Holder: " + owner.getName());
		System.out.println("Balance: " + balance);
	}

	public void printStatement() {
		// Print a transaction statement
		System.out.println("Transaction Statement:");
		System.out.println("Account Holder: " + owner.getName());
		System.out.println("Account Number: " + accountNumber);
		System.out.println("Date and Time: " + new Date());
		System.out.println("Transaction Amount: " + lastTransactionAmount);
		System.out.println("Remaining Balance: " + balance);
		System.out.println("--------------------------");
	}

	public void makeDeposit(double amount) {
		if (amount > 0) {
			balance += amount;
			lastTransactionAmount = amount;
			System.out.println("Deposit of " + amount + " PKR is successful.");
		} else {
			System.out.println("Invalid deposit amount.");
		}
	}

	public void transferAmount(BankAccount targetAccount, double amount) {
		if (amount > 0 && balance >= amount) {
			balance -= amount;
			targetAccount.makeDeposit(amount);
			System.out.println("Transfer of " + amount + " PKR to Account Number " + targetAccount.getAccountNumber() + " is successful.");
		} else {
			System.out.println("Invalid transfer amount or insufficient balance.");
		}
	}

	public void makeWithdrawal(double amount) {
		if (amount > 0 && balance >= amount) {
			balance -= amount;
			lastTransactionAmount = -amount; 
			System.out.println("Withdrawal of " + amount + " PKR is successful.");
		} else {
			System.out.println("Invalid withdrawal amount or insufficient balance.");
		}
	}

}


