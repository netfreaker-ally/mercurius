package com.Mercurius.accountservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "account_offer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOffer {
    @Id
    private String id;  
    private String accountId;
    private String offerId;

}
