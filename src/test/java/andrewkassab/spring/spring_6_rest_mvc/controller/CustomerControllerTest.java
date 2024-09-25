package andrewkassab.spring.spring_6_rest_mvc.controller;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerControllerTest {
	
	@Autowired
	CustomerController customerController;
	
	@Test
	public void getCustomerById() {
		System.out.println(customerController.getCustomerById(UUID.randomUUID()));
	}

}
