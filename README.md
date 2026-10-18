# Bank Account Management System

## Project Description
A console-based, menu-driven Java application that manages bank accounts. It supports Savings and Current accounts, deposits, withdrawals, searching, updating and deleting records, and stores all data permanently in a text file. It was built as the Week 6 Final Project of the InternNova 6-Week Java Development Internship.

## Features
- Create Savings or Current accounts (auto-generated account numbers)
- View all accounts in a formatted table
- Search an account by account number
- Update the account holder name
- Deposit and withdraw money with balance rules
  - Savings account: minimum balance of Rs.500
  - Current account: overdraft limit of Rs.5000
- Delete an account
- Transaction history for the current session
- Data saved to `accounts.txt` and loaded automatically on start
- Background auto-save every 10 seconds
- Input validation, so the program does not crash on wrong input

## Technologies Used
- Java (JDK 17 or above)
- Git and GitHub
- VS Code / Command Prompt

## Java Concepts Used
| Concept | Where it is used |
|---|---|
| Classes and Objects | `Account`, `SavingsAccount`, `CurrentAccount`, `BankService` |
| Constructors and `this` | `Account` constructor |
| `static` | `nextAccountNumber` in `BankService`, `MIN_BALANCE` in `SavingsAccount` |
| Inheritance | `SavingsAccount` and `CurrentAccount` extend `Account` |
| Polymorphism | `getMinBalance()` behaves differently for each account type |
| Encapsulation | Private fields with getters and setters in `Account` |
| Abstraction | Abstract class `Account` and interface `Transactable` |
| Exception Handling | Custom exceptions: `InvalidAmountException`, `InsufficientBalanceException`, `AccountNotFoundException`; try-catch and try-with-resources |
| Collections | `HashMap` (search by account number), `HashSet` (no duplicate account numbers), `ArrayList` (transaction history) |
| File Handling | `FileHandler` uses `FileReader`, `FileWriter`, `BufferedReader`, `PrintWriter` |
| Multithreading | `AutoSaveThread` saves data in the background; `synchronized` methods in `BankService` |

## Project Structure
```
BankSystem/
├── src/
│   ├── Main.java
│   ├── Account.java
│   ├── SavingsAccount.java
│   ├── CurrentAccount.java
│   ├── Transactable.java
│   ├── BankService.java
│   ├── FileHandler.java
│   ├── AutoSaveThread.java
│   ├── InvalidAmountException.java
│   ├── InsufficientBalanceException.java
│   ├── AccountNotFoundException.java
│   └── accounts.txt
├── Screenshots/
├── README.md
└── .gitignore
```

## How to Run the Project
1. Install JDK 17 or above.
2. Clone the repository:
   ```
   git clone https://github.com/ramashanmugi-v/InternNova-Week6-Assignment.git
   ```
3. Open the source folder:
   ```
   cd InternNova-Week6-Assignment/src
   ```
4. Compile and run:
   ```
   javac *.java
   java Main
   ```

## Sample Output
```
===== BANK ACCOUNT MANAGEMENT SYSTEM =====
1. Create Account
2. View All Accounts
3. Search Account
4. Update Account Holder Name
5. Deposit
6. Withdraw
7. Delete Account
8. Transaction History
9. Exit
Enter choice: 1
Account type (1-Savings, 2-Current): 1
Account holder name: Shanmugi
Opening balance: 9000
Account created! Account Number: 1001

Enter choice: 2
AccNo    Name                 Type       Balance
1001     Shanmugi             SAVINGS    Rs.9000.00

Enter choice: 5
Enter account number: 1001
Deposit amount: 2000
Deposited. New balance: Rs.11000.00

Enter choice: 6
Enter account number: 1001
Withdraw amount: 1000
Withdrawn. New balance: Rs.10000.00

Enter choice: 8
1. Created account 1001
2. Deposit Rs.2000.0 to 1001
3. Withdraw Rs.1000.0 from 1001
```

## Future Improvements
- Web version using Spring Boot, MySQL and a React frontend
- Login system with PIN for each account
- Money transfer between accounts
- Permanent transaction history stored in the file or database
- Interest calculation for Savings accounts
- Admin and customer roles

## Author
V. Ramashanmugi
InternNova Java Development Internship, Week 6 Final Project
