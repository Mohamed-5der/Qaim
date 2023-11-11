package com.qaim.qaim.Classes;

public class UpdateRealstateUserParams {
    int real_estate_id ; String description ; int type_id ; int city_id ; int region_id ; double latitude ; double longitude ; String address ; int distance ; String title ;

    public UpdateRealstateUserParams(int real_estate_id, String description, int type_id, int city_id, int region_id, double latitude, double longitude, String address, int distance, String title) {
        this.real_estate_id = real_estate_id;
        this.description = description;
        this.type_id = type_id;
        this.city_id = city_id;
        this.region_id = region_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.distance = distance;
        this.title = title;
    }
}
