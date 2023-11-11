package com.qaim.qaim.Classes;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SendImage {
    MultipartBody.Part body ;
    RequestBody name ;

    public SendImage(MultipartBody.Part body, RequestBody name) {
        this.body = body;
        this.name = name;
    }
}
