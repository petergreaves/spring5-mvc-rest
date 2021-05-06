package guru.springframework.spring5mvcrest.services;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Customer;
import guru.springframework.mapper.CustomerMapper;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import guru.springframework.services.CustomerService;
import guru.springframework.services.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VendorRepository vendorRepository;

    private CustomerService customerService;

    @Test
    public void dataContextLoads(){


    }

    @BeforeEach
    public void setup() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }



    @Test
    public void patchUpdateFirstName(){


        Customer newCustomer= new Customer();
        newCustomer.setLastName("smith");
        newCustomer.setFirstName("john");

        Customer saved=customerRepository.save(newCustomer);
        Long id = saved.getId();

        CustomerDTO updateDTO= new CustomerDTO();
        updateDTO.setFirstName("jonny");
        updateDTO.setId(id);

        CustomerDTO updated= customerService.patchCustomer(id, updateDTO);
        Assertions.assertEquals(updated.getFirstName(), "jonny");
        Assertions.assertEquals(updated.getLastName(), "smith");

    }

    @Test
    public void patchUpdateLastName(){


        Customer newCustomer= new Customer();
        newCustomer.setLastName("smith");
        newCustomer.setFirstName("john");

        Customer saved=customerRepository.save(newCustomer);
        Long id = saved.getId();

        CustomerDTO updateDTO= new CustomerDTO();
        updateDTO.setLastName("jones");
        updateDTO.setId(id);

        CustomerDTO updated= customerService.patchCustomer(id, updateDTO);
        Assertions.assertEquals(updated.getFirstName(), "john");
        Assertions.assertEquals(updated.getLastName(), "jones");

    }
    private Long getID(){

        return customerRepository.findAll().stream().findFirst().get().getId();
    }
}
