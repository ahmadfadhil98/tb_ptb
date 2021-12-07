package com.example.aplikasiptb.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRegister{

    @SerializedName("password")
    private List<String> password;

    @SerializedName("id")
    private int id;

    @SerializedName("message")
    private String message;

    @SerializedName("email")
    private List<String> email;

    @SerializedName("no_hp")
    private List<String> no_hp;

    @SerializedName("token")
    private String token;

    @SerializedName("username")
    private List<String> username;

    public List<String> getPassword(){
        return password;
    }

    public int getId(){
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getMessage(){
        return message;
    }

    public List<String> getEmail(){
        return email;
    }

    public List<String> getNo_hp() { return no_hp; }

    public List<String> getUsername(){
        return username;
    }
}