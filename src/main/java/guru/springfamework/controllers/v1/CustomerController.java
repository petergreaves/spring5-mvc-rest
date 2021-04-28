package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller
@Slf4j
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAlLCustomers(){

        return new ResponseEntity(customerService.getAllCustomers(), HttpStatus.OK);

    }


    @GetMapping({"/{id}"})
    public ResponseEntity<CustomerDTO> getCustomerByID(@PathVariable String id){

        return new ResponseEntity(customerService.getCustomerByID(Long.valueOf(id)), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO in){

        CustomerDTO out = customerService.createCustomer(in);
        out.setCustomerURL("/api/v1/customers/" + out.getId());
        return new ResponseEntity(out, HttpStatus.CREATED);

    }

    @PutMapping({"{id}"})
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO in){

        CustomerDTO out = customerService.updateCustomer(id, in);
        out.setCustomerURL("/api/v1/customers/" + out.getId());
        return new ResponseEntity(out, HttpStatus.OK);

    }
    @PatchMapping ({"{id}"})
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO in){

        CustomerDTO out = customerService.patchCustomer(id, in);
        out.setCustomerURL("/api/v1/customers/" + out.getId());
        return new ResponseEntity(out, HttpStatus.OK);

    }

}
