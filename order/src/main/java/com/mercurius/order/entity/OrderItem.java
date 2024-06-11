package com.mercurius.order.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class OrderItem {
	@Id
	private String orderItemId;
	private String orderId;
	private LocalDateTime orderDate;
	private LocalDateTime cancellationDate;
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
	

	public OrderItem(String orderItemId, String orderId, LocalDateTime orderDate, LocalDateTime cancellationDate,
			PaymentType paymentType, Status status, String cancellationReason, String cancellationSubReason,
			boolean courierReturn, int quantity, String listingId, List<String> packageIds,
			PriceComponent priceComponents, boolean isReplacement, String productId) {
		super();
		this.orderItemId = orderItemId;
		this.orderId = orderId;
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

	public String getProductId() {
		return productId;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(LocalDateTime cancellationDate) {
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
