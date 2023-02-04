package com.Abdo.qaim.Models.RealstateStoreUserResponse;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("row")
	private Row row;

	public void setRow(Row row){
		this.row = row;
	}

	public Row getRow(){
		return row;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"row = '" + row + '\'' + 
			"}";
		}
}