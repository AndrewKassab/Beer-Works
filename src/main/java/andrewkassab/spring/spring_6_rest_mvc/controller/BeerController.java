package andrewkassab.spring.spring_6_rest_mvc.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import andrewkassab.spring.spring_6_rest_mvc.model.Beer;
import andrewkassab.spring.spring_6_rest_mvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
				
	@Autowired
	private final BeerService beerService;
	
	@DeleteMapping("/{beerId}")
	public ResponseEntity<?> deleteById(@PathVariable UUID beerId) {
		beerService.deleteById(beerId);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{beerId}")
	public ResponseEntity<?> updateBeerPatchById(@PathVariable UUID beerId, @RequestBody Beer beer) {
		beerService.patchBeerById(beerId, beer);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{beerId}")
	public ResponseEntity<?> updateById(@PathVariable UUID beerId, @RequestBody Beer beer) {
		
		beerService.updateBeerById(beerId, beer);

		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<?> handlePost(@RequestBody Beer beer) {
		Beer savedBeer = beerService.saveNewBeer(beer);
		
		return ResponseEntity.created(URI.create("/api/v1/beer/" + savedBeer.getId().toString())).build();
	}

	@GetMapping
	public List<Beer> listBeers() {
		return beerService.listBeers();
	}

	@GetMapping("/{beerId}")
	public Beer getBeerById(@PathVariable UUID beerId) {
		log.debug("Get Beer by Id - in controller");
		
		return beerService.getBeerById(beerId);
	}

}
