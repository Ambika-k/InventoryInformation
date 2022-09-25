package com.inventory.spring.entity;
/*
*This is a Auxiliary class which is used to tie up 
*productName and stock variables
*/
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
		return productName;
	}

	@Override
	public int getStock() {
		return stock;
	}

}
