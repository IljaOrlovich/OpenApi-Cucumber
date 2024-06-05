package lt.eif.viko.iorlovic.library.Models;

import lt.eif.viko.iorlovic.library.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembler class for converting Book entities to EntityModel objects.
 */
@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

	/**
	 * Converts a Book entity to an EntityModel object.
	 *
	 * @param book The Book entity to be converted.
	 * @return The EntityModel representing the Book with appropriate links.
	 */
	@Override
	public EntityModel<Book> toModel(Book book) {
		EntityModel<Book> bookModel = EntityModel.of(book,
				linkTo(methodOn(BookController.class).one(book.getId())).withSelfRel(),
				linkTo(methodOn(BookController.class).all()).withRel("books"));

		if (book.getStatus() == Status.IN_PROGRESS) {
			bookModel.add(linkTo(methodOn(BookController.class).cancel(book.getId())).withRel("cancel"));
			bookModel.add(linkTo(methodOn(BookController.class).complete(book.getId())).withRel("complete"));
		}

		return bookModel;
	}
}
