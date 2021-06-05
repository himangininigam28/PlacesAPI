package resources;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import stepDefinitions.StepDefinition;

public class Utilities {
	private static final Logger logger = LogManager.getLogger(StepDefinition.class);
	static RequestSpecification req= null;
	public static RequestSpecification RequestSpecification() throws IOException, URISyntaxException, MalformedURLException {
		
		PrintStream stream = new PrintStream(new FileOutputStream("logging.txt"));
		ValidateURL(GetGlobalValues("baseURL"));
		if(req==null) {
			req=new RequestSpecBuilder()
				.setAccept(ContentType.JSON)
				.setBaseUri(GetGlobalValues("baseURL"))
				.addQueryParam(GetGlobalValues("queryParam1Key"), GetGlobalValues("queryParam1value"))
				.addFilter(RequestLoggingFilter.logRequestTo(stream))
				.addFilter(ResponseLoggingFilter.logResponseTo(stream))
				.build();
			logger.info("Request Spec ready");
		}
		return req;
	}
	
	public static String GetGlobalValues(String key) throws IOException {
		
		Properties prop = new Properties();
		
		FileInputStream fis = new FileInputStream("C:\\Users\\Aadiya Lijo\\eclpse-wksp\\PlacesAPI\\src\\test\\java\\global.properties");
		
		prop.load(fis);

		System.out.println(prop.getProperty(key));
		logger.info("variables ready");
		return prop.getProperty(key);
	}
	
	public static void ValidateURL(String key) throws IOException, URISyntaxException, MalformedURLException {
		
        URL obj = new URL(key);
        obj.toURI();
        logger.info("Valid URL");
				
	}
	
	public static ResponseSpecification ResponseSpecification() {
		ResponseSpecification res=new ResponseSpecBuilder()
				.build();
		return res;
				
	}
	
	public static JsonPath rawToJson(Response getResponse) {
		
		JsonPath jsGet= new JsonPath(getResponse.asString());
		return jsGet;
	}
	
	public static String KeyValueObtain(String field, Response getResponse) {
		JsonPath jsGet= new JsonPath(getResponse.asString());
		
		return jsGet.get(field);
		
	}
}
