package lt.eif.viko.iorlovic.library.Models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import lt.eif.viko.iorlovic.library.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * Assembler class to convert Awards entities to EntityModel objects.
 */
@Component
public class AwardModelAssembler implements RepresentationModelAssembler<Awards, EntityModel<Awards>> {

    /**
     * Converts an Awards entity to an EntityModel object.
     * @param awards The Awards entity to convert.
     * @return The EntityModel representing the Awards with appropriate links.
     */
    @Override
    public EntityModel<Awards> toModel(Awards awards) {
        EntityModel<Awards> awardsModel = EntityModel.of(awards,
                linkTo(methodOn(AwardController.class).one(awards.getId())).withSelfRel(),
                linkTo(methodOn(AwardController.class).all()).withRel("awards"));

        if (awards.getStatus() == Status.IN_PROGRESS) {
            awardsModel.add(linkTo(methodOn(AwardController.class).cancel(awards.getId())).withRel("cancel"));
            awardsModel.add(linkTo(methodOn(AwardController.class).complete(awards.getId())).withRel("complete"));
        }

        return awardsModel;
    }
}
