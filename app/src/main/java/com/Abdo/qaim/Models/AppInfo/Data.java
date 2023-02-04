package com.Abdo.qaim.Models.AppInfo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data{

	@SerializedName("info")
	private List<Object> info;

	public void setInfo(List<Object> info){
		this.info = info;
	}

	public List<Object> getInfo(){
		return info;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"info = '" + info + '\'' + 
			"}";
		}
}