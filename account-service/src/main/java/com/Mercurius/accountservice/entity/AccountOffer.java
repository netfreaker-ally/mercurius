package com.Mercurius.accountservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("account_offer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOffer {
    @Id
    private String id;  
    private String accountId;
    private String offerId;

}
