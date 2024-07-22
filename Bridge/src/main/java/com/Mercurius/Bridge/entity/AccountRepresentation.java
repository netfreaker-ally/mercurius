package com.Mercurius.Bridge.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AccountRepresentation {

	private String id;

	private String accountId;

	private String accountName;

	private String accountType;

	private Double balance;

	private Date createdDate;

	private Date updatedDate;

	private boolean isActive;

	private int age;

	private String location;

	private double income;

	private String employmentStatus;

	private boolean membershipLevel;
//	private Set<OfferRepresentation> offers = new HashSet<>();

}
