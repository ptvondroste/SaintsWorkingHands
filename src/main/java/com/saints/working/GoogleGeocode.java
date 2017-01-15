package com.saints.working;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

public class GoogleGeocode {

	public static GoogleGeocodeApi getGeocode(String addressCode){ 
	
	String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+addressCode+"&key="+Geocode.getApikey();
	
	URL urlObj;
	GoogleGeocodeApi geoCode; 
	
	try{
		urlObj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		
		con.setRequestMethod("GET");
		
		int responseCode = con.getResponseCode();
		
		
		if(responseCode == 200){
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine=in.readLine()) !=null){
				response.append(inputLine);
				//System.out.println(inputLine);
			}
			in.close();
			//Parse JSON
			//System.out.println(response);
			
			Gson gson = new Gson();
			geoCode = gson.fromJson(response.toString(), GoogleGeocodeApi.class);
			
			return geoCode;
			//System.out.println(noaa.getResults().get(0).getGeometry().getLocation().getLat());
			//System.out.println(noaa.getResults().get(0).getGeometry().getLocation().getLng());
			
		}else{
			
		}
	}catch(MalformedURLException e){
		e.printStackTrace();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	return null;

}

public static String addressConcat(String street, String city, String state, String zipCode){

	String addressUrl = "";
	
	String fullAddress = street + ", " + city + ", " + state + " " + zipCode;
	
	String[] splitAddress = fullAddress.split(" ");
	
	for(int i=0; i<splitAddress.length; i++){
		
		if(i==0){
			addressUrl += splitAddress[0];
		}else{
		
		addressUrl += "+" + splitAddress[i];
		}
	}
	
	return addressUrl;
}

}
 