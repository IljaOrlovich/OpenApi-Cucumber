package lt.eif.viko.iorlovic.library.Models;

/**
 * Exception thrown when an award with a specific ID is not found.
 */
public class AwardNotFoundException extends RuntimeException {

    /**
     * Constructs a new AwardNotFoundException with the specified award ID.
     * @param id The ID of the award that was not found.
     */
    AwardNotFoundException(Long id) {
        super("Could not find award " + id);
    }
}
