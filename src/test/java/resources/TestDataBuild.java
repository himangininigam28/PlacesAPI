package resources;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {
	
	public static addPlaces.AddMaps addPlacePayload(String name, String language, String address) {
		addPlaces.AddMaps am = new addPlaces.AddMaps();
		addPlaces.AddMapsLocation aml = new addPlaces.AddMapsLocation();
		
		List<String> types= new ArrayList<String>();
		am.setAccuracy(50);
		am.setAddress(address);
		am.setLanguage(language);
		am.setName(name);
		am.setPhone_number("(+91) 983 893 3937");
		am.setWebsite("http://google.com");
		aml.setLat(-38.383494);
		aml.setLng(33.427362);
		am.setLocation(aml);
		types.add("shoe park");
		types.add("shop");
		am.setTypes(types);
		
		return am;
	}
	
	public static updatePlaces.UpdateMaps UpdatePlacePayload(String website, String phone_number, String newlanguage, String newAddress, String placeId){
		
		updatePlaces.UpdateMaps um = new updatePlaces.UpdateMaps();
		um.setAddress(newAddress);
		um.setPlace_id(placeId);
		um.setLanguage(newlanguage);
		um.setWebsite(website);
		um.setPhone_number(phone_number);
		um.setKey("qaclick123");
		
		return um;
	}
	
}
