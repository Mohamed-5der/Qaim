package com.qaim.qaim.Models.ReportCompleted.sendFeedBack;

import com.google.gson.annotations.SerializedName;

public class FeedBackResponse {
    @SerializedName("data")
    private DataFeedBack dataObject;

    @SerializedName("message")
    private String message;

    @SerializedName("code")
    private int code;

    public DataFeedBack getData() {
        return dataObject;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public void setData(DataFeedBack dataObject) {
        this.dataObject = dataObject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

class DataFeedBack {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
