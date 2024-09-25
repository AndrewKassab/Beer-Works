package andrewkassab.spring.spring_6_rest_mvc.repository;

import andrewkassab.spring.spring_6_rest_mvc.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer savedCustomer = customerRepository.save(Customer.builder().name("My Customer").build());

        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
    }

}