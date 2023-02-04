package com.Abdo.qaim.Models.GetBalance;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("balance")
	private int balance;

	@SerializedName("message")
	private String message;

	public void setBalance(int balance){
		this.balance = balance;
	}

	public int getBalance(){
		return balance;
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
			",message = '" + message + '\'' + 
			"}";
		}
}