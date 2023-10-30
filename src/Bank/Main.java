package Bank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main{
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		AccountManagementSystem system = new AccountManagementSystem();
		System.out.println("\nWelcome, Admin!\n");
		while (true) {
			String Db_type;			
			
			System.out.println("\n-------------------------------------\n");
			System.out.println("1. Use MySQL database");
			System.out.println("2. Use Oracle database");
			System.out.println("3. Use File database");
			System.out.println("7. Exit");
			System.out.println("\n-------------------------------------\n");
			System.out.print("Enter your choice: ");
			
			int dbchoice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline
			
			if(dbchoice ==7) {
				System.out.println("Exiting...");
				scanner.close();
				System.exit(0);
			}
			if(dbchoice == 1) {
				Db_type="MySQL";
			}
			else if(dbchoice == 2) {
				Db_type="Oracle";
			}
			else if(dbchoice ==3) {
				Db_type="File";
			}
			else {
				System.out.println("Please Try Again!");
				while(dbchoice<1 || dbchoice>3 && dbchoice !=7) {
					System.out.print("Enter your choice: ");
					
					dbchoice = scanner.nextInt();
					scanner.nextLine(); 
					if(dbchoice == 1) {
						Db_type="MySQL";
						break;
					}
					else if(dbchoice == 2) {
						Db_type="Oracle";
						break;
					}
					else if(dbchoice ==3) {
						Db_type="File";
						break;
					}
				}
			}
			
			
			System.out.println("\n-------------------------------------\n");
			System.out.println("1. Open a New Account");
			System.out.println("2. Close an Account");
			System.out.println("3. Login to an Account");
			System.out.println("4. Set Savings Account Interest Rate");
			System.out.println("5. Display All Account Details");
			System.out.println("6. Display All Account Deductions");
			System.out.println("7. Exit");
			System.out.println("\n-------------------------------------\n");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline

			switch (choice) {
			case 1:
				// Open a new account
				System.out.print("Enter customer name: ");
				String name = scanner.nextLine();
				System.out.print("Enter customer address: ");
				String address = scanner.nextLine();
				System.out.print("Enter customer phone number: ");
				String phoneNumber = scanner.nextLine();
				Customer customer = new Customer(system.getNextCustomerId(), name, address, phoneNumber);

				System.out.print("Enter initial balance: ");
				double initialBalance = scanner.nextDouble();
				System.out.print("Enter account type (Savings or Checking): ");
				String accountType = scanner.next();

				if (accountType.equalsIgnoreCase("Savings")) {
					System.out.print("Enter interest rate: ");
					double interestRate = scanner.nextDouble();
					String acc_type ="Savings";
					system.openAccount(customer, initialBalance,acc_type,interestRate);
				} else if (accountType.equalsIgnoreCase("Checking")) {
					String acc_type="Checking";
					system.openAccount(customer, initialBalance,acc_type,0);
				} else {
					System.out.println("Invalid account type. Please choose Savings or Checking.");
				}
				break;
			case 2:
				// Close an account
				System.out.print("Enter account number to close: ");
				int accountToClose = scanner.nextInt();
				if (system.closeAccount(accountToClose)) {
					System.out.println("Account closed successfully.");
				} else {
					System.out.println("Account not found.");
				}
				break;
			case 3:
			    // Login to an account
			    System.out.print("Enter account number to login: ");
			    int accountToLogin = scanner.nextInt();
			    BankAccount loggedInAccount = system.getAccount(accountToLogin);
			    if (loggedInAccount != null) {
			        while (true) {
			        	System.out.println("\n-------------------------------------\n");
			            System.out.println("Account Operations:");
			            System.out.println("1. Check Balance");
			            System.out.println("2. Make Deposit");
			            System.out.println("3. Make Withdrawal");
			            System.out.println("4. Transfer Amount");
			            System.out.println("5. Calculate Zakat (Savings Account)");
			            System.out.println("6. Display All Deductions");
			            System.out.println("7. Exit this menu");
			            System.out.println("\n-------------------------------------\n");
			            System.out.print("Enter your choice: ");

			            int accountChoice = scanner.nextInt();
			            switch (accountChoice) {
			                case 1:
			                    loggedInAccount.checkBalance();
			                    break;
			                case 2:
			                    System.out.print("Enter the deposit amount: ");
			                    double depositAmount = scanner.nextDouble();
			                    loggedInAccount.makeDeposit(depositAmount);
			                    break;
			                case 3:
			                    System.out.print("Enter the withdrawal amount: ");
			                    double withdrawalAmount = scanner.nextDouble();
			                    loggedInAccount.makeWithdrawal(withdrawalAmount);
			                    break;
			                case 4:
			                    // Implement transfer operation
			                    break;
			                case 5:
			                    if (loggedInAccount instanceof SavingsAccount) {
			                        ((SavingsAccount) loggedInAccount).calculateZakat();
			                    } else {
			                        System.out.println("Zakat calculation is only applicable to Savings Accounts.");
			                    }
			                    break;
			                case 6:
			                    system.displayAllAccountDeductions();
			                    break;
			                case 7:
			                    System.out.println("Exiting account operations.");
			                    break;
			                default:
			                    System.out.println("Invalid choice. Please try again.");
			            }

			            if (accountChoice == 7) {
			                break; // Exit the account operations sub-menu
			            }
			        }
			    } else {
			        System.out.println("Account not found.");
			    }
			    break;

			case 4:
				// Set Savings Account Interest Rate
				System.out.print("Enter new interest rate: ");
				double newInterestRate = scanner.nextDouble();
				system.setSavingsInterestRate(newInterestRate);
				System.out.println("Interest rate updated successfully.");
				break;
			case 5:
				// Display All Account Details
				system.displayAllAccountDetails();
				break;
			case 6:
				// Display All Account Deductions
				system.displayAllAccountDeductions();
				break;
			case 7:
				// Exit the program
				System.out.println("Exiting...");
				scanner.close();
				System.exit(0);
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}
}
