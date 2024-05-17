package com.Mercurius.Bridge.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor

public class BundledProductRepresentation {
	private String productId;
    private String name;
    private String description;
    private double price;
    private List<ProductRepresentation> products;

}
