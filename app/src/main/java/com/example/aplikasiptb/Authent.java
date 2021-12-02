package com.example.aplikasiptb;

import com.example.aplikasiptb.retrofit.PortalClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Authent {
    public PortalClient portalClient;

    public PortalClient setPortalClient(String baseUrl){

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://tbptbklp4.herokuapp.com/")
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.portalClient = retrofit.create(PortalClient.class);

        return portalClient;
    }

}
