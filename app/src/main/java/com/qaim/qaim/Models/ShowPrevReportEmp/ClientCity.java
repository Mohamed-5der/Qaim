package com.qaim.qaim.Models.ShowPrevReportEmp;

import com.google.gson.annotations.SerializedName;

public class ClientCity{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}
}