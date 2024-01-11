import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    /**
     * Hashmap to store account data.
     * Stores account-objects within an Arraylist, using the username as key.
     */
    static HashMap<String, ArrayList<Account>> UserAccounts = new HashMap<>();

    /**
     * Accepts user's next integer input. If not int, it returns default value of -1.
     * Default value can be used to check if input happened correctly.
     * @return Returns user input if int, otherwise returns testInt.
     */
    static int getInputInt(){

        // variables for checking user's menu choice
        int testInt = -1;

        try {
            testInt = input.nextInt();

        } catch (Exception e) {
            input.nextLine();
            System.out.println(e);
        }

        return testInt;
    }

    /**
     * Accepts user's next double input. If not double, it returns default value of -1.
     * Default value can be used to check if input happened correctly.
     * @return Returns user input if double, otherwise returns testDouble.
     */
    static double getInputDouble(){

        // variables for checking user's menu choice
        double testDouble = -1;

        try {
            testDouble = input.nextDouble();

        } catch (Exception e) {
            input.nextLine();
            System.out.println(e);
        }

        return testDouble;
    }

    /**
     * Prompts user to input their username and returns it as String.
     * @return Returns user input as String.
     */
    static String getUserName(){

        Scanner input = new Scanner(System.in);

        System.out.print("Bitte Benutzernamen eingeben: ");
        String username = input.nextLine();
        System.out.println("Willkommen " + username + "!");

        return username;
    }

    /**
     * Presents main menu to user and prompts input.
     * @return Returns user's menu choice as integer.
     */
    static int mainMenu(){
        System.out.println("""
                         * 1 – Konto anlegen
                         * 2 – Einzahlen
                         * 3 – Abheben
                         * 4 – Kontoauszug
                         * 5 – Konto auflösen
                         * 8 – Ausloggen
                         * 0 – Herunterfahren (für Testzwecke)\
                    """);

        // get user input for main menu switch case
        System.out.print("Bitte korrespondierende Zahl eingeben: ");
        int menuChoice = 10;
        menuChoice = getInputInt();

        return menuChoice;
    }

    /**
     * Executes user's menu choice via switch statements, which refer to the relevant methods.
     * @param userName String with username to find correct user and attached accounts in the global hashmap.
     */
    static void menuChoice(String userName){
        inputLoop: while(true){

            int menuChoice = mainMenu();

            // variables for use in switch statement to choose user account and money amount
            int choice;
            double change;

            switch (menuChoice) {

                // Shutdown for testing purposes
                case 0:
                    System.out.println("Herunterfahren...");
                    System.exit(0);

                    // Create an account for current user.
                case 1:
                    AccountManagement.createAccount(userName, UserAccounts);
                    break;

                // Deposit money.
                case 2:
                    choice = AccountManagement.chooseUserAccount(userName, UserAccounts);
                    if(choice < 0){
                        break;
                    }

                    do{
                        System.out.print("Bitte einzuzahlenden Betrag eingeben: € ");
                        change = getInputDouble();
                    } while(change <= 0);

                    UserAccounts.get(userName).get(choice).balanceChange(change);
                    break;

                // Withdraw money.
                case 3:
                    choice = AccountManagement.chooseUserAccount(userName, UserAccounts);
                    if(choice < 0){
                        break;
                    }

                    do {
                        System.out.print("Bitte auszuzahlenden Betrag eingeben: € ");
                        change = getInputDouble();
                    } while (change <= 0);

                    change = change * -1;
                    UserAccounts.get(userName).get(choice).balanceChange(change);
                    break;

                // Print account history.
                case 4:
                    choice = AccountManagement.chooseUserAccount(userName, UserAccounts);
                    if(choice < 0){
                        break;
                    }
                    UserAccounts.get(userName).get(choice).printLastTenTransactions();
                    break;

                // Delete account.
                case 5:
                    choice = AccountManagement.chooseUserAccount(userName, UserAccounts);
                    if(choice < 0){
                        break;
                    }
                    UserAccounts.get(userName).remove(choice);
                    break;

                // Log out, should loop back to login.
                case 8:
                    System.out.println("Dankeschön! Wir wünschen Ihnen einen schönen Tag!");
                    break inputLoop;

                default:
                    System.out.println("Error: Bitte korrekte Zahl eingeben.");
                    break;
            }

        }
    }

    public static void main(String[] args){
        while(true){
            menuChoice(getUserName());
        }
    }
}


