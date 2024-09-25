package andrewkassab.spring.spring_6_rest_mvc.mapper;

import andrewkassab.spring.spring_6_rest_mvc.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

    void updateCustomerFromDto(Customer updatedCustomer, @MappingTarget Customer existingCustomer);

}
