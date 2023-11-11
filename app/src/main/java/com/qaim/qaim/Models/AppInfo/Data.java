package com.qaim.qaim.Models.AppInfo;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("info")
	private Info info;

	public void setInfo(Info info){
		this.info = info;
	}

	public Info getInfo(){
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