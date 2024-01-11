/**
 * Subclass of Account, basic savings account.
 */
public class SavingsAccount extends Account {

    /**
     * Basic constructor.
     * @param newIBAN IBAN String.
     * @param accountOwner Name of the account's owner.
     * @param accountType Type of account.
     */
    SavingsAccount(String newIBAN, String accountOwner, String accountType){
        IBAN = newIBAN;
        owner = accountOwner;
        type = accountType;
    }
}

