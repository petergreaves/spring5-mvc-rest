package guru.springframework.services;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;

public interface CustomerService {

    CustomerListDTO getAllCustomers();
    CustomerDTO getCustomerByID(Long id);
    CustomerDTO createCustomer(CustomerDTO in);
    CustomerDTO updateCustomer(Long id, CustomerDTO in);
    CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);
    void deleteCustomerByID(Long id);
}
