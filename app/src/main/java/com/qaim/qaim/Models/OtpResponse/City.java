package com.qaim.qaim.Models.OtpResponse;

import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("id")
    private Long mId;

    @SerializedName("name")
    private String mName;

    // Getters and setters
    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
