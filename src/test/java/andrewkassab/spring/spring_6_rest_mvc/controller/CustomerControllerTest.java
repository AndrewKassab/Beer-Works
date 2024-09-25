package andrewkassab.spring.spring_6_rest_mvc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import andrewkassab.spring.spring_6_rest_mvc.model.Customer;
import andrewkassab.spring.spring_6_rest_mvc.service.CustomerService;
import andrewkassab.spring.spring_6_rest_mvc.service.CustomerServiceImpl;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockitoBean
	CustomerService customerService;
	
	CustomerServiceImpl customerServiceImpl;
	
	@BeforeEach
	public void setUp() {
		customerServiceImpl = new CustomerServiceImpl();
		objectMapper.findAndRegisterModules();
	}
	
	@Test
	public void testCreateCustomer() throws Exception {
		var customer = customerServiceImpl.listCustomers().get(0);
		
		Mockito.when(customerService.saveNewCustomer(customer)).thenReturn(customer);
		
		mockMvc.perform(post("/api/v1/customer")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(customer)))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"));
	}

	@Test
	public void getCustomerById() throws Exception {
		var expectedCustomer = Customer.builder()
				.id(UUID.randomUUID())
				.customerName("Test name")
				.build();
		
		Mockito.when(customerService.getCustomerById(expectedCustomer.getId())).thenReturn(expectedCustomer);

		mockMvc.perform(get("/api/v1/customer/" + expectedCustomer.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", Is.is(expectedCustomer.getId().toString())))
				.andExpect(jsonPath("$.customerName", Is.is(expectedCustomer.getCustomerName())));
	}
	
	@Test
	public void testListCustomers() throws Exception {
		var expectedCustomerList = customerServiceImpl.listCustomers();
		Mockito.when(customerService.listCustomers()).thenReturn(expectedCustomerList);
		
		var result = mockMvc.perform(get("/api/v1/customer")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", Is.is(3)))
				.andReturn();

		var jsonResponse = result.getResponse().getContentAsString();
		
		List<Customer> customerList = objectMapper.readValue(jsonResponse, new TypeReference<List<Customer>>() {});

		assertEquals(customerServiceImpl.listCustomers(), customerList);
	}

}
