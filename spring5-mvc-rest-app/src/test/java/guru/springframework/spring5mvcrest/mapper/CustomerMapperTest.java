package guru.springframework.spring5mvcrest.mapper;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.mapper.CustomerMapper;
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
