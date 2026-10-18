import java.util.*;

public class Main {
    private static final String DATA_FILE = "accounts.txt";
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        BankService service = new BankService();
        for (Account a : FileHandler.load(DATA_FILE)) {
            service.addLoaded(a);
        }

        AutoSaveThread autoSave = new AutoSaveThread(service, DATA_FILE);
        autoSave.start();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1: createAccount(service); break;
                    case 2: viewAll(service); break;
                    case 3: searchAccount(service); break;
                    case 4: updateAccount(service); break;
                    case 5: deposit(service); break;
                    case 6: withdraw(service); break;
                    case 7: deleteAccount(service); break;
                    case 8: showHistory(service); break;
                    case 9:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Enter 1-9.");
                }
            } catch (AccountNotFoundException | InvalidAmountException | InsufficientBalanceException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        autoSave.stopSaving();
        FileHandler.save(service.getAll(), DATA_FILE);
        System.out.println("Data saved. Thank you for using the Bank System!");
    }

    private static void printMenu() {
        System.out.println("\n===== BANK ACCOUNT MANAGEMENT SYSTEM =====");
        System.out.println("1. Create Account");
        System.out.println("2. View All Accounts");
        System.out.println("3. Search Account");
        System.out.println("4. Update Account Holder Name");
        System.out.println("5. Deposit");
        System.out.println("6. Withdraw");
        System.out.println("7. Delete Account");
        System.out.println("8. Transaction History");
        System.out.println("9. Exit");
    }

    private static void createAccount(BankService s) throws InvalidAmountException {
        System.out.print("Account type (1-Savings, 2-Current): ");
        String t = sc.nextLine().trim();
        String type = t.equals("2") ? "CURRENT" : "SAVINGS";
        System.out.print("Account holder name: ");
        String name = sc.nextLine();
        double initial = readDouble("Opening balance: ");
        Account acc = s.createAccount(type, name, initial);
        System.out.println("Account created! Account Number: " + acc.getAccountNumber());
    }

    private static void viewAll(BankService s) {
        List<Account> list = s.getAll();
        if (list.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        System.out.printf("%-8s %-20s %-10s %s%n", "AccNo", "Name", "Type", "Balance");
        for (Account a : list) {
            System.out.println(a);   // polymorphic toString
        }
    }

    private static void searchAccount(BankService s) throws AccountNotFoundException {
        int no = readInt("Enter account number: ");
        System.out.println(s.find(no));
    }

    private static void updateAccount(BankService s)
            throws AccountNotFoundException, InvalidAmountException {
        int no = readInt("Enter account number: ");
        System.out.print("New holder name: ");
        s.updateName(no, sc.nextLine());
        System.out.println("Name updated.");
    }

    private static void deposit(BankService s)
            throws AccountNotFoundException, InvalidAmountException {
        int no = readInt("Enter account number: ");
        double amt = readDouble("Deposit amount: ");
        s.deposit(no, amt);
        System.out.println("Deposited. New balance: Rs." + String.format("%.2f", s.find(no).getBalance()));
    }

    private static void withdraw(BankService s)
            throws AccountNotFoundException, InvalidAmountException, InsufficientBalanceException {
        int no = readInt("Enter account number: ");
        double amt = readDouble("Withdraw amount: ");
        s.withdraw(no, amt);
        System.out.println("Withdrawn. New balance: Rs." + String.format("%.2f", s.find(no).getBalance()));
    }

    private static void deleteAccount(BankService s) throws AccountNotFoundException {
        int no = readInt("Enter account number: ");
        s.delete(no);
        System.out.println("Account deleted.");
    }

    private static void showHistory(BankService s) {
        List<String> log = s.getTransactionLog();
        if (log.isEmpty()) {
            System.out.println("No transactions in this session.");
            return;
        }
        for (int i = 0; i < log.size(); i++) {
            System.out.println((i + 1) + ". " + log.get(i));
        }
    }

    // Input helpers: keep asking until valid input (no crash)
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
