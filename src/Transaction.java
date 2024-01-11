import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class for creating objects that store single transactions of an account (i.e. one withdrawal) and print them.
 */
public class Transaction {

    // ArrayList for easy printing.
    ArrayList<String> info = new ArrayList<>();

    /**
     * Basic constructor.
     * Adds all variables to the arraylist after creation.
     * @param AccountIBAN IBAN of the affected account.
     * @param accountType Type of the affected account.
     * @param accountBalance Previous balance of the affected account.
     * @param accountChange Change to the account's balance.
     */
    Transaction(String AccountIBAN, String accountType, double accountBalance, double accountChange){
        String IBAN = AccountIBAN;
        String type = accountType;
        double balance = accountBalance;
        double change = accountChange;
        double newBalance = accountBalance + accountChange;
        String timestamp = String.valueOf(LocalDateTime.now());

        info.add("Kontoänderung " + timestamp);
        info.add("IBAN: " + IBAN);
        info.add(type);
        info.add("Vorheriger Kontostand: " + balance + " €");
        info.add("Kontoänderung: " + change + " €");
        info.add("Neuer Kontostand: " + newBalance + " €");
    }

    /**
     * Prints ArrayList containing all transaction variables alongside descriptions.
     */
    void printTransaction(){
        System.out.println(info);
    }
}
