package com.Mercurius.Bridge.entity;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class AccountRepresentation {
    private String accountId;
    private String userId;
    private String accountType;
    private double balance;
    private Date createdDate;
    private Date updatedDate;
    private boolean isActive;
    private List<ProductRepresentation> products;
}
