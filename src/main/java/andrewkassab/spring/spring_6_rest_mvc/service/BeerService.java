package andrewkassab.spring.spring_6_rest_mvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import andrewkassab.spring.spring_6_rest_mvc.model.Beer;

public interface BeerService {
	
	public List<Beer> listBeers();

	public Optional<Beer> getBeerById(UUID id);

	public Beer saveNewBeer(Beer beer);

	public void updateBeerById(UUID beerId, Beer beer);

	public void deleteById(UUID beerId);

	public void patchBeerById(UUID beerId, Beer beer);

}
