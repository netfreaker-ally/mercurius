package com.Mercurius.accountservice.entity;

import java.util.Date;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity(name = "account")
public class AccountRepresentation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Account ID is required")
	private String accountId;

	@NotBlank(message = "User ID is required")
	private String userId;

	@NotBlank(message = "Account type is required")
	private String accountType;

	@jakarta.validation.constraints.NotNull(message = "cannot be null")
	private Double balance;

	private Date createdDate;

	private Date updatedDate;

	private boolean isActive;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<ProductRepresentation> products;

	public Long getId() {
		return id;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public List<ProductRepresentation> getProducts() {
		return products;
	}

	public void setProducts(List<ProductRepresentation> products) {
		this.products = products;
	}

	public AccountRepresentation(Long id, @NotBlank(message = "Account ID is required") String accountId,
			@NotBlank(message = "User ID is required") String userId,
			@NotBlank(message = "Account type is required") String accountType,
			@NotBlank(message = "Balance is required") Double balance, Date createdDate, Date updatedDate,
			boolean isActive, List<ProductRepresentation> products) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.userId = userId;
		this.accountType = accountType;
		this.balance = balance;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.isActive = isActive;
		this.products = products;
	}

	public AccountRepresentation() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
