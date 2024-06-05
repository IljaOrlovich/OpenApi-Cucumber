package lt.eif.viko.iorlovic.library.Models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * Assembler class to convert Author entities to EntityModel objects.
 */
@Component
class AuthorModelAssembler implements RepresentationModelAssembler<Author, EntityModel<Author>> {

	/**
	 * Converts an Author entity to an EntityModel object.
	 * @param author The Author entity to convert.
	 * @return The EntityModel representing the Author with appropriate links.
	 */
	@Override
	public EntityModel<Author> toModel(Author author) {
		return EntityModel.of(author,
				linkTo(methodOn(AuthorController.class).one(author.getId())).withSelfRel(),
				linkTo(methodOn(AuthorController.class).all()).withRel("authors"));
	}
}
