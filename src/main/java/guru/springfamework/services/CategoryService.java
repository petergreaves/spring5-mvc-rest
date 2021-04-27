package guru.springfamework.services;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;

public interface CategoryService {

    CategoryListDTO getAllCategories();
    CategoryDTO getCategoryByName(String name);

}
