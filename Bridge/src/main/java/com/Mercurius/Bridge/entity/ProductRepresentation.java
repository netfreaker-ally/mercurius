package com.Mercurius.Bridge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductRepresentation {
	private String productId;
    private String productName;
    private double price;
    private String description;
    private AccountRepresentation account;
}
