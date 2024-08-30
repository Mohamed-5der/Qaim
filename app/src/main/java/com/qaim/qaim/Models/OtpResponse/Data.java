package com.qaim.qaim.Models.OtpResponse;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user")
    private User mUser;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

}
