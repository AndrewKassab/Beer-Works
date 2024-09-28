package andrewkassab.spring.spring_6_rest_mvc.repository;

import andrewkassab.spring.spring_6_rest_mvc.entity.Beer;
import andrewkassab.spring.spring_6_rest_mvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSavedBeer() {
        Beer beer = beerRepository.save(Beer.builder()
                .beerName("Budweiser")
                .beerStyle(BeerStyle.LAGER)
                .upc("123456789012L")
                .price(new BigDecimal("12.99"))
                .build());

        beerRepository.flush();

        assertThat(beer).isNotNull();
        assertThat(beer.getId()).isNotNull();
    }

    @Test
    void testSaveBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
             Beer beer = beerRepository.save(Beer.builder()
                .beerName("Budweiser213121212121212121212121212121212121212121212121212121212121212121212121212121212121212121212121212121322222")
                .beerStyle(BeerStyle.LAGER)
                .upc("123456789012L")
                .price(new BigDecimal("12.99"))
                .build());

            beerRepository.flush();
        });
    }

}