package com.inventory.spring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class ProductInfo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // IDENTITY)
	@Column(name = "id")
	private Long productId;
	@Column(name = "code")
	private String productCode;
	@Column(name = "name")
	private String productName;
	private String batch;
	private int stock;
	@Column(name = "exp")
	private String expiryDate;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;

	public ProductInfo() {
		super();
	}

	public ProductInfo(String productCode, String productName, String batch, int stock, String expiryDate) {
		super();
		this.productCode = productCode;
		this.productName = productName;
		this.batch = batch;
		this.stock = stock;
		this.expiryDate = expiryDate;
	}

	public ProductInfo(Long productId, String productCode, String productName, String batch, int stock,
			String expiryDate, Supplier supplier) {
		super();
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.batch = batch;
		this.stock = stock;
		this.expiryDate = expiryDate;
		this.supplier = supplier;
	}

	public Long getProductId() {
		return productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "ProductInfo [productId=" + productId + ", productCode=" + productCode + ", productName=" + productName
				+ ", batch=" + batch + ", stock=" + stock + ", expiryDate=" + expiryDate + ", supplier="
				+ supplier.getSupplier() + "]";
	}

}
