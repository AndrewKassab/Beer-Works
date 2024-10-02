package andrewkassab.spring.spring_6_rest_mvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import andrewkassab.spring.spring_6_rest_mvc.model.BeerDTO;
import andrewkassab.spring.spring_6_rest_mvc.model.BeerStyle;
import org.springframework.data.domain.Page;

public interface BeerService {
	
	public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);

	public Optional<BeerDTO> getBeerById(UUID id);

	public BeerDTO saveNewBeer(BeerDTO beer);

	public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

	public boolean deleteById(UUID beerId);

	public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);

}
