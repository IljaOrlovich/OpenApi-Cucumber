package lt.eif.viko.iorlovic.library;

import lt.eif.viko.iorlovic.library.Models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class responsible for preloading data into the database on application startup.
 */
@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	/**
	 * Initializes the database with sample data.
	 *
	 * @param authorRepository   The repository for managing author entities.
	 * @param bookRepository     The repository for managing book entities.
	 * @param accountRepository  The repository for managing account entities.
	 * @param awardsRepository   The repository for managing awards entities.
	 * @return A CommandLineRunner to execute the data initialization.
	 */
	@Bean
	CommandLineRunner initDatabase(AuthorRepository authorRepository, BookRepository bookRepository,
								   AccountRepository accountRepository, AwardsRepository awardsRepository) {

		return args -> {
			authorRepository.save(new Author("Name1", "LastName1"));
			authorRepository.save(new Author("Name2", "LastName2"));

			authorRepository.findAll().forEach(author -> log.info("Preloaded " + author));

			accountRepository.save(new Account("UserName1", "password1"));
			accountRepository.save(new Account("UserName2", "password2"));

			accountRepository.findAll().forEach(account -> log.info("Preloaded " + account));

			bookRepository.save(new Book("Book1", "1999", Status.COMPLETED));
			bookRepository.save(new Book("Book2", "2024", Status.IN_PROGRESS));

			bookRepository.findAll().forEach(book -> {
				log.info("Preloaded " + book);
			});

			awardsRepository.save(new Awards("Award1", "1999", Status.COMPLETED));
			awardsRepository.save(new Awards("Award2", "2024", Status.IN_PROGRESS));

			awardsRepository.findAll().forEach(awards -> {
				log.info("Preloaded " + awards);
			});
		};
	}
}
