package andrewkassab.spring.spring_6_rest_mvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import andrewkassab.spring.spring_6_rest_mvc.model.BeerDTO;

public interface BeerService {
	
	public List<BeerDTO> listBeers();

	public Optional<BeerDTO> getBeerById(UUID id);

	public BeerDTO saveNewBeer(BeerDTO beer);

	public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

	public boolean deleteById(UUID beerId);

	public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);

}
