package com.Abdo.qaim.Models.TypesResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data{

	@SerializedName("types")
	private List<TypesItem> types;

	public void setTypes(List<TypesItem> types){
		this.types = types;
	}

	public List<TypesItem> getTypes(){
		return types;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"types = '" + types + '\'' + 
			"}";
		}
}