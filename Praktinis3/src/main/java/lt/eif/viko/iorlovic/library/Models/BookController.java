package lt.eif.viko.iorlovic.library.Models;

import lt.eif.viko.iorlovic.library.Status;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller class for managing book-related operations.
 */
@RestController
public class BookController {

	private final BookRepository bookRepository;
	private final BookModelAssembler assembler;

	/**
	 * Constructor for BookController.
	 *
	 * @param bookRepository The repository for book entities.
	 * @param assembler      The assembler for converting Book entities to EntityModel objects.
	 */
	BookController(BookRepository bookRepository, BookModelAssembler assembler) {
		this.bookRepository = bookRepository;
		this.assembler = assembler;
	}

	/**
	 * Retrieves all books.
	 *
	 * @return A CollectionModel containing all books with appropriate links.
	 */
	@GetMapping("/books")
	public CollectionModel<EntityModel<Book>> all() {
		List<EntityModel<Book>> books = bookRepository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(books, linkTo(methodOn(BookController.class).all()).withSelfRel());
	}

	/**
	 * Retrieves a single book by its ID.
	 *
	 * @param id The ID of the book to retrieve.
	 * @return The EntityModel representing the book with appropriate links.
	 */
	@GetMapping("/books/{id}")
	public EntityModel<Book> one(@PathVariable Long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException(id));
		return assembler.toModel(book);
	}

	/**
	 * Creates a new book.
	 *
	 * @param book The book to create.
	 * @return The ResponseEntity containing the newly created book with appropriate links.
	 */
	@PostMapping("/books")
	public ResponseEntity<EntityModel<Book>> newOrder(@RequestBody Book book) {
		book.setStatus(Status.IN_PROGRESS);
		Book newBook = bookRepository.save(book);

		return ResponseEntity
				.created(linkTo(methodOn(BookController.class).one(newBook.getId())).toUri())
				.body(assembler.toModel(newBook));
	}

	/**
	 * Cancels a book order.
	 *
	 * @param id The ID of the book to cancel.
	 * @return ResponseEntity with appropriate status and message.
	 */
	@DeleteMapping("/books/{id}/cancel")
	public ResponseEntity<?> cancel(@PathVariable Long id) {

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException(id));

		if (book.getStatus() == Status.IN_PROGRESS) {
			book.setStatus(Status.CANCELLED);
			return ResponseEntity.ok(assembler.toModel(bookRepository.save(book)));
		}

		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create()
						.withTitle("Method not allowed")
						.withDetail("You can't cancel an order that is in the " + book.getStatus() + " status"));
	}

	/**
	 * Completes a book order.
	 *
	 * @param id The ID of the book to complete.
	 * @return ResponseEntity with appropriate status and message.
	 */
	@PutMapping("/books/{id}/complete")
	public ResponseEntity<?> complete(@PathVariable Long id) {

		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException(id));

		if (book.getStatus() == Status.IN_PROGRESS) {
			book.setStatus(Status.COMPLETED);
			return ResponseEntity.ok(assembler.toModel(bookRepository.save(book)));
		}

		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create()
						.withTitle("Method not allowed")
						.withDetail("You can't complete an order that is in the " + book.getStatus() + " status"));
	}
}
