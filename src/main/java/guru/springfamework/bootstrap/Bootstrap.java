package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository repository;

    public Bootstrap(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {

        Category fruits = Category.builder().name("Fruits").build();
        Category nuts = Category.builder().name("Nuts").build();
        Category dried = Category.builder().name("Dried").build();
        Category exotic = Category.builder().name("Exotic").build();
        Category fresh = Category.builder().name("Fresh").build();

        repository.save(fruits);
        repository.save(nuts);
        repository.save(fresh);
        repository.save(dried);
        repository.save(exotic);

        log.info("data loaded = {}", repository.count());

    }
}

