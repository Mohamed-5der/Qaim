package com.qaim.qaim.Models.GetOrdersPreviewer;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("rows")
	private Rows rows;

	public void setRows(Rows rows){
		this.rows = rows;
	}

	public Rows getRows(){
		return rows;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"rows = '" + rows + '\'' + 
			"}";
		}
}