package guru.springframework.services;

import guru.springframework.api.v1.model.CategoryDTO;

import guru.springframework.api.v1.model.CategoryListDTO;
import guru.springframework.mapper.CategoryMapper;
import guru.springframework.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository repository, CategoryMapper categoryMapper) {
        this.repository = repository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryListDTO getAllCategories() {
        return new CategoryListDTO(repository
                .findAll()
                .stream()
                .map(category -> {
                            return categoryMapper.categoryToCategoryDTO(category);
                        }
                )
                .collect(Collectors.toList()));
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return repository.findAll().stream().filter(category ->
            category.getName().equals(name)
        ).findFirst().map(CategoryMapper.INSTANCE::categoryToCategoryDTO).get();
    }


}
