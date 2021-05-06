package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(produces = "This is the customer controller")
@RestController
@Slf4j
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    private final CustomerService customerService;

    public static final String BASE_URL = "/api/v1/customers";

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "This will get a list of customers.", notes = "These are some notes about the API.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAlLCustomers(){

        return customerService.getAllCustomers();

    }


    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerByID(@PathVariable String id){

        return customerService.getCustomerByID(Long.valueOf(id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody CustomerDTO in){

        CustomerDTO created = customerService.createCustomer(in);
        created.setCustomerURL(BASE_URL + "/" + created.getId());
        return created;

    }

    @PutMapping({"{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO in){

        CustomerDTO updated = customerService.updateCustomer(id, in);
        updated.setCustomerURL(BASE_URL+"/" + updated.getId());
        return updated;

    }
    @PatchMapping ({"{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO in){

        CustomerDTO patched = customerService.patchCustomer(id, in);
        patched.setCustomerURL(BASE_URL+"/" + patched.getId());
        return patched;

    }

    @DeleteMapping ({"{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id){

        customerService.deleteCustomerByID(id);


    }
}
