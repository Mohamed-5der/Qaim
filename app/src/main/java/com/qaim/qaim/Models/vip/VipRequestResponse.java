package com.qaim.qaim.Models.vip;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VipRequestResponse {
    @SerializedName("data")
    private Object data;

    @SerializedName("errors")
    private List<String> errors;

    @SerializedName("message")
    private String message;

    @SerializedName("code")
    private int code;

    // Getters and Setters
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
