package com.qaim.qaim.Models.ShowPrevReportEmp;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("row")
	private Row row;

	public Row getRow(){
		return row;
	}
}