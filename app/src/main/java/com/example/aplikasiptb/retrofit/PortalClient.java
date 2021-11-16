package com.example.aplikasiptb.retrofit;

import com.example.aplikasiptb.model.AuthClass;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PortalClient {

    @FormUrlEncoded
    @POST("/login")
    Call<AuthClass> checkLogin(@Field("username") String username, @Field("password") String Password);
}
