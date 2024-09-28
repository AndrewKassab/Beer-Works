package andrewkassab.spring.spring_6_rest_mvc.entity;

import andrewkassab.spring.spring_6_rest_mvc.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.lang.NonNull;

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

	@NonNull
	@NotBlank
	@Size(max = 50)
	@Column(length = 55)
	private String beerName;

	@NotNull
	private BeerStyle beerStyle;

	@NotNull
	@NotBlank
	private	String upc;
	private Integer quantityOnHand;
	@NotNull
	private BigDecimal price;
	private LocalDateTime createdDate;
	private LocalDateTime updateDate;

}
