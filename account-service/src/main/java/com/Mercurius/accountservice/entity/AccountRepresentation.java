package com.Mercurius.accountservice.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/*
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;*/
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(value = "account")
public class AccountRepresentation {
	@Id
	private String id;

	@NotBlank(message = "Account ID is required")
	private String accountId;
	@NotBlank(message = "Account Name is required")
	private String accountName;
	@NotBlank(message = "Account type is required")
	private String accountType;

	@NotNull(message = "Balance is required")
	private Double balance;

	private Date createdDate;

	private Date updatedDate;

	private boolean isActive;

	@NotNull(message = "Age is required")
	private Integer age;

	@NotBlank(message = "Location is required")
	private String location;

	@NotNull(message = "Income is required")
	private Double income;
	@NotBlank(message = "Employment Status is required")
	private String employmentStatus;

	private boolean membershipLevel;
	private Set<OfferRepresentation> offers = new HashSet<>();
	@Override
	public String toString() {
		return "AccountRepresentation [id=" + id + ", accountId=" + accountId + ", accountName=" + accountName
				+ ", accountType=" + accountType + ", balance=" + balance + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", isActive=" + isActive + ", age=" + age + ", location=" + location
				+ ", income=" + income + ", employmentStatus=" + employmentStatus + ", membershipLevel="
				+ membershipLevel + "]";
	}

//	@ManyToMany(cascade = CascadeType.ALL)
//
//	@JoinTable(name = "account_offer", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "offer_id"))
//	@DBRef
	

}
