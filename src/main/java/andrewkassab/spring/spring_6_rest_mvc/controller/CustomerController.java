package andrewkassab.spring.spring_6_rest_mvc.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import andrewkassab.spring.spring_6_rest_mvc.model.Customer;
import andrewkassab.spring.spring_6_rest_mvc.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PatchMapping("/{customerId}")
	public ResponseEntity<?> updateCustomerPatchById(@PathVariable UUID customerId, @RequestBody Customer customer) {
		customerService.patchCustomerById(customerId, customer);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity deleteById(@PathVariable UUID customerId) {
		customerService.deleteById(customerId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{customerId}")
	public ResponseEntity<?> updateById(@PathVariable UUID customerId, @RequestBody Customer customer) {
		customerService.updateById(customerId, customer);
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<?> handlePost(@RequestBody Customer customer) {
		Customer newCustomer = customerService.saveNewCustomer(customer);
		
		return ResponseEntity.created(URI.create("/api/v1/customer/" + newCustomer.getId())).build();
	}

	@GetMapping
	public List<Customer> listCustomers() {
		return customerService.listCustomers();
	}
	
	@GetMapping("/{customerId}")
	public Customer getCustomerById(@PathVariable UUID customerId) {
		return customerService.getCustomerById(customerId);
	}

}
