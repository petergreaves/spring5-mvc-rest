package guru.springframework.services;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CategoryListDTO;

public interface CategoryService {

    CategoryListDTO getAllCategories();
    CategoryDTO getCategoryByName(String name);

}
