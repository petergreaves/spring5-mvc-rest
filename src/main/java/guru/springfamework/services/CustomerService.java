package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;

public interface CustomerService {

    CustomerListDTO getAllCustomers();
    CustomerDTO getCustomerByID(Long id);
    CustomerDTO createCustomer(CustomerDTO in);
    CustomerDTO updateCustomer(Long id, CustomerDTO in);
    CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);
    void deleteCustomerByID(Long id);
}
