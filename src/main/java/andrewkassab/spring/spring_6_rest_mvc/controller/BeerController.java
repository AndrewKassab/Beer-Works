package andrewkassab.spring.spring_6_rest_mvc.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import andrewkassab.spring.spring_6_rest_mvc.model.Beer;
import andrewkassab.spring.spring_6_rest_mvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {
				
	@Autowired
	private final BeerService beerService;
	
	private Beer getBeerById(UUID id) {
		log.debug("Get Beer by Id - in controller");
		
		return beerService.getBeerById(id);
	}

}
