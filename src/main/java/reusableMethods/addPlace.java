package reusableMethods;

import static io.restassured.RestAssured.given;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class addPlace {
	
	// Add Maps request
	public static RequestSpecification AddMapsRequest(addPlaces.AddMaps am, RequestSpecification req) {
		RequestSpecification request = given().log().all()
				.spec(req)
				.body(am);
		return request;
	}
	
	// Add Maps Response
	
	public static addPlaces.AddMapsResponse AddMapsResponse(RequestSpecification request, String resourceAPI) {
		

		addPlaces.AddMapsResponse response = request
		.expect().defaultParser(Parser.JSON)
		.when()
		.post(resourceAPI)
		.as(addPlaces.AddMapsResponse.class);
			
		return response;
	}
	
	public static String Response(RequestSpecification request) {
		
		String response =request
		.expect().defaultParser(Parser.JSON)
		.when()
		.post("/maps/api/place/add/json")
		.then().log().all()
		.extract()
		.response()
		.asString();
			
		return response;
	}
	

	
}
