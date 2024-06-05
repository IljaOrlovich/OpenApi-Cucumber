package lt.eif.viko.iorlovic.library.Models;

/**
 * Exception thrown when an account with a specific ID is not found.
 */
public class AccountNotFoundException extends RuntimeException {

    /**
     * Constructs a new AccountNotFoundException with the specified account ID.
     * @param id The ID of the account that was not found.
     */
    public AccountNotFoundException(Long id) {
        super("Could not find account " + id);
    }
}
