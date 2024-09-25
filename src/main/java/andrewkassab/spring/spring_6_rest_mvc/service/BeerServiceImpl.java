package andrewkassab.spring.spring_6_rest_mvc.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import andrewkassab.spring.spring_6_rest_mvc.model.Beer;
import andrewkassab.spring.spring_6_rest_mvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
	
	public Beer getBeerById(UUID id) {
		
		log.debug("Get Beer Id in service was called");

		return Beer.builder()
				.id(id)
				.version(1)
				.beerName("Galaxy Cat")
				.beerStyle(BeerStyle.PALE_ALE)
				.upc("12356")
				.price(new BigDecimal("12.99"))
				.quantityOnHand(122)
				.createdDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.build();
	}

}
