package lt.eif.viko.iorlovic.library.Models;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Author entities, providing CRUD operations.
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
