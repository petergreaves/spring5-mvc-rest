package guru.springframework.repositories;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jt on 9/24/17.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    CategoryDTO findByName(String s);
}
