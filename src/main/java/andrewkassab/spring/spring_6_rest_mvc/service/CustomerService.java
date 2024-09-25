package andrewkassab.spring.spring_6_rest_mvc.service;

import java.util.List;
import java.util.UUID;

import andrewkassab.spring.spring_6_rest_mvc.model.Beer;
import andrewkassab.spring.spring_6_rest_mvc.model.Customer;

public interface CustomerService {
	
	public List<Customer> listCustomers();
	
	public Customer getCustomerById(UUID id);

	public Customer saveNewCustomer(Customer customer);

	public void updateById(UUID customerId, Customer customer);

	public void deleteById(UUID customerId);

	public void patchCustomerById(UUID customerId, Customer customer);

}
