package guru.springframework.services;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;

import guru.springframework.domain.Customer;
import guru.springframework.mapper.CustomerMapper;
import guru.springframework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerListDTO getAllCustomers() {
        return new CustomerListDTO(customerRepository
                .findAll()
                .stream()
                .map(customer -> {
                            CustomerDTO dto = customerMapper.customerToCustomerDTO(customer);
                            dto.setCustomerURL("/api/v1/customers/" + dto.getId());
                            return dto;
                        }
                )
                .collect(Collectors.toList()));
    }

    @Override
    public CustomerDTO getCustomerByID(Long id) {
        return customerRepository
                .findById(id)
                .map(customer -> {
                    CustomerDTO dto = customerMapper.customerToCustomerDTO(customer);
                    dto.setCustomerURL("/api/v1/customers/" + dto.getId());
                    return dto;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO in) {
        return saveAndUpdate(in);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO in) {

        // TODO what if the  customer ID doesnt exist?  throw something
        in.setId(id);
        return saveAndUpdate(in);
    }

    private CustomerDTO saveAndUpdate(CustomerDTO in) {
        Customer updated = customerRepository.save(customerMapper.customerDTOToCustomer(in));
        return customerMapper.customerToCustomerDTO(updated);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {


        return customerRepository.findById(id).map(customer -> {

            if (customerDTO.getFirstName() != null) {
                customer.setFirstName(customerDTO.getFirstName());
            }

            if (customerDTO.getLastName() != null) {
                customer.setLastName(customerDTO.getLastName());
            }
            CustomerDTO retval = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
            retval.setCustomerURL("/api/v1/customers/" + id);

            return retval;
        }).orElseThrow(RuntimeException::new); //todo implement better exception handling;
    }

    @Override
    public void deleteCustomerByID(Long id) {

        customerRepository.deleteById(id);

    }
}
