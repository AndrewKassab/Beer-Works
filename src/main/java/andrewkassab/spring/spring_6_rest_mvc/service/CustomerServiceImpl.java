package andrewkassab.spring.spring_6_rest_mvc.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import andrewkassab.spring.spring_6_rest_mvc.mapper.GenericMapper;
import andrewkassab.spring.spring_6_rest_mvc.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	private Map<UUID, Customer> customerMap;
	
	private GenericMapper<Customer> mapper;
	
	public CustomerServiceImpl() {
		customerMap = new HashMap<>();
		
		var customerOne = Customer.builder()
				.id(UUID.randomUUID())
				.customerName("Customer 1")
				.version(1)
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();
			
		var customerTwo = Customer.builder()
				.id(UUID.randomUUID())
				.customerName("Customer 2")
				.version(1)
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();

		var customerThree = Customer.builder()
				.id(UUID.randomUUID())
				.customerName("Customer 3")
				.version(1)
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();
		
		customerMap.put(customerOne.getId(), customerOne);
		customerMap.put(customerTwo.getId(), customerTwo);
		customerMap.put(customerThree.getId(), customerThree);
		
	}

	@Override
	public List<Customer> listCustomers() {
		return new ArrayList<>(customerMap.values());
	}

	@Override
	public Customer getCustomerById(UUID id) {
		return customerMap.get(id);
	}

	@Override
	public Customer saveNewCustomer(Customer customer) {
		customer.setId(UUID.randomUUID());
		customer.setCreatedDate(LocalDateTime.now());
		customer.setLastModifiedDate(LocalDateTime.now());
		
		customerMap.put(customer.getId(), customer);
		return customer;
	}

	@Override
	public void updateById(UUID customerId, Customer customer) {
		var existing = customerMap.get(customerId);
		existing.setCustomerName(customer.getCustomerName());
	}

	@Override
	public void deleteById(UUID customerId) {
		customerMap.remove(customerId);
	}

	@Override
	public void patchCustomerById(UUID customerId, Customer customer) {
		var existing = customerMap.get(customer);
		
		if (existing != null) {
			mapper.updateEntityFromDto(customer, existing);
		}
	}

}
