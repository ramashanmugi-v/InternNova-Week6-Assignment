import java.util.*;

public class BankService {
    private final Map<Integer, Account> accounts = new HashMap<>();   // fast search by account number
    private final Set<Integer> usedNumbers = new HashSet<>();         // no duplicate account numbers
    private final List<String> transactionLog = new ArrayList<>();    // history
    private static int nextAccountNumber = 1001;                      // static counter

    public synchronized Account createAccount(String type, String name, double initial)
            throws InvalidAmountException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidAmountException("Name cannot be empty.");
        }
        name = name.trim().replace(",", " ");
        Account acc;
        if (type.equals("SAVINGS")) {
            if (initial < SavingsAccount.MIN_BALANCE) {
                throw new InvalidAmountException("Savings account needs minimum Rs."
                        + SavingsAccount.MIN_BALANCE + " opening balance.");
            }
            acc = new SavingsAccount(nextAccountNumber, name, initial);
        } else {
            if (initial < 0) {
                throw new InvalidAmountException("Opening balance cannot be negative.");
            }
            acc = new CurrentAccount(nextAccountNumber, name, initial);
        }
        usedNumbers.add(nextAccountNumber);
        accounts.put(nextAccountNumber, acc);
        nextAccountNumber++;
        transactionLog.add("Created account " + acc.getAccountNumber());
        return acc;
    }

    public synchronized void addLoaded(Account acc) {
        if (usedNumbers.add(acc.getAccountNumber())) {   // HashSet blocks duplicates
            accounts.put(acc.getAccountNumber(), acc);
            if (acc.getAccountNumber() >= nextAccountNumber) {
                nextAccountNumber = acc.getAccountNumber() + 1;
            }
        }
    }

    public synchronized Account find(int accNo) throws AccountNotFoundException {
        Account acc = accounts.get(accNo);
        if (acc == null) throw new AccountNotFoundException(accNo);
        return acc;
    }

    public synchronized List<Account> getAll() {
        List<Account> list = new ArrayList<>(accounts.values());
        list.sort(Comparator.comparingInt(Account::getAccountNumber));
        return list;
    }

    public synchronized void updateName(int accNo, String newName)
            throws AccountNotFoundException, InvalidAmountException {
        Account acc = find(accNo);
        if (newName == null || newName.trim().isEmpty()) {
            throw new InvalidAmountException("Name cannot be empty.");
        }
        acc.setHolderName(newName.trim().replace(",", " "));
        transactionLog.add("Updated name of account " + accNo);
    }

    public synchronized void deposit(int accNo, double amount)
            throws AccountNotFoundException, InvalidAmountException {
        find(accNo).deposit(amount);
        transactionLog.add("Deposit Rs." + amount + " to " + accNo);
    }

    public synchronized void withdraw(int accNo, double amount)
            throws AccountNotFoundException, InvalidAmountException, InsufficientBalanceException {
        find(accNo).withdraw(amount);
        transactionLog.add("Withdraw Rs." + amount + " from " + accNo);
    }

    public synchronized void delete(int accNo) throws AccountNotFoundException {
        find(accNo);
        accounts.remove(accNo);
        usedNumbers.remove(accNo);
        transactionLog.add("Deleted account " + accNo);
    }

    public synchronized List<String> getTransactionLog() {
        return new ArrayList<>(transactionLog);
    }
}
