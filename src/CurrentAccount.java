public class CurrentAccount extends Account {
    public static final double OVERDRAFT_LIMIT = 5000.0;

    public CurrentAccount(int accountNumber, String holderName, double balance) {
        super(accountNumber, holderName, balance);
    }

    @Override
    public String getAccountType() { return "CURRENT"; }

    @Override
    public double getMinBalance() { return -OVERDRAFT_LIMIT; }   // Polymorphism
}
