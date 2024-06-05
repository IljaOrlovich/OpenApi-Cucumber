package lt.eif.viko.iorlovic.library.Models;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling Account-related HTTP requests.
 */
@RestController
public class AccountController {

    private final AccountRepository repository;
    private final AccountModelAssembler assembler;

    /**
     * Constructor for AccountController.
     * @param repository The repository for accessing Account data.
     * @param assembler The model assembler for converting Account entities to EntityModel.
     */
    AccountController(AccountRepository repository, AccountModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Retrieves all accounts.
     * @return A CollectionModel of EntityModel containing all accounts and a self-link.
     */
    @GetMapping("/account")
    public CollectionModel<EntityModel<Account>> all() {
        List<EntityModel<Account>> account = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(account, linkTo(methodOn(AccountController.class).all()).withSelfRel());
    }

    /**
     * Creates a new account.
     * @param newAccount The account to be created.
     * @return ResponseEntity with status 201 Created and the created account in EntityModel format.
     */
    @PostMapping("/account")
    ResponseEntity<?> newAccount(@RequestBody Account newAccount) {
        EntityModel<Account> entityModel = assembler.toModel(repository.save(newAccount));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    /**
     * Retrieves a single account by ID.
     * @param id The ID of the account to retrieve.
     * @return EntityModel of the retrieved account.
     * @throws AccountNotFoundException if the account with the given ID does not exist.
     */
    @GetMapping("/account/{id}")
    EntityModel<Account> one(@PathVariable Long id) {
        Account account = repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return assembler.toModel(account);
    }

    /**
     * Replaces an existing account with new account data.
     * @param newAccount The new account data.
     * @param id The ID of the account to replace.
     * @return ResponseEntity with status 201 Created and the updated account in EntityModel format.
     */
    @PutMapping("/account/{id}")
    ResponseEntity<?> replaceAccount(@RequestBody Account newAccount, @PathVariable Long id) {
        Account updatedAccount = repository.findById(id)
                .map(account -> {
                    account.setUserName(newAccount.getUserName());
                    return repository.save(account);
                })
                .orElseGet(() -> {
                    newAccount.setId(id);
                    return repository.save(newAccount);
                });
        EntityModel<Account> entityModel = assembler.toModel(updatedAccount);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    /**
     * Deletes an account by ID.
     * @param id The ID of the account to delete.
     * @return ResponseEntity with status 204 No Content.
     */
    @DeleteMapping("/account/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
