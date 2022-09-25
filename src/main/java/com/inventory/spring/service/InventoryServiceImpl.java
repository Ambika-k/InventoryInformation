package com.inventory.spring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.inventory.spring.entity.NameAndInt;
import com.inventory.spring.entity.ProductAndExpiryDate;
import com.inventory.spring.entity.ProductAndStock;
import com.inventory.spring.entity.ProductInfo;
import com.inventory.spring.entity.Supplier;
import com.inventory.spring.repository.InventoryRepository;

@Service
public class InventoryServiceImpl implements InventoryService {
	@Autowired
	private InventoryRepository inventoryRepository;

	// This method is to store csv file data into database
	@Override
	public void saveAllSuppliers(List<Supplier> supplierList) {
		for (Supplier supplier : supplierList) {
			inventoryRepository.save(supplier);
		}

	}

	// This method recieves product and stock details for the supplier and it adds
	// the stock data if both the products are same
	@Override
	public List<ProductAndStock> getProductAndStockForSupplier(String supplierName, int pageNumber, int pageSize) {
		List<ProductAndStock> nOrigNameAndStockList = inventoryRepository.getProductAndStockForSupplier(supplierName,
				PageRequest.of(pageNumber, pageSize));
		HashMap<String, Integer> name2StockMap = new HashMap<>();
		for (ProductAndStock nameAndStock : nOrigNameAndStockList) {
			int prevStock = 0;
			if (name2StockMap.containsKey(nameAndStock.getName())) {
				prevStock = name2StockMap.get(nameAndStock.getName());
			}
			name2StockMap.put(nameAndStock.getName(), prevStock + nameAndStock.getStock());
		}
		List<ProductAndStock> nNewNameAndStockList = new ArrayList<>();
		for (HashMap.Entry<String, Integer> nameAndStockIt : name2StockMap.entrySet()) {
			nNewNameAndStockList.add(new NameAndInt(nameAndStockIt.getKey(), nameAndStockIt.getValue()));
		}
		return nNewNameAndStockList;
	}

	// This method returns the product and stock of supplier of particular
	// productname and if there are any same products then stock will be added and
	// returned.
	@Override
	public ProductAndStock getStockForProductBySupplier(String supplierName, String productName, int pageNumber,
			int pageSize) {
		List<ProductAndStock> nOrigNameAndStockList = inventoryRepository.getStockForProductBySupplier(supplierName,
				productName, PageRequest.of(pageNumber, pageSize));

		int nStock = 0;
		for (ProductAndStock nameAndStock : nOrigNameAndStockList) {
			nStock += nameAndStock.getStock();
		}
		ProductAndStock nProductNameAndStock = (ProductAndStock) new NameAndInt(productName, nStock);
		return nProductNameAndStock;
	}

	// This method recieves product and its expiry date from repository and if the product is expired then that product is removed from list
	@Override
	public HashSet<String> getListOfProductsBySupplier(List<String> supplierIds, int pageNumber, int pageSize) {

		List<ProductAndExpiryDate> productAndExpiryDateList = inventoryRepository
				.getListOfProductsBySupplier(supplierIds, PageRequest.of(pageNumber, pageSize));
		HashSet<String> nNonExpiredProducts = new HashSet<String>();

		String pattern = "dd/MM/yyyy";
		String dateInString = new SimpleDateFormat(pattern).format(new Date());
		Date currentDate = null;
		try {
			currentDate = new SimpleDateFormat(pattern).parse(dateInString);
		} catch (ParseException e1) {
		}
		for (ProductAndExpiryDate productAndExpiryDate : productAndExpiryDateList) {
			try {
				Date expDate = new SimpleDateFormat(pattern).parse(productAndExpiryDate.getExp());
				if (currentDate.compareTo(expDate) <= 0) { // current date is copared with expiry date
					nNonExpiredProducts.add(productAndExpiryDate.getName());
				}
			} catch (Exception e) {
				nNonExpiredProducts.add(productAndExpiryDate.getName());
			}
		}
		return nNonExpiredProducts;
	}
}
