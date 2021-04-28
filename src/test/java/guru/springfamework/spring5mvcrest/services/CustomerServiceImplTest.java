package guru.springfamework.spring5mvcrest.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.mapper.CustomerMapper;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.services.CustomerService;
import guru.springfamework.services.CustomerServiceImpl;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerServiceImplTest {

    CustomerService customerService;

    CustomerMapper customerMapper;

    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);
        customerMapper = CustomerMapper.INSTANCE;
        customerService = new CustomerServiceImpl(customerMapper, customerRepository);


    }


    @Test
    public void getAllCustomers() {

        List<Customer> customers = new ArrayList<>();

        customers.addAll(Arrays.asList(new Customer(), new Customer(), new Customer()));
        when(customerRepository.findAll()).thenReturn(customers);
        CustomerListDTO custDTOList = customerService.getAllCustomers();

        Assertions.assertEquals(custDTOList.getCustomerDTOList().size(), customers.size());


    }


    @Test
    public void getCustomerByID() {

        Customer anne = Customer.builder().firstName("anne").lastName("jones").id(2L).build();

        when(customerRepository.findById(2L)).thenReturn(Optional.of(anne));

        CustomerDTO found = customerService.getCustomerByID(2L);

        Assertions.assertAll(

                () -> {
                    Assertions.assertNotNull(found);
                },
                () -> {
                    Assertions.assertEquals(found.getFirstName(), "anne");
                });
    }

    @Test
    public void createCustomer() {

        CustomerDTO dtoIn= new CustomerDTO();
        dtoIn.setId(1L);
        dtoIn.setLastName("smith");
        dtoIn.setFirstName("john");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastName("smith");
        customer.setFirstName("john");


        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO dtoExpected=customerService.createCustomer(dtoIn);

        Assertions.assertAll(
                () -> {
                    Assertions.assertEquals(dtoExpected.getFirstName(), dtoIn.getFirstName());
                    Assertions.assertEquals(dtoExpected.getLastName(), dtoIn.getLastName());
                    Assertions.assertEquals(dtoExpected.getId(), dtoIn.getId());
                });
    }


    @Test
    public void updateCustomer() {

        CustomerDTO current= new CustomerDTO();
        current.setLastName("smith");
        current.setFirstName("john");
        current.setId(99L);

        CustomerDTO updatedDTO= new CustomerDTO();
        updatedDTO.setLastName("smithUpdated");
        updatedDTO.setFirstName("johnUpdated");
        updatedDTO.setId(99L);

        Customer returned = new Customer();
        returned.setLastName("smithUpdated");
        returned.setFirstName("johnUpdated");
        returned.setId(99L);
        when(customerRepository.save(any(Customer.class))).thenReturn(returned);

        CustomerDTO dtoFromUpdateService=customerService.updateCustomer(current.getId(), updatedDTO);

        Assertions.assertAll(
                () -> {
                    Assertions.assertEquals(dtoFromUpdateService.getFirstName(), updatedDTO.getFirstName());
                    Assertions.assertEquals(dtoFromUpdateService.getLastName(), updatedDTO.getLastName());
                    Assertions.assertEquals(dtoFromUpdateService.getId(), updatedDTO.getId());
                    Assertions.assertEquals(dtoFromUpdateService.getId(), current.getId());
                });
    }

}