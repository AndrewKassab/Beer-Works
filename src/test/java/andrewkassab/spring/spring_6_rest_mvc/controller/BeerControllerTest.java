package andrewkassab.spring.spring_6_rest_mvc.controller;

import java.util.*;

import andrewkassab.spring.spring_6_rest_mvc.model.BeerDTO;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import andrewkassab.spring.spring_6_rest_mvc.service.BeerService;
import andrewkassab.spring.spring_6_rest_mvc.service.BeerServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BeerController.class)
public class BeerControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	BeerService beerService;
	
	BeerServiceImpl beerServiceImpl;
	
	@Captor
	ArgumentCaptor<UUID> uuidCaptor;
	
	@Captor
	ArgumentCaptor<BeerDTO> beerCaptor;
	
	@BeforeEach
	public void setup() {
		beerServiceImpl = new BeerServiceImpl();
		objectMapper.findAndRegisterModules();
	}
	
	public BeerDTO getABeer() {
		return beerServiceImpl.listBeers(null, null).get(0);
	}
	
	public List<BeerDTO> getBeerList() {
		return beerServiceImpl.listBeers(null, null);
	}
	
	@Test
	public void testPatchBeer() throws Exception {
		BeerDTO beer = getABeer();
		
		Map<String, Object> beerMap = new HashMap<>();
		beerMap.put("beerName", "New Name");
		
		mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(beerMap)))
			.andExpect(status().isNoContent());
		
		Mockito.verify(beerService).patchBeerById(uuidCaptor.capture(), beerCaptor.capture());
		
		assertEquals(beer.getId(), uuidCaptor.getValue());
		assertEquals(beerMap.get("beerName"), beerCaptor.getValue().getBeerName()); 
	}

	@Test
	public void testDeleteBeer() throws Exception {
		BeerDTO beer = getABeer();

		Mockito.when(beerService.deleteById(beer.getId())).thenReturn(true);

		mockMvc.perform(delete(BeerController.BEER_PATH_ID, beer.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
		
		Mockito.verify(beerService).deleteById(beer.getId());
	}

	@Test
	public void testUpdateBeer() throws Exception {
		BeerDTO beer = getABeer();
		beer.setBeerName("New name");

		Mockito.when(beerService.updateBeerById(beer.getId(), beer)).thenReturn(Optional.of(beer));
		
		mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(beer)))
				.andExpect(status().isNoContent());
		
		Mockito.verify(beerService).updateBeerById(beer.getId(), beer);
	}

	@Test
	public void testUpdateBeerMissingName() throws Exception {
		BeerDTO beer = getABeer();
		beer.setBeerName(null);

		mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(beer)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.length()", Is.is(2)));
	}

	@Test
	public void testCreateNewBeer() throws Exception {
		BeerDTO beer = getABeer();
		
		Mockito.when(beerService.saveNewBeer(beer)).thenReturn(beer);
		
		mockMvc.perform(post(BeerController.BEER_PATH)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(beer)))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"));
	}

	@Test
	public void testCreateBeerNullBeerName() throws Exception {
		BeerDTO beerDto = BeerDTO.builder().build();

		mockMvc.perform(post(BeerController.BEER_PATH)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(beerDto)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.length()", Is.is(6)));
	}

	@Test
	public void testGetBeerById() throws Exception {
		var expectedBeer = BeerDTO.builder()
				.id(UUID.randomUUID())
				.beerName("Test name")
				.build();
		
		Mockito.when(beerService.getBeerById(expectedBeer.getId())).thenReturn(Optional.of(expectedBeer));

		mockMvc.perform(get(BeerController.BEER_PATH_ID, expectedBeer.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", Is.is(expectedBeer.getId().toString())))
				.andExpect(jsonPath("$.beerName", Is.is(expectedBeer.getBeerName())));
	}

	@Test
	public void testGetBeerByIdNotFound() throws Exception {
		Mockito.when(beerService.getBeerById(any(UUID.class))).thenReturn(Optional.empty());

		mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
				.andExpect(status().isNotFound());
	}
	@Test
	public void testListBeers() throws Exception {
		var expectedBeerList = getBeerList();
		Mockito.when(beerService.listBeers(null, null)).thenReturn(expectedBeerList);
		
		var result = mockMvc.perform(get(BeerController.BEER_PATH)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", Is.is(3)))
				.andReturn();

		var jsonResponse = result.getResponse().getContentAsString();
		
		List<BeerDTO> beerList = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

		assertEquals(beerServiceImpl.listBeers(null, null), beerList);
	}

}
