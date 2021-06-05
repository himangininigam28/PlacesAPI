package stepDefinitions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utilities;
import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;

public class StepDefinition extends Utilities{
	private static final Logger logger = LogManager.getLogger(StepDefinition.class);  
	static addPlaces.AddMaps am;
	static addPlaces.AddMapsResponse amr1=null; 
	static getPlaces.GetMaps gm;
	static updatePlaces.UpdateMaps up;
	static Response response2;
	static RequestSpecification request1; 
	static ResponseSpecification response1;
	static String placeId=null;
	static Response responseAdd, responseGet, responseUpdate, responseDelete;
	static List<String> placeIdList =null;
    @Given("The AddPlace api payload is ready {string} {string} {string}")
    public void AddPlacePayload(String name, String language, String address){
    	PropertyConfigurator.configure("src\\test\\java\\resources\\log4j.properties");
    	am= TestDataBuild.addPlacePayload(name,language,address);
       	logger.info("Payload Ready");   	
    }

    @When("{string} is triggered with {string} method")
    public Response ApiRequestTrigger(String apiName, String apiMethod) throws IOException, URISyntaxException, MalformedURLException{
        
    	APIResources resourceAPI = APIResources.valueOf(apiName);
    	request1= RequestSpecification();
    	if(apiMethod.equalsIgnoreCase("Post")) {
    		//request1= RequestSpecification();
    		request1=reusableMethods.addPlace.AddMapsRequest(am,request1);
    		//Generate AddMaps Response
    		addPlaces.AddMapsResponse amr = new addPlaces.AddMapsResponse();
    		amr = reusableMethods.addPlace.AddMapsResponse(request1, resourceAPI.getResource());
    		amr1=amr;
    		logger.info("Request Ready");
    		responseAdd = request1
		    		.when()
		    		.post(resourceAPI.getResource())
		    		.then().log().all().
		    		extract().response();
    		return responseAdd;
    	}
    	else if(apiMethod.equalsIgnoreCase("Put")) {
    		
    		request1= reusableMethods.updatePlace.UpdateMapsRequest(up, request1, amr1.getPlace_id());
    		return reusableMethods.updatePlace.UpdateMapsResponseString(request1, resourceAPI.getResource());
    	}
    	else if(apiMethod.equalsIgnoreCase("Get")) {
    		 gm=given().log().all()
    		.queryParam("place_id", amr1.getPlace_id())
    		.spec(request1)
    		.when()
    		.get(resourceAPI.getResource())
    		.as(getPlaces.GetMaps.class);
    		 
    		 responseGet = given().log().all()
    		    		.queryParam("place_id", amr1.getPlace_id())
    		    		.spec(request1)
    		    		.when()
    		    		.get(resourceAPI.getResource())
    		    		.then().log().all().
    		    		extract().response();
    		 return responseGet;
    	}
    	else if(apiMethod.equalsIgnoreCase("delete")) {
    		deletePlaces.DeleteMaps dm = new deletePlaces.DeleteMaps();
    		dm.setPlace_id(amr1.getPlace_id());
    		request1 = reusableMethods.deletePlace.DeleteMapsRequest(dm, request1);
    		responseDelete = reusableMethods.deletePlace.DeleteMapsResponseString(request1, resourceAPI.getResource());
    		
    		return responseDelete;
    	}
		return null;
      }

    @Then("Response should contain {string} as {string} for {string}")
    public void ValidateResponseBodyFields(String field, String value, String apiName){
    	JsonPath js;
    	if(apiName.equalsIgnoreCase("AddPlaceAPI")) {
    		js = resources.Utilities.rawToJson(responseAdd);
    		Assert.assertEquals(js.get(field),value );
    	}
    	else if(apiName.equalsIgnoreCase("GetPlaceAPI")) {
    		js = resources.Utilities.rawToJson(responseGet);
    		Assert.assertEquals(js.get(field),value );
    	}
    	else if(apiName.equalsIgnoreCase("UpdatePlaceAPI")) {
    		js = resources.Utilities.rawToJson(responseUpdate);
    		Assert.assertEquals(js.get(field),value );
    	}
    	else if(apiName.equalsIgnoreCase("DeletePlaceAPI")) {
    		js = resources.Utilities.rawToJson(responseDelete);
  
    		Assert.assertEquals(js.get(field),value );
    	}
    	
    	logger.info("Response Validated");
    }

    @And("placeId is successfully generated for {string} with {string} method")
    public void PlaceidGeneration(String apiName, String apiMethod) throws MalformedURLException, IOException, URISyntaxException{
       // Assert.assertFalse(amr1.getPlace_id().isEmpty());
        placeId = amr1.getPlace_id();
       // placeIdList.add(amr1.getPlace_id());
        logger.info("PlaceId Obtained");
        responseGet=ApiRequestTrigger(apiName,apiMethod);
    }

    @Then("placeId is successfully generated to validate {string} field with value {string} using {string}")
    public void ResponseValidation(String fieldName,String fieldValue, String apiName) throws MalformedURLException, IOException, URISyntaxException {
        // Write code here that turns the phrase above into concrete actions
    	String value = resources.Utilities.KeyValueObtain(fieldName,responseGet);	
    	
    	Assert.assertEquals(value, fieldValue);
    	logger.info("Field Validated :"+fieldName);
    	
    }
    
    @Then("Use placeId to update the {string} {string} {string} {string} using {string} and {string} method")
    public void UpdatePlace(String website, String phone_number, String newlanguage, String newAddress, String apiName, String apiMethod) throws MalformedURLException, IOException, URISyntaxException {
        // Write code here that turns the phrase above into concrete actions
    	up = TestDataBuild.UpdatePlacePayload(website, phone_number, newlanguage, newAddress, amr1.getPlace_id());
    	
    	responseUpdate= ApiRequestTrigger(apiName, apiMethod);
    	logger.info("Place Id Update");
     	
    }
    
    @Then("Response Header should contain {string} as {string} for {string}")
    public void ResponseHeaderValidation(String statusCode, String value, String apiName) throws MalformedURLException, IOException, URISyntaxException {
        // Write code here that turns the phrase above into concrete actions
    	
    	if(apiName.equalsIgnoreCase("AddPlaceAPI")) {
    		Assert.assertEquals(responseAdd.statusCode(),Integer.parseInt(value));
    	}
    	else if(apiName.equalsIgnoreCase("GetPlaceAPI")) {
    		Assert.assertEquals(responseGet.statusCode(),Integer.parseInt(value));
    	}
    	else if(apiName.equalsIgnoreCase("UpdatePlaceAPI")) {
    		Assert.assertEquals(responseUpdate.statusCode(),Integer.parseInt(value));
    	}
    	else if(apiName.equalsIgnoreCase("DeletePlaceAPI")) {
    		Assert.assertEquals(responseDelete.statusCode(),Integer.parseInt(value));
    	}
    		
    	logger.info("Status Code Validated :"+value);
    }
    
    public void DeleteAllPlaceIds(String plId) {
    	APIResources resourceAPI = APIResources.valueOf("DeletePlaceAPI");
    	deletePlaces.DeleteMaps dm = new deletePlaces.DeleteMaps();
		dm.setPlace_id(plId);
		request1 = reusableMethods.deletePlace.DeleteMapsRequest(dm, request1);
		responseDelete = reusableMethods.deletePlace.DeleteMapsResponseString(request1, resourceAPI.getResource());
    	    	
    }

}
