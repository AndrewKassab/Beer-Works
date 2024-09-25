package andrewkassab.spring.spring_6_rest_mvc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import andrewkassab.spring.spring_6_rest_mvc.model.Beer;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GenericMapper<T> {
	
	void updateEntityFromDto(T updated, @MappingTarget T existing);

}
