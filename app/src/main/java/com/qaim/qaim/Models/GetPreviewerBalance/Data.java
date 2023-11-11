package com.qaim.qaim.Models.GetPreviewerBalance;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("balance")
	private double balance;

	@SerializedName("available")
	private double available;

	@SerializedName("message")
	private String message;

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getAvailable() {
		return available;
	}

	public void setAvailable(double available) {
		this.available = available;
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