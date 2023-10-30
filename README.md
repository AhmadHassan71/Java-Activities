# Account Management System

The Account Management System is a Java-based application for managing personal bank accounts. It allows users to create and manage bank accounts for customers, perform various banking operations, and store data in different storage systems such as MySQL, Oracle, or the file system.

## Features

- Create and manage customer information with a name, address, and phone number.
- Support for two types of accounts: Checking and Savings.
- Perform operations like checking the balance, making deposits, withdrawals, and transfers.
- Calculate annual zakat for Savings accounts with a balance of 20,000 or more.
- Admin interface to open/close accounts, set interest rates, and view account details.

## Usage

This project is developed using Eclipse IDE. To use the Account Management System, follow these steps:

1. Clone this repository to your local machine.

2. Open the project in Eclipse.

3. Compile and run the Java program.

4. Choose the storage system (MySQL, Oracle, or File System) when prompted.

5. Use the provided admin interface to perform account management operations.

## Database Configuration

If you choose MySQL or Oracle as the storage system, make sure to configure your database connection parameters in the code as needed. You'll need to update the connection URL, username, and password.

```java
// Example for MySQL
connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "password");

// Example for Oracle
connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/yourdb", "username", "password");
```

## File Database
If you choose the file system as the storage option, a text file named customer_data.txt will be created to store customer data. You can manually edit this file to update data.


## Happy Coding!!

###  Feel free to customize and extend this Account Management System.


- Note: The project is developed using the Eclipse IDE.
