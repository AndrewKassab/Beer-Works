package andrewkassab.spring.spring_6_rest_mvc.repository;

import andrewkassab.spring.spring_6_rest_mvc.entity.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
