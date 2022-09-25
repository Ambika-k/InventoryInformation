package com.inventory.spring.controller;

/*
 * This is a controller layer which handles 
 * the requests from users
 */

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.inventory.spring.entity.ProductAndExpiryDate;
import com.inventory.spring.entity.ProductAndStock;
import com.inventory.spring.entity.ProductInfo;
import com.inventory.spring.entity.Supplier;
import com.inventory.spring.service.InventoryService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class InventoryController {
	@Autowired
	private InventoryService inventoryService;

	@PostMapping("/upload")
	// This method is to store csv file into database
	public String myService(@RequestParam("file") MultipartFile file) throws Exception, ParseException {

		List<Supplier> supplierList = new ArrayList<>();
		HashMap<String, Supplier> name2SupplierMap = new HashMap<>();

		InputStream inputStream = file.getInputStream();
		CsvParserSettings setting = new CsvParserSettings();
		setting.setHeaderExtractionEnabled(true);
		CsvParser csvParser = new CsvParser(setting);
		List<Record> parseRecords = csvParser.parseAllRecords(inputStream);
		parseRecords.forEach(record -> {
			String supplierName = record.getString("supplier");

			ProductInfo productInfo = new ProductInfo();

			productInfo.setProductCode(record.getString("code"));
			productInfo.setProductName(record.getString("name"));
			productInfo.setBatch(record.getString("batch"));
			productInfo.setStock(Integer.parseInt(record.getString("stock")));
			productInfo.setExpiryDate(record.getString("exp"));
			Supplier supplier;
			if (supplierName == null) {
				supplierName = " ";
			}
			if (name2SupplierMap.containsKey(supplierName)) { // storing list of products for one supplier
				supplier = name2SupplierMap.get(supplierName);
			} else {
				supplier = new Supplier();
				List<ProductInfo> productInfoList = new ArrayList<>();
				supplier.setProductInfo(productInfoList);
				supplier.setSupplierName(supplierName);
				supplierList.add(supplier);
				name2SupplierMap.put(supplierName, supplier);
			}
			productInfo.setSupplier(supplier);
			supplier.addInProductInfoList(productInfo);
		});
		inventoryService.saveAllSuppliers(supplierList);
		return "Uploaded succesfully";
	}

	// This method is to list all the products that the supplier has with stock 
	@GetMapping(value = "/getProducts/{supplierName}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<ProductAndStock> getProductAndStockForSupplier(@PathVariable("supplierName") String supplierName,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {

		List<ProductAndStock> productNameAndStockList = (List<ProductAndStock>) inventoryService
				.getProductAndStockForSupplier(supplierName, pageNumber, pageSize);
		return productNameAndStockList;

	}
	//This method is to get product and stock based on supplier and product name 
	@GetMapping(value = "/getProducts/{supplierName}/{productName}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ProductAndStock getStockForProductBySupplier(@PathVariable("supplierName") String supplierName,
			@PathVariable("productName") String productName,
			@RequestParam(value = "pageNumber", required = true, defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {

		ProductAndStock productNameAndStock = inventoryService.getStockForProductBySupplier(supplierName, productName,
				pageNumber, pageSize);
		return productNameAndStock;

	}
	//This method is to list out the products that didn’t yet expire for that supplier or list of suppliers
	@GetMapping(value = "/getProductsOfSupplier/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public HashSet<String> getListOfProductsBySupplier(@PathVariable("id") List<String> supplierIds,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {

		return inventoryService.getListOfProductsBySupplier(supplierIds, pageNumber, pageSize);
	}


}
