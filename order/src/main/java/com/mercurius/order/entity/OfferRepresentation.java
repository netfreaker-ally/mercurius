package com.mercurius.order.entity;


import java.util.HashSet;
import java.util.Set;
import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@AllArgsConstructor
//@Data

public class OfferRepresentation {

	private Long id;

	private String offerId;


	private String name;

	private String description;

	private double discountPercentage;

	private Date startDate;


	private Date endDate;
	@JsonBackReference
 
    private Set<AccountRepresentation> accounts = new HashSet<>();;

	

	public OfferRepresentation(Long id, @NotBlank(message = "Offer ID is required") String offerId,
			@NotBlank(message = "Offer name is required") String name, String description,
			@NotNull(message = "Discount percentage is required") double discountPercentage,
			@NotNull(message = "Start date is required") Date startDate,
			@NotNull(message = "End date is required") Date endDate, Set<AccountRepresentation> accounts) {
		super();
		this.id = id;
		this.offerId = offerId;
		this.name = name;
		this.description = description;
		this.discountPercentage = discountPercentage;
		this.startDate = startDate;
		this.endDate = endDate;
		this.accounts = accounts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public Set<AccountRepresentation> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<AccountRepresentation> accounts) {
		this.accounts = accounts;
	}

	public OfferRepresentation() {
		super();
		// TODO Auto-generated constructor stub
	}

}
