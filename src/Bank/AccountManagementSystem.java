package Bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class AccountManagementSystem {
	private List<Customer> customers;
	private List<BankAccount> accounts;
	private double savingsInterestRate;
	private int nextCustomerId; // To track the next available customer ID
	private int nextAccountNumber; // To track the next available account number

	public AccountManagementSystem() {
		customers = new ArrayList<>();
		accounts = new ArrayList<>();
		savingsInterestRate = 0.0; // Default interest rate
		nextCustomerId = 1; // Start with the first customer ID
		nextAccountNumber = 1; // Start with the first account number
	}


	public int getNextCustomerId() {
		return nextCustomerId++; 
	}


	public void openAccount(Customer customer, double initialBalance, String accountType, double interestRate) {
		int accountNumber = getNextAccountNumber();
		System.out.print(customer.getName()+", your account number is " + accountNumber + '\n');
		if (accountType.equalsIgnoreCase("Savings")) {
			SavingsAccount savingsAccount = new SavingsAccount(accountNumber, initialBalance, new Date(), customer, interestRate);
			accounts.add(savingsAccount);
		} else if (accountType.equalsIgnoreCase("Checking")) {
			CheckingAccount checkingAccount = new CheckingAccount(accountNumber, initialBalance, new Date(), customer);
			accounts.add(checkingAccount);
		} else {
			System.out.println("Invalid account type. Please choose Savings or Checking.");
		}
	}
	
	public void displayAllAccountDeductions() {
	    for (BankAccount account : accounts) {
	        System.out.println("Account Number: " + account.getAccountNumber());

	        if (account instanceof SavingsAccount) {
	            SavingsAccount savingsAccount = (SavingsAccount) account;
	            savingsAccount.calculateZakat();
	        } else if (account instanceof CheckingAccount) {
	            CheckingAccount checkingAccount = (CheckingAccount) account;
	            double transactionFees = checkingAccount.getTransactionFees();
	            System.out.println("Transaction Fees Deduction: " + transactionFees);
	        }
	    }
	}

	

	public boolean closeAccount(int accountNumber) {
		for (BankAccount account : accounts) {
			if (account.getAccountNumber() == accountNumber) {
				accounts.remove(account);
				return true;
			}
		}
		return false; 
	}

	public BankAccount login(int accountNumber) {
		for (BankAccount account : accounts) {
			if (account.getAccountNumber() == accountNumber) {
				return account;
			}
		}
		return null; // Account not found
	}

	public void setSavingsInterestRate(double newInterestRate) {
		savingsInterestRate = newInterestRate;
	}

	public void displayAllAccountDetails() {
		
		for (BankAccount account : accounts) {
			System.out.println("Account Number: " + account.getAccountNumber());
			account.checkBalance(); // Display balance and other details
		}
	}

	private int getNextAccountNumber() {
		return nextAccountNumber++; 
	}
	
	public BankAccount getAccount(int accountNumber) {
	    for (BankAccount account : accounts) {
	        if (account.getAccountNumber() == accountNumber) {
	            return account;
	        }
	    }
	    return null; // Account not found
	}
}
