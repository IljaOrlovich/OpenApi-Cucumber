package lt.eif.viko.iorlovic.library;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import lt.eif.viko.iorlovic.library.Models.AccountController;
import lt.eif.viko.iorlovic.library.Models.AuthorController;
import lt.eif.viko.iorlovic.library.Models.AwardController;
import lt.eif.viko.iorlovic.library.Models.BookController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class to handle root endpoint requests.
 */
@RestController
class RootController {

	/**
	 * Handles the root endpoint request and returns the representation model containing links to other resources.
	 *
	 * @return RepresentationModel<?> - the representation model containing links to other resources
	 */
	@GetMapping
	RepresentationModel<?> index() {

		RepresentationModel<?> rootModel = new RepresentationModel<>();
		rootModel.add(linkTo(methodOn(AuthorController.class).all()).withRel("authors"));
		rootModel.add(linkTo(methodOn(BookController.class).all()).withRel("books"));
		rootModel.add(linkTo(methodOn(AccountController.class).all()).withRel("account"));
		rootModel.add(linkTo(methodOn(AwardController.class).all()).withRel("awards"));
		return rootModel;
	}
}
