package com.inventory.spring.service;

import java.util.HashSet;
import java.util.List;

import com.inventory.spring.entity.ProductAndStock;
import com.inventory.spring.entity.Supplier;

public interface InventoryService {
	// public void saveAll(List<Inventory> inventoryList);
	// public void saveAllProducts(List<ProductInfo> productInfoList);
	public void saveAllSuppliers(List<Supplier> supplierlist);

	public List<ProductAndStock> getProductAndStockForSupplier(String supplierName, int pageNumber, int pageSize);

	public ProductAndStock getStockForProductBySupplier(String supplierName, String productName, int pageNumber,
			int pageSize);

	public HashSet<String> getListOfProductsBySupplier(List<String> supplierIds, int pageNumber, int pageSize);

}
