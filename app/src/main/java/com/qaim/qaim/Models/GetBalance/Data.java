package com.qaim.qaim.Models.GetBalance;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("balance")
	private int balance;

	@SerializedName("available")
	private String available;

	@SerializedName("message")
	private String message;

	public void setBalance(int balance){
		this.balance = balance;
	}

	public int getBalance(){
		return balance;
	}

	public void setAvailable(String available){
		this.available = available;
	}

	public String getAvailable(){
		return available;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"balance = '" + balance + '\'' + 
			",available = '" + available + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}