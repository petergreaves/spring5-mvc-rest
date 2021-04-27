package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;



@Getter
public class CategoryListDTO {


    public CategoryListDTO(List<CategoryDTO> categoryDTOList) {
        this.categoryDTOList = categoryDTOList;
    }

    @JsonProperty("categories")
    private List<CategoryDTO> categoryDTOList;

}
