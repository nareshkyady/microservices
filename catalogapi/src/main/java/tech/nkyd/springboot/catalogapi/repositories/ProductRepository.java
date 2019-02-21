package tech.nkyd.springboot.catalogapi.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import tech.nkyd.springboot.catalogapi.entities.Product;

@Repository
public class ProductRepository {

   String table_name = "product";
   AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
    		  .withRegion(Regions.US_EAST_1)
    		  .build(); 
   public ProductRepository() {
	   System.setProperty("aws.accessKeyId", "<key id>");
	   System.setProperty("aws.secretKey", "<secret key>");
   }
// Save/create a new profile
   public Product  saveProduct(Product product) {
      
	   DynamoDBMapper mapper = new DynamoDBMapper(client);

	 

	   mapper.save(product);          
       return product;
   }
    // Lists all products
    public List<Product> findAll() {
    	 
        try {
       	  DynamoDBMapper mapper = new DynamoDBMapper(client);
       	DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
       	List<Product> itemList = mapper.scan(Product.class, scanExpression);
       return itemList;
             
         } catch (AmazonServiceException e) {
             System.err.println(e.getErrorMessage());
             System.exit(1);
         }
		return null;
    }
 // We can delete a profile by id
    public Product deleteById(String id) {
    	DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
    	DynamoDBMapper mapper = new DynamoDBMapper(client);
    	Product updatedItem = mapper.load(Product.class, id, config);


        // Delete the item.
        mapper.delete(updatedItem);
   	 

        return updatedItem;
    }
 // This finds a profile by the id
    public Product findById(String id) {
    	DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
    	DynamoDBMapper mapper = new DynamoDBMapper(client);
    	Product item = mapper.load(Product.class, id, config);

    	
       return item;
    }
}