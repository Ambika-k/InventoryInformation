package com.inventory.spring.entity;
/*
 * This is Supplier class which contains 
 * list of productInfo.
 * This class has OneToMany relationship
 * with ProductInfo.
 */
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Supplier {

	@Id
	private String supplierName;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "supplier")
	private List<ProductInfo> productInfoList;

	public Supplier() {
		super();
	}

	public Supplier(String supplierName) {
		super();
		this.supplierName = supplierName;
	}

	public Supplier(String supplierName, List<ProductInfo> productInfoList) {
		super();
		this.supplierName = supplierName;
		this.productInfoList = productInfoList;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public List<ProductInfo> getProductInfoList() {
		return productInfoList;
	}

	public void setProductInfo(List<ProductInfo> productInfoList) {
		this.productInfoList = productInfoList;
	}

	public void addInProductInfoList(ProductInfo productInfo) {
		productInfoList.add(productInfo);
	}

	@Override
	public String toString() {
		return "Supplier [supplier=" + supplierName + ", productInfo=" + productInfoList + "]";
	}

}
