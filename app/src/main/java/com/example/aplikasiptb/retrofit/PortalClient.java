package com.example.aplikasiptb.retrofit;

import com.example.aplikasiptb.model.Auth;
import com.example.aplikasiptb.model.HomestayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PortalClient {

    @FormUrlEncoded
    @POST("/login")
    Call<Auth> checkLogin(@Field("username") String username, @Field("password") String Password);

    @GET("api/homestay")
    Call<HomestayList> getHomestay(@Header("token") String token);
}
