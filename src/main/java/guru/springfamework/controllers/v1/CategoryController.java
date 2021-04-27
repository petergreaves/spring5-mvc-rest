package guru.springfamework.controllers.v1;


import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/api/v1/categories/")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {

        CategoryListDTO list = categoryService.getAllCategories();
        log.info("list contains : {}", list.getCategoryDTOList().size());
        return new ResponseEntity(list, HttpStatus.OK);

    }

    @GetMapping("{name}")
    public ResponseEntity<CategoryDTO> getAllCategories(@PathVariable("name") String catName) {

        CategoryDTO cat = categoryService.getCategoryByName(catName);
        return new ResponseEntity(cat, HttpStatus.OK);

    }
}
