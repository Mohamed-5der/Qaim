package com.qaim.qaim.Models.LoginResponse;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LoginResponse {

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	// New fields added
	@SerializedName("account_type")
	private String accountType;

	@SerializedName("errors")
	private List<String> errors;

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	// Getters and Setters for new fields
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "LoginResponse{" +
				"code='" + code + '\'' +
				", data='" + data + '\'' +
				", message='" + message + '\'' +
				", accountType='" + accountType + '\'' +
				", errors='" + errors + '\'' +
				'}';
	}
}
