// Abstract class: common data and behaviour for all account types
public abstract class Account implements Transactable {
    private final int accountNumber;      // Encapsulation: private fields
    private String holderName;
    protected double balance;

    public Account(int accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;   // 'this' keyword
        this.holderName = holderName;
        this.balance = balance;
    }

    public abstract String getAccountType();
    public abstract double getMinBalance();   // rule differs per account type

    public int getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    @Override
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than 0.");
        }
        balance += amount;
    }

    @Override
    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than 0.");
        }
        if (balance - amount < getMinBalance()) {
            throw new InsufficientBalanceException("Insufficient balance. Available: Rs."
                    + String.format("%.2f", balance - getMinBalance()));
        }
        balance -= amount;
    }

    public String toFileString() {
        return getAccountType() + "," + accountNumber + "," + holderName + "," + balance;
    }

    @Override
    public String toString() {
        return String.format("%-8d %-20s %-10s Rs.%.2f", accountNumber, holderName, getAccountType(), balance);
    }
}
