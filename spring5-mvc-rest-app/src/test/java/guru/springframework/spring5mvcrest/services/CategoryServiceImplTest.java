package guru.springframework.spring5mvcrest.services;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CategoryListDTO;
import guru.springframework.domain.Category;
import guru.springframework.mapper.CategoryMapper;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.services.CategoryService;
import guru.springframework.services.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CategoryServiceImplTest {


    CategoryService categoryService;

    CategoryMapper categoryMapper;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);
        categoryMapper = CategoryMapper.INSTANCE;
        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);


    }

    @Test
    public void getAllCategories() {

        List<Category> cats = new ArrayList<>();

        cats.addAll(Arrays.asList(new Category(), new Category(), new Category()));

        when(categoryRepository.findAll()).thenReturn(cats);

        CategoryListDTO catDTOList = categoryService.getAllCategories();

        Assertions.assertEquals(catDTOList.getCategoryDTOList().size(), cats.size());


    }

    @Test
    public void getCategoryByName() {

        List<Category> cats = new ArrayList<>();

        cats.addAll(Arrays.asList(Category.builder().name("fruits").build(),
                Category.builder().name("exotic").build(), Category.builder().name("dried").build()));

        when(categoryRepository.findAll()).thenReturn(cats);

        CategoryDTO found = categoryService.getCategoryByName("fruits");

        Assertions.assertAll(

                () -> {
                    Assertions.assertNotNull(found);
                },
                () -> {
                    Assertions.assertEquals(found.getName(), "fruits");
                });
    }
}
