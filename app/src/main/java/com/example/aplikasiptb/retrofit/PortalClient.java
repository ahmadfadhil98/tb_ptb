package com.example.aplikasiptb.retrofit;

import com.example.aplikasiptb.model.Auth;
import com.example.aplikasiptb.model.AvatarList;
import com.example.aplikasiptb.model.DBooking;
import com.example.aplikasiptb.model.DHome;
import com.example.aplikasiptb.model.DUser;
import com.example.aplikasiptb.model.DetailHomestay;
import com.example.aplikasiptb.model.FasilitasHomestayList;
import com.example.aplikasiptb.model.HomestayList;
import com.example.aplikasiptb.model.NotifikasiList;
import com.example.aplikasiptb.model.PembayaranList;
import com.example.aplikasiptb.model.ResponseRegister;
import com.example.aplikasiptb.model.ReviewList;
import com.example.aplikasiptb.model.UnitList;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PortalClient {

    @FormUrlEncoded
    @POST("login")
    Call<Auth> checkLogin(
            @Field("username") String username,
            @Field("password") String Password,
            @Field("fcm_token") String token
    );

    @GET("api/duserOtp/{phone}")
    Call<ResponseRegister> checkPhone(
            @Path("phone") String phone
    );

    @FormUrlEncoded
    @POST("api/otpUser")
    Call<Auth> loginHp(
            @Field("id_user") Integer id_user,
            @Field("fcm_token") String fcm_token
    );

    @GET("api/homestay")
    Call<HomestayList> getHomestay(
            @Header("token") String token
    );

    @GET("api/dhome/{id}")
    Call<DetailHomestay> setDHome(
            @Header("token") String token,
            @Path("id") Integer id
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
    @POST("register")
    Call<ResponseRegister> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String Password,
            @Field("fcm_token") String fcm_token
    );

    @FormUrlEncoded
    @POST("api/duser/store")
    Call<ResponseRegister> registerAvatar(
            @Header("token") String token,
            @Field("id") int id,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("api/user/update/{id}")
    Call<ResponseRegister> updateAvatar(
            @Header("token") String token,
            @Path("id") int id,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("api/user/update/{id}")
    Call<ResponseRegister> registerUser(
            @Header("token") String token,
            @Path("id") int id,
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
            @Field("username") String username,
            @Field("email") String email,
            @Field("jk") int jk,
            @Field("no_hp") String no_hp,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tgl_lahir") String tgl_lahir
    );

    @FormUrlEncoded
    @POST("api/review/store")
    Call<ResponseRegister> review(
            @Header("token") String token,
            @Field("homestay_id") int homestay_id,
            @Field("user_id") int user_id,
            @Field("rating") float rating,
            @Field("komentar") String komentar
    );

    @GET("api/review/{user_id}/{homestay_id}")
    Call<ResponseRegister> reviewItem(
            @Header("token") String token,
            @Path("user_id") int user_id,
            @Path("homestay_id") int homestay_id
    );

    @GET("api/duser/{id}")
    Call<DUser> getDUser(
            @Header("token") String token,
            @Path("id") String tokenString
    );

    @GET("api/unit/{id}")
    Call<UnitList> getUnit(
            @Header("token") String token,
            @Path("id") int id
    );

    @POST("logout")
    Call<ResponseRegister> logout(
            @Header("token") String token
    );

    @GET("api/avatar")
    Call<AvatarList> getAvatar(@Header("token") String token);

    @GET("api/pembayaran/{id}")
    Call<PembayaranList> getPembayaran(@Header("token") String token,@Path("id") int id);

    @FormUrlEncoded
    @POST("api/booking/store")
    Call<ResponseRegister> booking(
            @Header("token") String token,
            @Field("unit_id") Integer unit_id,
            @Field("check_in") String check_in,
            @Field("check_out") String check_out,
            @Field("pembayaran_id") Integer pembayaran_id
    );

    @FormUrlEncoded
    @POST("api/notifikasi/store")
    Call<ResponseRegister> addNotif(
            @Header("token") String token,
            @Field("title") String title,
            @Field("message") String message
    );

    @GET("api/notifikasi/show")
    Call<NotifikasiList> getNotif(
            @Header("token") String token
    );

    @GET("api/booking/{id}")
    Call<DBooking> getDBook(
            @Header("token") String token,
            @Path("id") Integer id
    );

}
