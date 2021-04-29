package guru.springfamework.controllers.v1;


import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import guru.springfamework.services.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends AbstractRestControllerTest {


    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController controller;

    private MockMvc mockMvc;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();

    }

    @Test
    public void getAllCustomers() throws Exception {

        List<CustomerDTO> customers = new ArrayList<>();

        CustomerDTO joe = new CustomerDTO();
        joe.setFirstName("joe");
        joe.setLastName("smith");
        joe.setId(1L);

        CustomerDTO anne = new CustomerDTO();
        anne.setId(22L);
        anne.setFirstName("anne");
        anne.setLastName("jones");

        CustomerDTO peter = new CustomerDTO();
        peter.setId(3L);
        peter.setFirstName("peter");
        peter.setLastName("perfect");

        customers.add(joe);
        customers.add(peter);
        customers.add(anne);

        CustomerListDTO customerListDTO = new CustomerListDTO(customers);
        when(customerService.getAllCustomers()).thenReturn(customerListDTO);


        mockMvc.perform(get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)));

    }


    @Test
    public void getCustomerByID() throws Exception {


        CustomerDTO peter = new CustomerDTO();
        peter.setId(3L);
        peter.setFirstName("peter");
        peter.setLastName("perfect");

        when(customerService.getCustomerByID(3L)).thenReturn(peter);

        mockMvc.perform(get(CustomerController.BASE_URL + "/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("peter")));
    }

    @Test
    public void createCustomer() throws Exception {

        CustomerDTO peterIn = new CustomerDTO();
        peterIn.setFirstName("peter");
        peterIn.setLastName("perfect");

        CustomerDTO peterSaved = new CustomerDTO();
        peterSaved.setId(3L);
        peterSaved.setFirstName("peter");
        peterSaved.setLastName("perfect");

        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(peterSaved);

        mockMvc.perform(post(CustomerController.BASE_URL)
                .content(asJsonString(peterIn))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.firstname", equalTo("peter")))
                .andExpect(jsonPath("$.lastname", equalTo("perfect")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/3")));

    }

    @Test
    public void updateCustomer() throws Exception {

        CustomerDTO peterNewVersion = new CustomerDTO();
        peterNewVersion.setFirstName("peterNew");
        peterNewVersion.setLastName("perfectNew");

        final Long id = 399L;
        final String url = CustomerController.BASE_URL + "/3";

        CustomerDTO peterUpdated = new CustomerDTO();
        peterUpdated.setId(id);
        peterUpdated.setFirstName("peterNew");
        peterUpdated.setLastName("peterNew");
        peterUpdated.setCustomerURL(url);

        when(customerService.updateCustomer(any(Long.class), any(CustomerDTO.class))).thenReturn(peterUpdated);

        mockMvc.perform(put(CustomerController.BASE_URL + "/399")
                .content(asJsonString(peterNewVersion))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(peterUpdated.getFirstName())))
                .andExpect(jsonPath("$.lastname", equalTo(peterUpdated.getLastName())))
                .andExpect(jsonPath("$.customer_url", equalTo(peterUpdated.getCustomerURL())));
    }

    @Test
    public void patchCustomer() throws Exception {

        CustomerDTO peterNewVersion = new CustomerDTO();
        peterNewVersion.setFirstName("peterPatched");


        final Long id = 399L;
        final String url = CustomerController.BASE_URL + "/3";

        CustomerDTO peterUpdated = new CustomerDTO();
        peterUpdated.setId(id);
        peterUpdated.setFirstName("peterPatched");
        peterUpdated.setLastName("peterNew");
        peterUpdated.setCustomerURL(url);

        when(customerService.patchCustomer(any(Long.class), any(CustomerDTO.class))).thenReturn(peterUpdated);

        mockMvc.perform(patch(CustomerController.BASE_URL + "/399")
                .content(asJsonString(peterNewVersion))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(peterUpdated.getFirstName())))
                .andExpect(jsonPath("$.lastname", equalTo(peterUpdated.getLastName())))
                .andExpect(jsonPath("$.customer_url", equalTo(peterUpdated.getCustomerURL())));
    }

    @Test
    public void deleteCustomer() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerByID(any(Long.class));

    }

    @Test
    public void testNotFoundException() throws Exception {

        when(customerService.getCustomerByID(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
    }

}