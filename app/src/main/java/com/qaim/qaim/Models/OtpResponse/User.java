package com.qaim.qaim.Models.OtpResponse;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("city")
    private City mCity;

    @SerializedName("city_id")
    private Long mCityId;

    @SerializedName("country_code")
    private String mCountryCode;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("id")
    private Long mId;

    @SerializedName("image")
    private String mImage;

    @SerializedName("is_verified")
    private int mIsVerified;

    @SerializedName("license")
    private Object mLicense;

    @SerializedName("name")
    private String mName;

    @SerializedName("phone")
    private String mPhone;

    @SerializedName("token")
    private String mToken;

    // Getters and setters
    public City getCity() {
        return mCity;
    }

    public void setCity(City city) {
        mCity = city;
    }

    public Long getCityId() {
        return mCityId;
    }

    public void setCityId(Long cityId) {
        mCityId = cityId;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(String countryCode) {
        mCountryCode = countryCode;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public int getIsVerified() {
        return mIsVerified;
    }

    public void setIsVerified(int isVerified) {
        mIsVerified = isVerified;
    }

    public Object getLicense() {
        return mLicense;
    }

    public void setLicense(Object license) {
        mLicense = license;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }
}
