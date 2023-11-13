package Bank;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
			  Database database = null; 
			 switch (dbchoice) {
	            case 1:
	                database = new MySQLdb();
	                database.connect();
	                break;
	            case 2:
	                database = new Oracledb();
	                database.connect();
	                break;
	            case 3:
	                database = new FileSystemdb("customer_data.txt");
	                break;
	            case 7:
	                System.out.println("Exiting...");
	                scanner.close();
	                System.exit(0);
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
			scanner.nextLine();
			switch (choice) {
			case 1:
				System.out.print("Enter customer name: ");
				String name = scanner.nextLine();
				System.out.print("Enter customer address: ");
				String address = scanner.nextLine();
				System.out.print("Enter customer phone number: ");
				String phoneNumber = scanner.nextLine();
				Customer customer = new Customer(system.getNextCustomerId(), name, address, phoneNumber);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        String formattedDate = dateFormat.format(new java.util.Date());

		        // Parse the formatted date to create a java.sql.Date object
		        java.sql.Date dateCreated = java.sql.Date.valueOf(formattedDate);


				System.out.print("Enter initial balance: ");
				double initialBalance = scanner.nextDouble();
				System.out.print("Enter account type (Savings or Checking): ");
				String accountType = scanner.next();
				String acc_type = "";
				double interestRate = 0;

				if (accountType.equalsIgnoreCase("Savings")) {
				    System.out.print("Enter interest rate: ");
				    interestRate = scanner.nextDouble();
				    acc_type = "Savings";
				    system.openAccount(customer, initialBalance, acc_type, interestRate);
				} else if (accountType.equalsIgnoreCase("Checking")) {
				    acc_type = "Checking";
				    system.openAccount(customer, initialBalance, acc_type, 0);
				} else {
				    System.out.println("Invalid account type. Please choose Savings or Checking.");
				    break;
				}

				String insertCustomerQuery = "INSERT INTO Customer (name, address, phoneNumber) " +
				        "VALUES ( '" + name + "', '" + address + "', '" + phoneNumber + "')";

				String insertAccountQuery = "INSERT INTO accounts (customer_id, balance, date_created, account_type, interest_rate) " +
				        "VALUES ( " + customer.customerId() + ", " + initialBalance + ", '" + dateCreated + "', '" + acc_type + "', " + interestRate + ")";

				if (database != null) {
				    database.executeQuery(insertCustomerQuery);
				    database.executeQuery(insertAccountQuery);
				    System.out.println("Account created successfully.");
				} else {
				    System.out.println("Database not selected. Please choose a database first.");
				}
				break;


			case 2:
				// Close an account
				System.out.print("Enter account number to close: ");
				int accountToClose = scanner.nextInt();
				String deleteQuery = "DELETE FROM accounts WHERE account_number = " + accountToClose;

				if (database != null) {
				    database.executeQuery(deleteQuery);
				    System.out.println("Account closed successfully.");
				}
				else {
					System.out.println("Database not selected. Please choose a database first.");
				}
				break;
			case 3:
			    // Login to an account
			    System.out.print("Enter account number to login: ");
			    int accountToLogin = scanner.nextInt();

			    String selectQuery = "SELECT * FROM accounts WHERE account_number = " + accountToLogin;

			    ResultSet resultSet = database.executeSelectQuery(selectQuery);
			    if (resultSet != null) {
			        try {
			            if (resultSet.next()) {
			                int accountNumber = resultSet.getInt("account_number");
			                String account_Type = resultSet.getString("account_type");
			                double balance = resultSet.getDouble("balance");
			                Date date_Created = resultSet.getDate("date_created");
			                double interest_Rate = resultSet.getDouble("interest_rate");

			                String selectCustomerQuery = "SELECT c.customerId, c.name, c.address, c.phoneNumber " +
			                        "FROM Customer c " +
			                        "JOIN accounts a ON c.customerId = a.customer_id " +
			                        "WHERE a.account_number = " + accountToLogin;
			                ResultSet customerResult = database.executeSelectQuery(selectCustomerQuery);

			                if (customerResult.next()) {
			                    Customer owner = new Customer(
			                            customerResult.getInt("customerId"),
			                            customerResult.getString("name"),
			                            customerResult.getString("address"),
			                            customerResult.getString("phoneNumber")
			                    );

			                    BankAccount loggedInAccount = null;
			                    if (account_Type.equalsIgnoreCase("Savings")) {
			                        loggedInAccount = new SavingsAccount(accountNumber, balance, date_Created, owner, interest_Rate);
			                    } else if (account_Type.equalsIgnoreCase("Checking")) {
			                        loggedInAccount = new CheckingAccount(accountNumber, balance, date_Created, owner);
			                    }

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

			                                String depositQuery = "UPDATE accounts SET balance = balance + " + depositAmount +
			                                        " WHERE account_number = " + accountToLogin;
			                                database.executeUpdate(depositQuery);

			                                System.out.println("Successfully Deposited!!");
			                                break;
			                            case 3:
			                                System.out.print("Enter the withdrawal amount: ");
			                                double withdrawalAmount = scanner.nextDouble();
			                                String updateSourceAccountQuery = "UPDATE accounts SET balance = balance - " +
			                                        withdrawalAmount + " WHERE account_number = " + accountToLogin;

			                                if (loggedInAccount.getBalance() >= withdrawalAmount) {
			                                    database.executeUpdate(updateSourceAccountQuery);
			                                    loggedInAccount.makeWithdrawal(withdrawalAmount);
			                                    System.out.println("Withdrawal completed successfully.");
			                                } else {
			                                    System.out.println("Insufficient funds in the source account for the withdrawal.");
			                                }
			                                break;
			                            case 4:
			                                System.out.print("Enter the target account number: ");
			                                int targetAccountNumber = scanner.nextInt();
			                                System.out.print("Enter the transfer amount: ");
			                                double transferAmount = scanner.nextDouble();

			                                String selectSourceAccountQuery = "SELECT * FROM accounts WHERE account_number = " +
			                                        accountToLogin;
			                                String selectTargetAccountQuery = "SELECT * FROM accounts WHERE account_number = " +
			                                        targetAccountNumber;

			                                ResultSet sourceAccountResultSet = database.executeSelectQuery(selectSourceAccountQuery);
			                                ResultSet targetAccountResultSet = database.executeSelectQuery(selectTargetAccountQuery);

			                                try {
			                                    if (sourceAccountResultSet.next() && targetAccountResultSet.next()) {
			                                        double sourceAccountBalance = sourceAccountResultSet.getDouble("balance");

			                                        if (sourceAccountBalance >= transferAmount) {
			                                            String updateSource = "UPDATE accounts SET balance = balance - " +
			                                                    transferAmount + " WHERE account_number = " + accountToLogin;
			                                            String updateTarget = "UPDATE accounts SET balance = balance + " +
			                                                    transferAmount + " WHERE account_number = " + targetAccountNumber;

			                                            // Deduct the transfer amount from the source account
			                                            database.executeUpdate(updateSource);

			                                            // Add the transfer amount to the target account
			                                            database.executeUpdate(updateTarget);

			                                            System.out.println("Transfer completed successfully.");
			                                        } else {
			                                            System.out.println("Insufficient funds in the source account for the transfer.");
			                                        }
			                                    } else {
			                                        System.out.println("Source or target account not found.");
			                                    }
			                                } catch (SQLException e) {
			                                    e.printStackTrace();
			                                }
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
			                    System.out.println("No customer found for the given account number.");
			                }
			            } else {
			                System.out.println("Account not found.");
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			        }
			    } else {
			        System.out.println("Database not selected. Please choose a database first.");
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
				 ResultSet resultaccSet=null;
				try {
				    String selectAllAccountsQuery = "SELECT * FROM accounts";

				     resultaccSet = database.executeSelectQuery(selectAllAccountsQuery);

				    while (resultaccSet.next()) {
				        int accountNumber = resultaccSet.getInt("account_number");
				        int customerId = resultaccSet.getInt("customer_id");
				        double balance = resultaccSet.getDouble("balance");
				        Date date_Created = resultaccSet.getDate("date_created");
				        String account_type = resultaccSet.getString("account_type");
				        double interest_rate = resultaccSet.getDouble("interest_rate");

				        System.out.println("Account Number: " + accountNumber);
				        System.out.println("Customer ID: " + customerId);
				        System.out.println("Balance: " + balance);
				        System.out.println("Date Created: " + date_Created);
				        System.out.println("Account Type: " + account_type);
				        System.out.println("Interest Rate: " + interest_rate);
				        System.out.println("---------------");
				    }

				    resultaccSet.close();

				} catch (SQLException e) {
				    System.out.println("An error occurred while fetching account details: " + e.getMessage());
				    e.printStackTrace();
				} finally {
				    if (resultaccSet != null) {
				        try {
				            resultaccSet.close();
				        } catch (SQLException e) {
				            e.printStackTrace();
				        }
				    }
				}
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
