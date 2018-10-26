package com.prs.business.purchaserequest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.prs.business.product.Product;

@Entity
public class PurchaseRequestLineItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "PurchaseRequestID")
	private PurchaseRequest purchaseRequest;
	@ManyToOne
	@JoinColumn(name = "ProductID")
	private Product product;
	private int quantity;

	public PurchaseRequestLineItem() {

	}

	public PurchaseRequestLineItem(int id, PurchaseRequest purchaseRequest, Product product, int quantity) {
		this.id = id;
		this.purchaseRequest = purchaseRequest;
		this.product = product;
		this.quantity = quantity;
	}

	public PurchaseRequestLineItem(PurchaseRequest purchaseRequest, Product product, int quantity) {
		this.purchaseRequest = purchaseRequest;
		this.product = product;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "purchaseRequestLineItem [id=" + id + ", purchaseRequest=" + purchaseRequest.getId() + ", product="
				+ product.getId() + ", quantity=" + quantity + "]";
	}
}
