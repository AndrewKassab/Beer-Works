package andrewkassab.spring.spring_6_rest_mvc.controller;

import andrewkassab.spring.spring_6_rest_mvc.entity.Beer;
import andrewkassab.spring.spring_6_rest_mvc.exception.NotFoundException;
import andrewkassab.spring.spring_6_rest_mvc.model.BeerDTO;
import andrewkassab.spring.spring_6_rest_mvc.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testBeerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetById() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = beerController.getBeerById(beer.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    public void testListBeers() {
        List<BeerDTO> beerDtoList = beerController.listBeers();

        assertThat(beerDtoList.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> beerDtoList = beerController.listBeers();

        assertThat(beerDtoList.size()).isEqualTo(0);
    }

}
