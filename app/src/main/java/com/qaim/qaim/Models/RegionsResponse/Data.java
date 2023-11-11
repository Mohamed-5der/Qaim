package com.qaim.qaim.Models.RegionsResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("cities")
	private List<CitiesItem> cities;

	public void setCities(List<CitiesItem> cities){
		this.cities = cities;
	}

	public List<CitiesItem> getCities(){
		return cities;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"cities = '" + cities + '\'' + 
			"}";
		}
}