package lt.eif.viko.iorlovic.library.Models;

/**
 * Exception indicating that a book was not found.
 */
class BookNotFoundException extends RuntimeException {

	/**
	 * Constructs a new BookNotFoundException with the specified book ID.
	 *
	 * @param id The ID of the book that was not found.
	 */
	BookNotFoundException(Long id) {
		super("Could not find book " + id);
	}
}
