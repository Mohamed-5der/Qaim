package com.qaim.qaim.Models.TypesResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

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