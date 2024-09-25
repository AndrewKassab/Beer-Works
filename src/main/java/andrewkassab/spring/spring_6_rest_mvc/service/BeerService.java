package andrewkassab.spring.spring_6_rest_mvc.service;

import java.util.List;
import java.util.UUID;

import andrewkassab.spring.spring_6_rest_mvc.model.Beer;

public interface BeerService {
	
	public List<Beer> listBeers();

	public Beer getBeerById(UUID id);

}
