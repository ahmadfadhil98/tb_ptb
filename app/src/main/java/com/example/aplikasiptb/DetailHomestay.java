package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DetailHomestay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_homestay);
    }

    public void toReview(View view){
        Intent intent = new Intent(this, Review.class);
        startActivity(intent);
    }

    public void toBooking(View view){
        Intent intent = new Intent(this, Booking.class);
        startActivity(intent);
    }
}