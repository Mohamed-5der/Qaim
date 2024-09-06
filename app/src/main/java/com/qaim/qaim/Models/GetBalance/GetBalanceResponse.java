package com.qaim.qaim.Models.GetBalance;

import com.google.gson.annotations.SerializedName;

public class GetBalanceResponse{

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	public int getCode(){
		return code;
	}

	public Data getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}
}