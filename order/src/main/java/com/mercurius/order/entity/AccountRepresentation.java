package com.mercurius.order.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

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

	public AccountRepresentation(Long id, String accountId, String accountType, Double balance, Date createdDate,
			Date updatedDate, boolean isActive, Integer age, String location, Double income, String employmentStatus,
			boolean membershipLevel, String accountName, Set<OfferRepresentation> offers) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.accountType = accountType;
		this.balance = balance;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.isActive = isActive;
		this.age = age;
		this.location = location;
		this.income = income;
		this.employmentStatus = employmentStatus;
		this.membershipLevel = membershipLevel;
		this.accountName = accountName;
		this.offers = offers;
	}

	public AccountRepresentation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public boolean isMembershipLevel() {
		return membershipLevel;
	}

	public void setMembershipLevel(boolean membershipLevel) {
		this.membershipLevel = membershipLevel;
	}

	public Set<OfferRepresentation> getOffers() {
		return offers;
	}

	public void setOffers(Set<OfferRepresentation> offers) {
		this.offers = offers;
	}

}
