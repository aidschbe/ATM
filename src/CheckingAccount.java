/**
 * Subclass of Account, basic checking account.
 */
public class CheckingAccount extends Account {

    /**
     * Basic constructor.
     * @param newIBAN IBAN String.
     * @param accountOwner Name of the account's owner.
     * @param accountType Type of account.
     * @param overdraftLimit Overdraft limit of the account.
     */
    CheckingAccount(String newIBAN, String accountOwner, String accountType, double overdraftLimit){
        IBAN = newIBAN;
        owner = accountOwner;
        type = accountType;
        overdraft = overdraftLimit;
    }
}
