package andrewkassab.spring.spring_6_rest_mvc.mapper;

import andrewkassab.spring.spring_6_rest_mvc.entity.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import andrewkassab.spring.spring_6_rest_mvc.model.BeerDTO;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDto);

    BeerDTO beerToBeerDto(Beer beer);

    void updateBeerFromDto(BeerDTO updatedBeer, @MappingTarget BeerDTO existingBeer);

}
