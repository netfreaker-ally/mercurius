package com.Mercurius.accountservice.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;

/*import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;*/
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(value = "offer")
public class OfferRepresentation {

	@Id

	private Long id;

	@NotBlank(message = "Offer ID is required")
	private String offerId;

	@NotBlank(message = "Offer name is required")
	private String name;

	private String description;

	@NotNull(message = "Discount percentage is required")
	private double discountPercentage;

	@NotNull(message = "Start date is required")
	private Date startDate;

	@NotNull(message = "End date is required")
	private Date endDate;

	  @JsonBackReference
	  
//	  @ManyToMany(mappedBy = "offers")
	 // @DBRef
	  private Set<AccountRepresentation> accounts = new HashSet<>();;
	 

}
