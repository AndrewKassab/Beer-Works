package andrewkassab.spring.spring_6_rest_mvc.controller;

import andrewkassab.spring.spring_6_rest_mvc.exception.NotFoundException;
import andrewkassab.spring.spring_6_rest_mvc.model.CustomerDTO;
import andrewkassab.spring.spring_6_rest_mvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CustomerController {
	public static final String CUSTOMER_PATH = "/api/v1/customer";
	public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

	private final CustomerService customerService;

	@PatchMapping(CUSTOMER_PATH_ID)
	public ResponseEntity patchCustomerById(@PathVariable("customerId") UUID customerId,
											@RequestBody CustomerDTO customer){

		customerService.patchCustomerById(customerId, customer);

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(CUSTOMER_PATH_ID)
	public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID customerId){

		if (customerService.deleteCustomerById(customerId)) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		throw new NotFoundException();
	}

	@PutMapping(CUSTOMER_PATH_ID)
	public ResponseEntity updateCustomerByID(@PathVariable("customerId") UUID customerId,
											 @RequestBody CustomerDTO customer){

		if (customerService.updateCustomerById(customerId, customer).isPresent()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		throw new NotFoundException();

	}

	@PostMapping(CUSTOMER_PATH)
	public ResponseEntity handlePost(@RequestBody CustomerDTO customer){
		CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", CUSTOMER_PATH + "/" + savedCustomer.getId().toString());

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@GetMapping(CUSTOMER_PATH)
	public List<CustomerDTO> listAllCustomers(){
		return customerService.getAllCustomers();
	}

	@GetMapping(value = CUSTOMER_PATH_ID)
	public CustomerDTO getCustomerById(@PathVariable("customerId") UUID id){
		return customerService.getCustomerById(id).orElseThrow(NotFoundException::new);
	}

}