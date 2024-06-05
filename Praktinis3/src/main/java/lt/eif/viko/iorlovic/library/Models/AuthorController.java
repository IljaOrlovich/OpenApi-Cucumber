package lt.eif.viko.iorlovic.library.Models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling Author-related HTTP requests.
 */
@RestController
public class AuthorController {

	private final AuthorRepository repository;
	private final AuthorModelAssembler assembler;

	/**
	 * Constructor for AuthorController.
	 * @param repository The repository for accessing Author data.
	 * @param assembler The model assembler for converting Author entities to EntityModel.
	 */
	AuthorController(AuthorRepository repository, AuthorModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	/**
	 * Retrieves all authors.
	 * @return A CollectionModel of EntityModel containing all authors and a self-link.
	 */
	@GetMapping("/authors")
	public CollectionModel<EntityModel<Author>> all() {
		List<EntityModel<Author>> authors = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(authors, linkTo(methodOn(AuthorController.class).all()).withSelfRel());
	}

	/**
	 * Creates a new author.
	 * @param newAuthor The author to be created.
	 * @return ResponseEntity with status 201 Created and the created author in EntityModel format.
	 */
	@PostMapping("/authors")
	ResponseEntity<?> newAuthor(@RequestBody Author newAuthor) {
		EntityModel<Author> entityModel = assembler.toModel(repository.save(newAuthor));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	/**
	 * Retrieves a single author by ID.
	 * @param id The ID of the author to retrieve.
	 * @return EntityModel of the retrieved author.
	 * @throws AuthorNotFoundException if the author with the given ID does not exist.
	 */
	@GetMapping("/authors/{id}")
	EntityModel<Author> one(@PathVariable Long id) {
		Author author = repository.findById(id)
				.orElseThrow(() -> new AuthorNotFoundException(id));
		return assembler.toModel(author);
	}

	/**
	 * Replaces an existing author with new author data.
	 * @param newAuthor The new author data.
	 * @param id The ID of the author to replace.
	 * @return ResponseEntity with status 200 OK and the updated author in EntityModel format.
	 */
	@PutMapping("/authors/{id}")
	ResponseEntity<?> replaceAuthor(@RequestBody Author newAuthor, @PathVariable Long id) {
		Author updatedAuthor = repository.findById(id)
				.map(author -> {
					author.setName(newAuthor.getName());
					return repository.save(author);
				})
				.orElseGet(() -> {
					newAuthor.setId(id);
					return repository.save(newAuthor);
				});

		EntityModel<Author> entityModel = assembler.toModel(updatedAuthor);
		return ResponseEntity.ok().body(entityModel);
	}

	/**
	 * Deletes an author by ID.
	 * @param id The ID of the author to delete.
	 * @return ResponseEntity with status 204 No Content.
	 */
	@DeleteMapping("/authors/{id}")
	ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
