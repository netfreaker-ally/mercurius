package com.Mercurious.productservice.entity;


import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "account")
public class AccountRepresentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Account ID is required")
    private String accountId;
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
   
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "account_offer",
        joinColumns = @JoinColumn(name = "account_id"),  
        inverseJoinColumns = @JoinColumn(name = "offer_id")  
    )
    private Set<OfferRepresentation> offers = new HashSet<>();


}