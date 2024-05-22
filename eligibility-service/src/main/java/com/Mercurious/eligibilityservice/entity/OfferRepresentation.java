package com.Mercurious.eligibilityservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name="offer")
public class OfferRepresentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Offer ID is required")
    private String offerId;

    @NotBlank(message = "Offer name is required")
    private String name;

    private String description;

    @NotNull(message = "Discount percentage is required")
    private double discountPercentage;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;

    @ManyToOne
    private AccountRepresentation account;

	public OfferRepresentation(Long id, @NotBlank(message = "Offer ID is required") String offerId,
			@NotBlank(message = "Offer name is required") String name, String description,
			@NotNull(message = "Discount percentage is required") double discountPercentage,
			@NotNull(message = "Start date is required") LocalDateTime startDate,
			@NotNull(message = "End date is required") LocalDateTime endDate, AccountRepresentation account) {
		super();
		this.id = id;
		this.offerId = offerId;
		this.name = name;
		this.description = description;
		this.discountPercentage = discountPercentage;
		this.startDate = startDate;
		this.endDate = endDate;
		this.account = account;
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

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public AccountRepresentation getAccount() {
		return account;
	}

	public void setAccount(AccountRepresentation account) {
		this.account = account;
	}

	public OfferRepresentation() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
