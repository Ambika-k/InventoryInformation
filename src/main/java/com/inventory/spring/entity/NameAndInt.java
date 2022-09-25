package com.inventory.spring.entity;

public class NameAndInt implements ProductAndStock {
	private String productName;
	private int stock;

	public NameAndInt(String productName, int stock) {
		super();
		this.productName = productName;
		this.stock = stock;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return productName;
	}

	@Override
	public int getStock() {
		// TODO Auto-generated method stub
		return stock;
	}

}
