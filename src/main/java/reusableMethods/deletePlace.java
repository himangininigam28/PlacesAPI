package reusableMethods;

import static io.restassured.RestAssured.given;

import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class deletePlace {
	
	// Update Maps request
	public static RequestSpecification DeleteMapsRequest(deletePlaces.DeleteMaps dm, RequestSpecification req) {
		RequestSpecification request = given().log().all()
				//.queryParam("place_id", placeId)
				.spec(req)
				.body(dm);
		return request;
	}
	
	// Add Maps Response
	
	public static updatePlaces.UpdateMaps UpdateMapsResponse(RequestSpecification request, String resourceAPI) {
		

		updatePlaces.UpdateMaps response = request
		
		.expect().defaultParser(Parser.JSON)
		.when()
		.put(resourceAPI)
		.as(updatePlaces.UpdateMaps.class);
			// checkout the branch
		return response;
	}
	
	public static Response DeleteMapsResponseString(RequestSpecification request, String resourceAPI) {
		
		Response response =request
		.expect().defaultParser(Parser.JSON)
		.when()
		.delete(resourceAPI)
		.then().log().all()
		.extract()
		.response()
		;
			
		return response;
	}

}
