package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;

import guru.springfamework.domain.Customer;
import guru.springfamework.mapper.CustomerMapper;
import guru.springfamework.repositories.CustomerRepository;
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
                .orElseThrow(RuntimeException::new);
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


    private CustomerDTO saveAndUpdate(CustomerDTO in){
        Customer updated = customerRepository.save(customerMapper.customerDTOToCustomer(in));
        return customerMapper.customerToCustomerDTO(updated);

    }
}
