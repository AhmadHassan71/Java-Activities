package Bank;

import java.util.Date;

public class SavingsAccount extends BankAccount {
	private double interestRate;

	public SavingsAccount(int accountNumber, double balance, Date dateCreated, Customer owner, double interestRate) {
		super(accountNumber, balance, dateCreated, owner);
		this.interestRate = interestRate;
	}

	public void calculateZakat() {
		if (getBalance() >= 20000) {
			double zakat = (getBalance() * 2.5) / 100;
			System.out.println("Zakat: " + zakat);
		}
		else {
			System.out.println("Zakat is not applicable as balance under PKR20,000 ");
		}
	}

}
