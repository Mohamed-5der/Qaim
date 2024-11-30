package com.qaim.qaim.Models.CountriesResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data{

	@SerializedName("countries")
	private List<CountriesItem> countries;

	public List<CountriesItem> getCountries(){
		return countries;
	}
}