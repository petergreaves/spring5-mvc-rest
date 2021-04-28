package guru.springfamework.spring5mvcrest.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.mapper.CustomerMapper;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.services.CustomerService;
import guru.springfamework.services.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private CustomerService customerService;

    @Test
    public void dataContextLoads(){


    }

    @BeforeEach
    public void setup() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
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
