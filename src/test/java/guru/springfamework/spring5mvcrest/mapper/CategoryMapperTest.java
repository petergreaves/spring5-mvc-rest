package guru.springfamework.spring5mvcrest.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.mapper.CategoryMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryMapperTest {

    public static final String FRUIT = "fruit";
    public static final long ID = 2;

    @Test
    public void testCategoryMapper() {

        Category fruit = Category.builder().name(FRUIT).id(ID).build();
        CategoryDTO fruitDTO = CategoryMapper.INSTANCE.categoryToCategoryDTO(fruit);

        Assertions.assertAll(
                () -> {
                    Assertions.assertNotNull(fruitDTO.getId());
                    Assertions.assertNotNull(fruitDTO.getName());
                    Assertions.assertEquals(fruit.getId(), fruitDTO.getId());
                    Assertions.assertEquals(fruit.getName(), fruitDTO.getName());
                }
        );


    }
}
