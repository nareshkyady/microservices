package tech.nkyd.springboot.profileapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.acmpca.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

@Repository
public class ProfileRepository {

   String table_name = "profile";
   AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
    		  .withRegion(Regions.US_EAST_1)
    		  .build(); 
   public ProfileRepository() {
	   System.setProperty("aws.accessKeyId", "<access key id>");
	   System.setProperty("aws.secretKey", "<secret key>");
   }

    // Lists all profiles
    public List<Profile> findAll() {
    	 
    	 List<Profile> profiles = new ArrayList<Profile>();
        try {
       	  DynamoDB dynamoDB = new DynamoDB(client);

       	  ScanRequest scanRequest = new ScanRequest()
       			    .withTableName("profile");

       			ScanResult result = client.scan(scanRequest);
       			Profile profile;
       			
       			for (Map<String, AttributeValue> item : result.getItems()){
       				profile = new Profile((String)item.get("id").getS(), (String)item.get("name").getS(), (String)item.get("email").getS());
       			    profiles.add(profile);
       			}

             
         } catch (AmazonServiceException e) {
             System.err.println(e.getErrorMessage());
             System.exit(1);
         }
        return profiles;
    }
    
    // Save/create a new profile
    public Profile save(Profile profile) {
       
		
		HashMap<String,AttributeValue> item_values =
		    new HashMap<String,AttributeValue>();
		ArrayList<String[]> extra_fields = new ArrayList<String[]>();
		item_values.put("id", new AttributeValue(profile.getId()));
		
		item_values.put("name", new AttributeValue(profile.getName()));
		item_values.put("email", new AttributeValue(profile.getEmail()));
		
		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
		
		try {
		    ddb.putItem(table_name, item_values);
		} catch (ResourceNotFoundException e) {
		    System.err.format("Error: The table \"%s\" can't be found.\n", table_name);
		    System.err.println("Be sure that it exists and that you've typed its name correctly!");
		    System.exit(1);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getMessage());
		    System.exit(1);
		}
        return profile;
    }

    // This finds a profile by the id
    public Profile findById(String id) {
       
    	HashMap<String,AttributeValue> key_to_get =
                new HashMap<String,AttributeValue>();

            key_to_get.put("id", new AttributeValue(id));

            GetItemRequest request = null;
            if (id != null) {
                request = new GetItemRequest()
                    .withKey(key_to_get)
                    .withTableName(table_name);
                 
            } else {
                request = new GetItemRequest()
                    .withKey(key_to_get)
                    .withTableName(table_name);
            }

            final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
            Profile profile = null;
            try {
                Map<String,AttributeValue> returned_item =
                   ddb.getItem(request).getItem();
                if (returned_item != null) {
           				profile = new Profile((String)returned_item.get("id").getS(), (String)returned_item.get("name").getS(), (String)returned_item.get("email").getS());
                } 
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
                System.exit(1);
            }
       return profile;
    }

    // We can delete a profile by id
    public Profile deleteById(String id) {
    	DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable(table_name);

       
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
            .withPrimaryKey(new PrimaryKey("id", id));

        // Conditional delete (we expect this to fail)

        try {
            System.out.println("Attempting a conditional delete...");
            table.deleteItem(deleteItemSpec);
            System.out.println("DeleteItem succeeded");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}