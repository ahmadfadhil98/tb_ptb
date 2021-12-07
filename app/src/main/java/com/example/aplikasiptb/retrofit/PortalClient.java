package com.example.aplikasiptb.retrofit;

import com.example.aplikasiptb.model.Auth;
import com.example.aplikasiptb.model.AvatarList;
import com.example.aplikasiptb.model.DHome;
import com.example.aplikasiptb.model.DUser;
import com.example.aplikasiptb.model.FasilitasHomestayList;
import com.example.aplikasiptb.model.HomestayList;
import com.example.aplikasiptb.model.ResponseRegister;
import com.example.aplikasiptb.model.ReviewList;


import java.sql.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PortalClient {

    @FormUrlEncoded
    @POST("/login")
    Call<Auth> checkLogin(
            @Field("username") String username,
            @Field("password") String Password
    );

    @GET("api/homestay")
    Call<HomestayList> getHomestay(
            @Header("token") String token
    );

    @GET("api/homestay/{id}")
    Call<DHome> getHome(
            @Header("token") String token,
            @Path("id") int homestayId
    );

    @GET("api/dfasilitas/{id}")
    Call<FasilitasHomestayList> getDFasilitas(
            @Header("token") String token,
            @Path("id") int homestayId
    );

    @GET("api/review/{id}")
    Call<ReviewList> getDReview(
            @Header("token") String token,
            @Path("id") int homestayId
    );

    @FormUrlEncoded
    @POST("/register")
    Call<ResponseRegister> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String Password
    );

    @FormUrlEncoded
    @POST("api/duser/store")
    Call<ResponseRegister> registerUser(
            @Header("token") String token,
            @Field("id") int id,
            @Field("nama") String nama,
            @Field("jk") int jk,
            @Field("no_hp") String no_hp,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tgl_lahir") String tgl_lahir
    );

    @FormUrlEncoded
    @POST("api/user/update/{id}")
    Call<ResponseRegister> updatePass(
            @Header("token") String token,
            @Path("id") int id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/user/update/{id}")
    Call<ResponseRegister> updateProfil(
            @Header("token") String token,
            @Path("id") int id,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("jk") int jk,
            @Field("no_hp") String no_hp,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tgl_lahir") String tgl_lahir
    );

    @GET("api/duser/{id}")
    Call<DUser> getDUser(
            @Header("token") String token,
            @Path("id") String tokenString
    );

    @POST("/logout")
    Call<ResponseRegister> logout(
            @Header("token") String token
    );

    @GET("api/avatar")
    Call<AvatarList> getAvatar(@Header("token") String token);

}
