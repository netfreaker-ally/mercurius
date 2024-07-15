package com.Mercurius.Bridge.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AccountRepresentation {

	private Long id;

	private String accountId;

	private String accountType;

	private Double balance;

	private Date createdDate;

	private Date updatedDate;

	private boolean isActive;

	private Integer age;

	private String location;

	private Double income;

	private String employmentStatus;

	private boolean membershipLevel;

	private String accountName;

	private Set<OfferRepresentation> offers = new HashSet<>();

}
