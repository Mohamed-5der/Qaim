package com.qaim.qaim.Models.OtpResponse;

import com.google.gson.annotations.SerializedName;

public class OtpResponse {

    @SerializedName("code")
    private Long mCode;

    @SerializedName("data")
    private Data mData;

    @SerializedName("message")
    private String mMessage;

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
