package lt.eif.viko.iorlovic.library.Models;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing book entities.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

}
