package tech.nkyd.springboot.catalogapi.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="product")
public class Product {
	
    private String id;

    private String name;

    private String description;
    private String sku;
    private String price;
    
    @DynamoDBHashKey(attributeName = "id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@DynamoDBAttribute(attributeName="name") 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@DynamoDBAttribute(attributeName="description") 
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@DynamoDBAttribute(attributeName="sku") 
	public String getSku() {
		return sku;
	}
	
	public void setSku(String sku) {
		this.sku = sku;
	}
	@DynamoDBAttribute(attributeName="price") 
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
   
}
