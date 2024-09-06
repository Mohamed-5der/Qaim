package com.qaim.qaim.Models.GetBalance;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("balance")
	private int balance;

	@SerializedName("available")
	private String available;

	@SerializedName("rows")
	private List<RowsItem> rows;

	@SerializedName("message")
	private String message;

	public int getBalance(){
		return balance;
	}

	public String getAvailable(){
		return available;
	}

	public List<RowsItem> getRows(){
		return rows;
	}

	public String getMessage(){
		return message;
	}
}