package bzh.duncan.dshop.repository;

import bzh.duncan.dshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);  // Return Optional<Category>
    boolean existsByName(String name);
}
