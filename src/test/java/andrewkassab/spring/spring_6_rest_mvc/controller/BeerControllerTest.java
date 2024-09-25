package andrewkassab.spring.spring_6_rest_mvc.controller;

import java.util.List;
import java.util.UUID;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import andrewkassab.spring.spring_6_rest_mvc.model.Beer;
import andrewkassab.spring.spring_6_rest_mvc.service.BeerService;
import andrewkassab.spring.spring_6_rest_mvc.service.BeerServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BeerController.class)
public class BeerControllerTest {
	
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockitoBean
	BeerService beerService;
	
	BeerServiceImpl beerServiceImpl = new BeerServiceImpl();
	
	@Test
	public void getBeerById() throws Exception {
		var expectedBeer = Beer.builder()
				.id(UUID.randomUUID())
				.beerName("Test name")
				.build();
		
		Mockito.when(beerService.getBeerById(expectedBeer.getId())).thenReturn(expectedBeer);

		mockMvc.perform(get("/api/v1/beer/" + expectedBeer.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", Is.is(expectedBeer.getId().toString())))
				.andExpect(jsonPath("$.beerName", Is.is(expectedBeer.getBeerName())));
	}
	
	@Test
	public void testListBeers() throws Exception {
		var expectedBeerList = beerServiceImpl.listBeers();
		Mockito.when(beerService.listBeers()).thenReturn(expectedBeerList);
		
		var result = mockMvc.perform(get("/api/v1/beer")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", Is.is(3)))
				.andReturn();

		var jsonResponse = result.getResponse().getContentAsString();
		
		List<Beer> beerList = objectMapper.readValue(jsonResponse, new TypeReference<List<Beer>>() {});

		assertEquals(beerServiceImpl.listBeers(), beerList);
	}

}
