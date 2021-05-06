package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CategoryListDTO;
import guru.springframework.services.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController controller;

    private MockMvc mockMvc;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();

    }


    @Test
    public void testListCategories() throws Exception {

        List<CategoryDTO> catDTOs = new ArrayList<>();

        CategoryDTO dto1 = new CategoryDTO();
        dto1.setId(1L);
        dto1.setName("name1");
        catDTOs.add(dto1);

        CategoryDTO dto2 = new CategoryDTO();
        dto2.setId(2L);
        dto2.setName("name2");
        catDTOs.add(dto2);

        CategoryListDTO list = new CategoryListDTO(catDTOs);
        when(categoryService.getAllCategories()).thenReturn(list);

        mockMvc.perform(get("/api/v1/categories/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));

    }

    @Test
    public void testGetCategoryByName() throws Exception {

        CategoryDTO dto1 = new CategoryDTO();
        dto1.setId(99L);
        dto1.setName("name1");

        when(categoryService.getCategoryByName(anyString())).thenReturn(dto1);

        mockMvc.perform(get("/api/v1/categories/name1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("name1")))
                .andExpect(jsonPath("$.id", equalTo(99)));

    }


}