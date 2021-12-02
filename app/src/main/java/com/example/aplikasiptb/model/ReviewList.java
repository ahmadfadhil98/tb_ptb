package com.example.aplikasiptb.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ReviewList{

    @SerializedName("review")
    private List<ReviewItem> review;

    public List<ReviewItem> getReview(){
        return review;
    }
}