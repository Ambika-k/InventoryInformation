# InventoryInformation
This is a springboot application which inserts csv file into database and performs some operations on it.

### PREREQUISITES:
 * JDK 11
 * Maven 3.8.6
 * Sample CSVfile
 * MySQL [Create inventorydb database in your MYSQL]
 * Postman
 
 ### Database requirements
 Install MYSQL in your system
* spring.datasource.url=jdbc:mysql://localhost:3306/inventorydb
* spring.datasource.username= your_username
* spring.datasource.password= your_password

### To insert csv file into database
* POST Request => http://localhost:8081/upload
In Postman, at Body section select form-data and at key select 'File' type & 'file' in key field and in value section click on selectfiles and select the csv file which you want to insert.

### To list all the products that the supplier has with stock
* GET Request => http://localhost:8081/getProducts/{supplier_id}
* Here stock count will be the sum of same product code.
* So, even if product code is same and batch code is different, it will sum the stock quantity of both the batch codes.
* Did in this way because in problem statement it is mentioned to give just product stock count irrespective of batch code.

### To get product and stock based on supplier and product name 
 * GET Request => http://localhost:8081/getProducts/{supplierName}/{productName}
 * Here stock count will be the sum of same product code.
 * So, even if product code is same and batch code is different, it will sum the stock quantity of both the batch codes.
 * Did in this way because in problem statement it is mentioned to give just product stock count irrespective of batch code.
 * So this will always give only one entry.
 
### To list out the products that didn’t yet expire for that supplier or list of suppliers
* GET Request => http://localhost:8081/getProductsOfSupplier/{supplierid1,supplierid2,..}

### Pagination
* http://localhost:8081/getProducts/{supplier_id}?pageNumber=0&pageSize=10
* http://localhost:8081/getProductsOfSupplier/{supplierid1,supplierid2,..}?pageNumber=0&pageSize=15

Pagination can be applied to any above get methods which gives list of data.

Here pageNumber starts from index 0 to n-1 and will display the data in the pageNumber you asked for.

Here pageSize represents how many objects you want to display.

Even if you didnt mention the pageNumber and pageSize, the default values[pagenumber=0, pageSize=15] will be taken and data will be displayed.
