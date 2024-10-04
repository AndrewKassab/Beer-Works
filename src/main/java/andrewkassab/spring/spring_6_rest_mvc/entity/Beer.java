package andrewkassab.spring.spring_6_rest_mvc.entity;

import andrewkassab.spring.spring_6_rest_mvc.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
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
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
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

	@OneToMany(mappedBy = "beer")
	private Set<BeerOrderLine> beerOrderLines;

	@ManyToMany
	@JoinTable(name = "beer_category",
			joinColumns = @JoinColumn(name = "beer_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories;

	@NotNull
	private BigDecimal price;

	@CreationTimestamp
	private LocalDateTime createdDate;

	@UpdateTimestamp
	private LocalDateTime updateDate;

}
