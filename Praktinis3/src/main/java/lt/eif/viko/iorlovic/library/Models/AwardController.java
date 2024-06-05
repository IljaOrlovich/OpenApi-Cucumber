package lt.eif.viko.iorlovic.library.Models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import lt.eif.viko.iorlovic.library.Status;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling Award-related HTTP requests.
 */
@RestController
public class AwardController {

    private final AwardsRepository awardsRepository;
    private final AwardModelAssembler assembler;

    /**
     * Constructor for AwardController.
     * @param awardsRepository The repository for accessing Award data.
     * @param assembler The model assembler for converting Award entities to EntityModel.
     */
    AwardController(AwardsRepository awardsRepository, AwardModelAssembler assembler) {
        this.awardsRepository = awardsRepository;
        this.assembler = assembler;
    }

    /**
     * Retrieves all awards.
     * @return A CollectionModel of EntityModel containing all awards and a self-link.
     */
    @GetMapping("/awards")
    public CollectionModel<EntityModel<Awards>> all() {
        List<EntityModel<Awards>> awards = awardsRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(awards, linkTo(methodOn(AwardController.class).all()).withSelfRel());
    }

    /**
     * Retrieves a single award by ID.
     * @param id The ID of the award to retrieve.
     * @return EntityModel of the retrieved award.
     * @throws AwardNotFoundException if the award with the given ID does not exist.
     */
    @GetMapping("/awards/{id}")
    EntityModel<Awards> one(@PathVariable Long id) {
        Awards awards = awardsRepository.findById(id)
                .orElseThrow(() -> new AwardNotFoundException(id));
        return assembler.toModel(awards);
    }

    /**
     * Creates a new award.
     * @param awards The award to be created.
     * @return ResponseEntity with status 201 Created and the created award in EntityModel format.
     */
    @PostMapping("/awards")
    ResponseEntity<EntityModel<Awards>> newOrder(@RequestBody Awards awards) {
        awards.setStatus(Status.IN_PROGRESS);
        Awards newAward = awardsRepository.save(awards);
        return ResponseEntity.created(linkTo(methodOn(AwardController.class).one(newAward.getId())).toUri())
                .body(assembler.toModel(newAward));
    }

    /**
     * Cancels an award by ID.
     * @param id The ID of the award to cancel.
     * @return ResponseEntity with status 200 OK and the updated award in EntityModel format.
     */
    @DeleteMapping("/awards/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id) {
        Awards awards = awardsRepository.findById(id)
                .orElseThrow(() -> new AwardNotFoundException(id));
        if (awards.getStatus() == Status.IN_PROGRESS) {
            awards.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(awardsRepository.save(awards)));
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create().withTitle("Method not allowed")
                        .withDetail("You can't cancel an order that is in the " + awards.getStatus() + " status"));
    }

    /**
     * Completes an award by ID.
     * @param id The ID of the award to complete.
     * @return ResponseEntity with status 200 OK and the updated award in EntityModel format.
     */
    @PutMapping("/awards/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id) {
        Awards awards = awardsRepository.findById(id)
                .orElseThrow(() -> new AwardNotFoundException(id));
        if (awards.getStatus() == Status.IN_PROGRESS) {
            awards.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(awardsRepository.save(awards)));
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create().withTitle("Method not allowed")
                        .withDetail("You can't complete an order that is in the " + awards.getStatus() + " status"));
    }
}
