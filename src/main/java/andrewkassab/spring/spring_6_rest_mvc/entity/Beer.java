package andrewkassab.spring.spring_6_rest_mvc.entity;

import andrewkassab.spring.spring_6_rest_mvc.model.BeerStyle;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Beer {

	@Id
	@GeneratedValue
	@Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
	private UUID id;

	@Version
	private Integer version;

	private String beerName;
	private BeerStyle beerStyle;
	private	String upc;
	private Integer quantityOnHand;
	private BigDecimal price;
	private LocalDateTime createdDate;
	private LocalDateTime updateDate;

}
