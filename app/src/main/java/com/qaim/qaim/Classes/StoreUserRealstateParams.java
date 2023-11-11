package com.qaim.qaim.Classes;

public class StoreUserRealstateParams {
     String title ;String description ; int type_id ; int city_id ; int region_id ; double latitude ; double longitude ; String address ; String distance;

    public StoreUserRealstateParams(String title, String description, int type_id, int city_id, int region_id, double latitude, double longitude, String address, String distance) {
        this.title = title;
        this.description = description;
        this.type_id = type_id;
        this.city_id = city_id;
        this.region_id = region_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.distance = distance;

    }
}
