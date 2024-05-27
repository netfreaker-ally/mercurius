package com.Mercurius.Bridge.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class OfferRepresentation {

    private String offerId;
    private String name;
    private String description;
    private double discountPercentage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
	public OfferRepresentation(String offerId, String name, String description, double discountPercentage,
			LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.offerId = offerId;
		this.name = name;
		this.description = description;
		this.discountPercentage = discountPercentage;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public OfferRepresentation() {
		super();
		// TODO Auto-generated constructor stub
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
	

   
}
