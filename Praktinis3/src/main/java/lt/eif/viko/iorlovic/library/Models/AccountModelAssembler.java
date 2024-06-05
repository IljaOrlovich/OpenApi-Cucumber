package lt.eif.viko.iorlovic.library.Models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * Assembler class to convert Account entities to EntityModel objects.
 */
@Component
class AccountModelAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {

    /**
     * Converts an Account entity to an EntityModel object.
     * @param account The Account entity to convert.
     * @return The EntityModel representing the Account with appropriate links.
     */
    @Override
    public EntityModel<Account> toModel(Account account) {
        return EntityModel.of(account,
                linkTo(methodOn(AccountController.class).one(account.getId())).withSelfRel(),
                linkTo(methodOn(AccountController.class).all()).withRel("accounts"));
    }
}
