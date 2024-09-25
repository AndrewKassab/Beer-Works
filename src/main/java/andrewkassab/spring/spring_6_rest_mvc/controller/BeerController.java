package andrewkassab.spring.spring_6_rest_mvc.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import andrewkassab.spring.spring_6_rest_mvc.exception.NotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import andrewkassab.spring.spring_6_rest_mvc.model.Beer;
import andrewkassab.spring.spring_6_rest_mvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {
	
	public static final String BEER_PATH = "/api/v1/beer";
	public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
				
	@Autowired
	private final BeerService beerService;
	
	@DeleteMapping(BEER_PATH_ID)
	public ResponseEntity<?> deleteById(@PathVariable UUID beerId) {
		beerService.deleteById(beerId);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping(BEER_PATH_ID)
	public ResponseEntity<?> updateBeerPatchById(@PathVariable UUID beerId, @RequestBody Beer beer) {
		beerService.patchBeerById(beerId, beer);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(BEER_PATH_ID)
	public ResponseEntity<?> updateById(@PathVariable UUID beerId, @RequestBody Beer beer) {
		
		beerService.updateBeerById(beerId, beer);

		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(BEER_PATH)
	public ResponseEntity<?> handlePost(@RequestBody Beer beer) {
		Beer savedBeer = beerService.saveNewBeer(beer);
		
		return ResponseEntity.created(URI.create("/api/v1/beer/" + savedBeer.getId().toString())).build();
	}

	@GetMapping(BEER_PATH)
	public List<Beer> listBeers() {
		return beerService.listBeers();
	}

	@GetMapping(BEER_PATH_ID)
	public Beer getBeerById(@PathVariable UUID beerId) {
		log.debug("Get Beer by Id - in controller");
		
		return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
	}

}
