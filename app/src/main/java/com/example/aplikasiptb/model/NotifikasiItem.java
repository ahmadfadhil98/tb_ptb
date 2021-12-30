package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NotifikasiItem{

    @SerializedName("user_id")
    private int userId;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    @SerializedName("created_at")
    private Date createdAt;

    public int getUserId(){
        return userId;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getMessage(){
        return message;
    }

    public int getStatus(){
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}