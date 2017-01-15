package com.saints.working;

import java.util.List;

public class GoogleGeocodeApi {

	
	private List<Results> results;
	
	public GoogleGeocodeApi(){
		
	}

	public List<Results> getResults() {
		return results;
	}

	public void setResults(List<Results> results) {
		this.results = results;
	}
}
