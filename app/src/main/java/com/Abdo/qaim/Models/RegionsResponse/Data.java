package com.Abdo.qaim.Models.RegionsResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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