package com.inventory.spring.repository;

/*
 * This is repository layer which
 * interacts with database.
 */

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inventory.spring.entity.ProductAndExpiryDate;
import com.inventory.spring.entity.ProductAndStock;
import com.inventory.spring.entity.Supplier;

@Repository
public interface InventoryRepository extends JpaRepository<Supplier, String> {
	/*
	 * custom query to get product and 
	 * its stock based on supplierid
	 */
	@Query(value = "select p.name,p.stock from product_info p where p.supplier_id=:supplierName", nativeQuery = true)
	List<ProductAndStock> getProductAndStockForSupplier(@Param("supplierName") String supplierName, Pageable pageable);

	/*
	 * custom query to get product and 
	 * its stock based on supplierid and 
	 * productname
	 */
	@Query(value = "select p.name,p.stock from product_info p where p.supplier_id=:supplierName and p.name=:productName", nativeQuery = true)
	List<ProductAndStock> getStockForProductBySupplier(@Param("supplierName") String supplierName,
			@Param("productName") String productName, Pageable pageable);

	/*
	 * custom query to get product and its 
	 * expirydatebased on list of suplierids
	 */
	@Query(value = "select p.name,p.stock,p.exp from product_info p where p.supplier_id in :supplierIds", nativeQuery = true)
	List<ProductAndExpiryDate> getListOfProductsBySupplier(@Param("supplierIds") List<String> supllierIds,
			Pageable pageable);
}
