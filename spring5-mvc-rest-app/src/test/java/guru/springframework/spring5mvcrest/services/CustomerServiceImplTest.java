package guru.springframework.spring5mvcrest.services;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.domain.Customer;
import guru.springframework.mapper.CustomerMapper;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.services.CustomerService;
import guru.springframework.services.CustomerServiceImpl;
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



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.mockito.BDDMockito.then;

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

        assertEquals(custDTOList.getCustomerDTOList().size(), customers.size());


    }

    @Test
    public void getAllCustomersAlt() {

        List<Customer> customers = new ArrayList<>();
        customers.addAll(Arrays.asList(new Customer(), new Customer(), new Customer()));
        given(customerRepository.findAll()).willReturn(customers);

        CustomerListDTO custDTOList = customerService.getAllCustomers();
        then(customerRepository.findAll());//.//sh != custDTOList.getCustomerDTOList().size());
        assertEquals(custDTOList.getCustomerDTOList().size(), customers.size());


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
                    assertEquals(found.getFirstName(), "anne");
                });
    }

    @Test
    public void createCustomer() {

        CustomerDTO dtoIn = new CustomerDTO();
        dtoIn.setId(1L);
        dtoIn.setLastName("smith");
        dtoIn.setFirstName("john");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastName("smith");
        customer.setFirstName("john");


        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO dtoExpected = customerService.createCustomer(dtoIn);

        Assertions.assertAll(
                () -> {
                    assertEquals(dtoExpected.getFirstName(), dtoIn.getFirstName());
                    assertEquals(dtoExpected.getLastName(), dtoIn.getLastName());
                    assertEquals(dtoExpected.getId(), dtoIn.getId());
                });
    }


    @Test
    public void updateCustomer() {

        CustomerDTO current = new CustomerDTO();
        current.setLastName("smith");
        current.setFirstName("john");
        current.setId(99L);

        CustomerDTO updatedDTO = new CustomerDTO();
        updatedDTO.setLastName("smithUpdated");
        updatedDTO.setFirstName("johnUpdated");
        updatedDTO.setId(99L);

        Customer returned = new Customer();
        returned.setLastName("smithUpdated");
        returned.setFirstName("johnUpdated");
        returned.setId(99L);
        when(customerRepository.save(any(Customer.class))).thenReturn(returned);

        CustomerDTO dtoFromUpdateService = customerService.updateCustomer(current.getId(), updatedDTO);

        Assertions.assertAll(
                () -> {
                    assertEquals(dtoFromUpdateService.getFirstName(), updatedDTO.getFirstName());
                    assertEquals(dtoFromUpdateService.getLastName(), updatedDTO.getLastName());
                    assertEquals(dtoFromUpdateService.getId(), updatedDTO.getId());
                    assertEquals(dtoFromUpdateService.getId(), current.getId());
                });
    }

    @Test
    public void deleteCustomer() {

        Long id = 1L;
        customerService.deleteCustomerByID(id);
        verify(customerRepository, times(1)).deleteById(any(Long.class));


    }

}