package andrewkassab.spring.spring_6_rest_mvc.mapper;

import andrewkassab.spring.spring_6_rest_mvc.entity.Customer;
import andrewkassab.spring.spring_6_rest_mvc.model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDto);

    CustomerDTO customerToCustomerDto(Customer customer);

    void updateCustomerFromDto(@MappingTarget Customer existingCustomer, CustomerDTO updatedCustomer);

}