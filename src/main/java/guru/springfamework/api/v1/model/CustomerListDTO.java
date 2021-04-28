package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CustomerListDTO {

    public CustomerListDTO(List<CustomerDTO> customerDTOList) {
        this.customerDTOList = customerDTOList;
    }

    @JsonProperty("customers")
    private List<CustomerDTO> customerDTOList;


}
