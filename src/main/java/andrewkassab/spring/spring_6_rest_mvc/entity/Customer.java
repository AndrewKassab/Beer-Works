package andrewkassab.spring.spring_6_rest_mvc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	 @Id
	 @GeneratedValue
	 @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
	 private UUID id;
	 private String name;

	 @Version
	 private Integer version;
	 private LocalDateTime createdDate;
	 private LocalDateTime updateDate;

}
