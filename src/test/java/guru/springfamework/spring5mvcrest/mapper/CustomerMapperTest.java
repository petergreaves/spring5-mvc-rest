package guru.springfamework.spring5mvcrest.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.mapper.CategoryMapper;
import guru.springfamework.mapper.CustomerMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerMapperTest {

    public static final String JOE = "joe";
    public static final String SMITH = "smith";
    public static final long ID = 1L;

    @Test
    public void testCustomerMapper() {

        Customer joe = Customer.builder().firstName(JOE).lastName(SMITH).id(ID).build();
        CustomerDTO joeDTO = CustomerMapper.INSTANCE.customerToCustomerDTO(joe);

        Assertions.assertAll(
                () -> {
                    Assertions.assertEquals(ID, joeDTO.getId());
                    Assertions.assertEquals(JOE, joeDTO.getFirstName());
                    Assertions.assertEquals(SMITH, joeDTO.getLastName());
                }
        );


    }
}
