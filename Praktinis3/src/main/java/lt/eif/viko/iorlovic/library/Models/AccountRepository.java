package lt.eif.viko.iorlovic.library.Models;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Account entities, providing CRUD operations.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}
