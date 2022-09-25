# InventoryInformation
This is a springboot application which inserts csv file into database and performs some operations on it.

### PREREQUISITES:
 * JDK 11
 * Maven 3.8.6
 * Sample CSVfile
 * MySQL 
 * Postman
 
 ### Database requirements
 Install MYSQL in your system
* spring.datasource.url=jdbc:mysql://localhost:3306/your db 
* spring.datasource.username= your username
* spring.datasource.password= your password

### To insert csv file into database
* POST Request => http://localhost:8081/upload
In Postman, at Body section select form-data and at key select 'File' type & 'file' in key field and in value section click on selectfiles and select the csv file which you want to insert.

### To list all the products that the supplier has with stock
* GET Request => http://localhost:8081/getProducts/{supplier_id}?pageNumber=0&pageSize=10

### To get product and stock based on supplier and product name 
 * GET Request => http://localhost:8081/getProducts/{supplierName}/{productName}
 
### To list out the products that didn’t yet expire for that supplier or list of suppliers
* GET Request => http://localhost:8081/getProductsOfSupplier/{supplierid1,supplierid2,..}?pageSize=10
