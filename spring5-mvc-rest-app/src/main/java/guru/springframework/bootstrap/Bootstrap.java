package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadCategories();
        loadCustomers();
        loadVendors();

    }

    private void loadCategories() {
        Category fruits = Category.builder().name("Fruits").build();
        Category nuts = Category.builder().name("Nuts").build();
        Category dried = Category.builder().name("Dried").build();
        Category exotic = Category.builder().name("Exotic").build();
        Category fresh = Category.builder().name("Fresh").build();

        categoryRepository.save(fruits);
        categoryRepository.save(nuts);
        categoryRepository.save(fresh);
        categoryRepository.save(dried);
        categoryRepository.save(exotic);

        log.info("Category data loaded = {}", categoryRepository.count());
    }

    private void loadCustomers(){

        Customer joe = Customer.builder().firstName("joe").lastName("smith").id(1L).build();
        Customer anne = Customer.builder().firstName("anne").lastName("jones").id(2L).build();
        Customer peter = Customer.builder().firstName("peter").lastName("perfect").id(3L).build();

        customerRepository.save(joe);
        customerRepository.save(anne);
        customerRepository.save(peter);

        log.info("Customer data loaded = {}", customerRepository.count());


    }
    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor 1");
        vendorRepository.save(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor 2");
        vendorRepository.save(vendor2);

    }
}

