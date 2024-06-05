package lt.eif.viko.iorlovic.library.Models;

/**
 * Exception thrown when an author with a specific ID is not found.
 */
class AuthorNotFoundException extends RuntimeException {

	/**
	 * Constructs a new AuthorNotFoundException with the specified author ID.
	 * @param id The ID of the author that was not found.
	 */
	AuthorNotFoundException(Long id) {
		super("Could not find author " + id);
	}
}
