package andrewkassab.spring.spring_6_rest_mvc.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import andrewkassab.spring.spring_6_rest_mvc.model.CustomerDTO;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import andrewkassab.spring.spring_6_rest_mvc.service.CustomerService;
import andrewkassab.spring.spring_6_rest_mvc.service.CustomerServiceImpl;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@MockitoBean
	CustomerService customerService;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	CustomerServiceImpl customerServiceImpl;

	@BeforeEach
	void setUp() {
		customerServiceImpl = new CustomerServiceImpl();
	}

	@Captor
	ArgumentCaptor<UUID> uuidArgumentCaptor;

	@Captor
	ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

	@Test
	void testPatchCustomer() throws Exception {
		CustomerDTO customer = customerServiceImpl.listCustomers().get(0);

		Map<String, Object> customerMap = new HashMap<>();
		customerMap.put("name", "New Name");

		mockMvc.perform(patch( CustomerController.CUSTOMER_PATH_ID, customer.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customerMap)))
				.andExpect(status().isNoContent());

		verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(),
				customerArgumentCaptor.capture());

		assertThat(uuidArgumentCaptor.getValue()).isEqualTo(customer.getId());
		assertThat(customerArgumentCaptor.getValue().getName()).isEqualTo(customerMap.get("name"));
	}

	@Test
	void testDeleteCustomer() throws Exception {
		CustomerDTO customer = customerServiceImpl.listCustomers().get(0);

		mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, customer.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());

		assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
	}

	@Test
	void testUpdateCustomer() throws Exception {
		CustomerDTO customer = customerServiceImpl.listCustomers().get(0);

		mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customer.getId())
						.content(objectMapper.writeValueAsString(customer))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		verify(customerService).updateCustomerById(uuidArgumentCaptor.capture(), any(CustomerDTO.class));

		assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
	}

	@Test
	void testCreateCustomer() throws Exception {
		CustomerDTO customer = customerServiceImpl.listCustomers().get(0);
		customer.setId(null);
		customer.setVersion(null);

		Mockito.when(customerService.saveNewCustomer(any(CustomerDTO.class)))
				.thenReturn(customerServiceImpl.listCustomers().get(1));

		mockMvc.perform(post(CustomerController.CUSTOMER_PATH).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer)))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"));
	}

	@Test
	void listAllCustomers() throws Exception {
		Mockito.when(customerService.listCustomers()).thenReturn(customerServiceImpl.listCustomers());

		mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", Is.is(3)));
	}

	@Test
	void getCustomerByIdNotFound() throws Exception {

		Mockito.when(customerService.getCustomerById(any(UUID.class))).thenReturn(Optional.empty());

		mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
				.andExpect(status().isNotFound());
	}

	@Test
	void getCustomerById() throws Exception {
		CustomerDTO customer = customerServiceImpl.listCustomers().get(0);

		Mockito.when(customerService.getCustomerById(customer.getId())).thenReturn(Optional.of(customer));

		mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, customer.getId())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name", Is.is(customer.getName())));
	}
}
