package com.Mercurious.productservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity(name="product")
public class ProductRepresentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotBlank(message = "Product Name is required")
    private String productName;

    @NotNull(message = "Price is required")
    private Double price;

    private String description;

    

	public ProductRepresentation() {
		super();
		// TODO Auto-generated constructor stub
	}



	public ProductRepresentation(Long id, @NotBlank(message = "Product ID is required") String productId,
			@NotBlank(message = "Product Name is required") String productName,
			@NotNull(message = "Price is required") Double price, String description) {
		super();
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.description = description;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getProductId() {
		return productId;
	}



	public void setProductId(String productId) {
		this.productId = productId;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public Double getPrice() {
		return price;
	}



	public void setPrice(Double price) {
		this.price = price;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}

	
}
