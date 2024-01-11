import java.time.LocalDate;
import java.util.*;

/**
 * Superclass for all account types.
 * Contains all essential account data as well as methods to change the balance.
 * Automatically logs these changes and can print the last 10 of these transactions.
 */
public class Account {

    /**
     * Gets last two numbers from year of current date to construct our BIC.
     *
     * @return Returns our current BIC as String.
     */
    public static String generateCurrentBIC() {
        LocalDate currentDate = LocalDate.now();
        String year = String.valueOf(currentDate.getYear());
        String yearShort = year.charAt(year.length() - 2) + String.valueOf(year.charAt(year.length() - 1));
        return "AUTBBRZ20" + yearShort;
    }

    // Account Data
    String owner = "";
    String type = "";
    String IBAN = "";
    final String BIC = generateCurrentBIC();
    double overdraft = 0;
    double balance = 0;
    ArrayList<Transaction> accountHistory = new ArrayList<>();

    /**
     * Changes this account's balance and generates a Transaction object to store in its account history.
     * @param change Amount by which the balance changes. Can be a positive or negative double.
     */
    void balanceChange(double change) {

        if(balance+change > -overdraft){
            // get current balance and change to new value
            double oldBalance = this.balance;
            this.balance += change;

            // generate transaction object and store it in account history
            Transaction transaction = new Transaction(this.IBAN, this.type, oldBalance, change);
            this.accountHistory.add(transaction);
        } else System.out.println("Abbruch: Transaktion würde Überziehungsrahmen überschreiten.");


    }

    /**
     * Prints all account transactions for this account, or the last 10 if there are more than 10.
     */
    void printLastTenTransactions(){
        if(this.accountHistory.size()<=10){
            this.accountHistory.forEach(Transaction::printTransaction);
        } else this.accountHistory.subList(this.accountHistory.size()-10, this.accountHistory.size()).forEach(Transaction::printTransaction);
    }
}