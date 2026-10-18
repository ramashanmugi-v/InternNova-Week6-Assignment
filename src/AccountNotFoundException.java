public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(int accountNumber) {
        super("Account " + accountNumber + " not found.");
    }
}
