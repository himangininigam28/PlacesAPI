package reusableMethods;

import static io.restassured.RestAssured.given;

import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class updatePlace {
	
	// Update Maps request
		public static RequestSpecification UpdateMapsRequest(updatePlaces.UpdateMaps um, RequestSpecification req, String placeId) {
			RequestSpecification request = given().log().all()
					.queryParam("place_id", placeId)
					.spec(req)
					.body(um);
			return request;
		}
		
		// Add Maps Response
		
		public static updatePlaces.UpdateMaps UpdateMapsResponse(RequestSpecification request, String resourceAPI) {
			

			updatePlaces.UpdateMaps response = request
			
			.expect().defaultParser(Parser.JSON)
			.when()
			.put(resourceAPI)
			.as(updatePlaces.UpdateMaps.class);
				
			return response;
		}
		
		public static Response UpdateMapsResponseString(RequestSpecification request, String resourceAPI) {
			
			Response response =request
			.expect().defaultParser(Parser.JSON)
			.when()
			.put(resourceAPI)
			.then().log().all()
			.extract()
			.response()
			;
				
			return response;
		}

}
