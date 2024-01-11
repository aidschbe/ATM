import java.util.*;

/**
 * Class containing functions for managing the accounts of a user, such as creating an account or printing an overview of all their accounts.
 */
public class AccountManagement {

    static Scanner input = new Scanner(System.in);

    /**
     * Prints out basic information on all the user's accounts.
     * @param userName String with username to find correct user and attached accounts in the global hashmap.
     * @param ArrayListIndex Integer to find index of correct account inside the user's ArrayList.
     */
    static void printAccounts(String userName, int ArrayListIndex, HashMap<String, ArrayList<Account>> UserAccounts) {
        System.out.println(UserAccounts.get(userName).get(ArrayListIndex).IBAN);
        System.out.println(UserAccounts.get(userName).get(ArrayListIndex).type);
        System.out.println("Inhaber: " + UserAccounts.get(userName).get(ArrayListIndex).owner);
        System.out.println("Kontostand: € " + UserAccounts.get(userName).get(ArrayListIndex).balance);
        System.out.println("Überziehungsrahmen: € " + UserAccounts.get(userName).get(ArrayListIndex).overdraft);
    }

    /**
     * Constructs IBAN using random numbers, checks it against account hashmap.
     * If it already exists, generates a new one.
     * Once it generates a unique IBAN, returns it as string.
     */
    static public String generateIBAN(HashMap<String, ArrayList<Account>> UserAccounts) {

        Random random = new Random();

        while (true) {
            String iban4 = String.format("%04d", random.nextInt(9999));
            String iban8 = String.format("%08d", random.nextInt(99999999));
            String NewIBAN = "AT00" + iban4 + "0000" + iban8;
            if (!UserAccounts.containsKey(NewIBAN)) {
                return NewIBAN;
            }
        }
    }

    /**
     * Prompts user to input necessary data for account creation, stores it in an arraylist, which it then returns.
     */
    static ArrayList<String> getAccountData() {

        // Variables for filling ArrayList at the end.
        String Name = "";
        String Typ = "";
        double Rahmen = 0;

        // Name input.
        while (Name.isEmpty()) {
            System.out.print("Bitte Vorname + Nachname eingeben: ");
            Name = input.nextLine();
        }

        // Account type input.
        System.out.println("""
                     * 1 – Girokonto
                     * 2 – Kreditkonto
                     * 3 – Sparkonto\
                """);

        do{
            System.out.print("Bitte Kontotyp auswählen: ");
            String MenuChoice = input.nextLine();
            switch (MenuChoice) {
                case "1" -> Typ = "Girokonto";
                case "2" -> Typ = "Kreditkonto";
                case "3" -> Typ = "Sparkonto";
                default -> System.out.println("Error: Eingabefehler");
            }
        } while(Typ.isEmpty());

        // Overdraft Input, if not savings account.
        if (Typ.equals("Girokonto") || Typ.equals("Kreditkonto")) {
            do{
                System.out.print("Bitte gewünschten Überziehungsrahmen eingeben: ");
                Rahmen = Main.getInputDouble();
            } while (Rahmen < 0);
        }

        // Construct ArrayList from variables to return.
        ArrayList<String> KontoDaten = new ArrayList<>();
        KontoDaten.add(Name);
        KontoDaten.add(Typ);
        KontoDaten.add(String.valueOf(Rahmen));
        return KontoDaten;
    }

    /**
     * Creates an account based on user input from getAccountData method.
     * @param userName The username input during login and used to identify the users accounts in the global hashmap.
     */
    static void createAccount(String userName, HashMap<String, ArrayList<Account>> UserAccounts) {

        ArrayList<String> KontoDaten = getAccountData();

        String newIBAN = generateIBAN(UserAccounts);
        String KontoInhaber = KontoDaten.get(0);
        String Kontotyp = KontoDaten.get(1);
        double Rahmen = Double.parseDouble(KontoDaten.get(2));

        // creates user in hashmap if nonexistent or arraylist empty
        if (!UserAccounts.containsKey(userName) || UserAccounts.get(userName).isEmpty()) {
            ArrayList<Account> UserAccountList = new ArrayList<>();
            UserAccounts.put(userName, UserAccountList);
        }

        switch (Kontotyp) {
            case "Girokonto" -> {
                CheckingAccount newGiro = new CheckingAccount(newIBAN, KontoInhaber, Kontotyp, Rahmen);
                UserAccounts.get(userName).add(newGiro);
            }
            case "Kreditkonto" -> {
                CreditAccount newKred = new CreditAccount(newIBAN, KontoInhaber, Kontotyp, Rahmen);
                UserAccounts.get(userName).add(newKred);
            }
            case "Sparkonto" -> {
                SavingsAccount newSpar = new SavingsAccount(newIBAN, KontoInhaber, Kontotyp);
                UserAccounts.get(userName).add(newSpar);
            }
        }
    }

    /**
     * Prints list of accounts belonging to the user.
     * Prompts input to choose an account to perform an action on.
     * @param userName String with username to find correct user and attached accounts in the global hashmap.
     * @return Returns integer corresponding to the index of the chosen account in the user's ArrayList.
     */
    static int chooseUserAccount(String userName, HashMap<String, ArrayList<Account>> UserAccounts){

        if(UserAccounts.get(userName) == null){
            System.out.println("Benutzer besitzt keine aktiven Konten.");
            return -1;
        }

        if (UserAccounts.get(userName).size() == 0 || UserAccounts.get(userName).isEmpty()) {
            System.out.println("Benutzer besitzt aktuell keine Konten.");
            return -1;
        }

        System.out.println("Kontoliste:");

        for (int i = 0; i < UserAccounts.get(userName).size(); i++){
            System.out.println("Konto " + (i + 1) + ":");
            printAccounts(userName, i, UserAccounts);
        }

        int userChoice = -1;

        while(userChoice < 0 || userChoice > UserAccounts.get(userName).size()){
            System.out.print("Bitte Konto auswählen: ");
            userChoice = Main.getInputInt();
        }
        return userChoice-1;
    }
}
