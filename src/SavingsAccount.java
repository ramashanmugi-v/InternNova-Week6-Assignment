// Inheritance: SavingsAccount IS-A Account
public class SavingsAccount extends Account {
    public static final double MIN_BALANCE = 500.0;   // static constant

    public SavingsAccount(int accountNumber, String holderName, double balance) {
        super(accountNumber, holderName, balance);
    }

    @Override
    public String getAccountType() { return "SAVINGS"; }

    @Override
    public double getMinBalance() { return MIN_BALANCE; }   // Polymorphism
}
