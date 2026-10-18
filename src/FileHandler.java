import java.io.*;
import java.util.*;

public class FileHandler {

    public static void save(List<Account> accounts, String path) {
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            for (Account a : accounts) {
                out.println(a.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public static List<Account> load(String path) {
        List<Account> list = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) return list;   // first run: no file yet

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length != 4) continue;
                try {
                    int no = Integer.parseInt(p[1].trim());
                    double bal = Double.parseDouble(p[3].trim());
                    if (p[0].equals("SAVINGS")) {
                        list.add(new SavingsAccount(no, p[2], bal));
                    } else if (p[0].equals("CURRENT")) {
                        list.add(new CurrentAccount(no, p[2], bal));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Skipping corrupted line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return list;
    }
}
