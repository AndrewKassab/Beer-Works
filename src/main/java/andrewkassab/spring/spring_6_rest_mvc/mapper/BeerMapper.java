package andrewkassab.spring.spring_6_rest_mvc.mapper;

import andrewkassab.spring.spring_6_rest_mvc.entity.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import andrewkassab.spring.spring_6_rest_mvc.model.BeerDTO;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BeerMapper {

    public Beer beerDtoToBeer(BeerDTO beerDto);

    public BeerDTO beerToBeerDto(Beer beer);

    public void updateBeerFromDto(@MappingTarget Beer existingBeer, BeerDTO updatedBeer);

}
