package com.mercurius.order.entity;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItem {
	@Id
	private String orderItemId;
	 @JsonIgnore 
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	private Order order;
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_account_id")
    private Cart cart;

	
	private String accountId;
	private Date orderDate;
	private Date cancellationDate;
	private PaymentType paymentType;
	private Status status;
	private String cancellationReason;
	private String cancellationSubReason;
	private boolean courierReturn;
	private int quantity;
	private String listingId;
	private List<String> packageIds;
	private PriceComponent priceComponents;
	private boolean isReplacement;
	private String productId;

	
	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderItem(String orderItemId, Order order, Cart cart, String accountId, Date orderDate,
			Date cancellationDate, PaymentType paymentType, Status status, String cancellationReason,
			String cancellationSubReason, boolean courierReturn, int quantity, String listingId,
			List<String> packageIds, PriceComponent priceComponents, boolean isReplacement, String productId) {
		super();
		this.orderItemId = orderItemId;
		this.order = order;
		this.cart = cart;
		this.accountId = accountId;
		this.orderDate = orderDate;
		this.cancellationDate = cancellationDate;
		this.paymentType = paymentType;
		this.status = status;
		this.cancellationReason = cancellationReason;
		this.cancellationSubReason = cancellationSubReason;
		this.courierReturn = courierReturn;
		this.quantity = quantity;
		this.listingId = listingId;
		this.packageIds = packageIds;
		this.priceComponents = priceComponents;
		this.isReplacement = isReplacement;
		this.productId = productId;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getProductId() {
		return productId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(Date cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getCancellationReason() {
		return cancellationReason;
	}

	public void setCancellationReason(String cancellationReason) {
		this.cancellationReason = cancellationReason;
	}

	public String getCancellationSubReason() {
		return cancellationSubReason;
	}

	public void setCancellationSubReason(String cancellationSubReason) {
		this.cancellationSubReason = cancellationSubReason;
	}

	public boolean isCourierReturn() {
		return courierReturn;
	}

	public void setCourierReturn(boolean courierReturn) {
		this.courierReturn = courierReturn;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getListingId() {
		return listingId;
	}

	public void setListingId(String listingId) {
		this.listingId = listingId;
	}

	public List<String> getPackageIds() {
		return packageIds;
	}

	public void setPackageIds(List<String> packageIds) {
		this.packageIds = packageIds;
	}

	public PriceComponent getPriceComponents() {
		return priceComponents;
	}

	public void setPriceComponents(PriceComponent priceComponents) {
		this.priceComponents = priceComponents;
	}

	public boolean isReplacement() {
		return isReplacement;
	}

	public void setReplacement(boolean isReplacement) {
		this.isReplacement = isReplacement;
	}

	public enum PaymentType {
		COD, PREPAID
	}

	public enum Status {
		APPROVED, PACKING_IN_PROGRESS, FORM_FAILED, PACKED, READY_TO_DISPATCH, PICKUP_COMPLETE, CANCELLED,
		RETURN_REQUESTED, RETURNED, SHIPPED, DELIVERED, COMPLETED
	}

	@Embeddable
	public static class PriceComponent {
		private double sellingPrice;
		private double totalPrice;
		private double shippingCharge;
		private double customerPrice;
		private double mercuiusDiscount;

		public double getSellingPrice() {
			return sellingPrice;
		}

		public void setSellingPrice(double sellingPrice) {
			this.sellingPrice = sellingPrice;
		}

		public double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(double totalPrice) {
			this.totalPrice = totalPrice;
		}

		public double getShippingCharge() {
			return shippingCharge;
		}

		public void setShippingCharge(double shippingCharge) {
			this.shippingCharge = shippingCharge;
		}

		public double getCustomerPrice() {
			return customerPrice;
		}

		public void setCustomerPrice(double customerPrice) {
			this.customerPrice = customerPrice;
		}

		public double getmercuiusDiscount() {
			return mercuiusDiscount;
		}

		public void setmercuiusDiscount(double mercuiusDiscount) {
			this.mercuiusDiscount = mercuiusDiscount;
		}
	}
}
