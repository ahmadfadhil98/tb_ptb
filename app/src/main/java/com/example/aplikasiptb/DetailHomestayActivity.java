package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DetailHomestayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_homestay);
    }

    public void toReview(View view){
        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
    }

    public void toBooking(View view){
        Intent intent = new Intent(this, BookingActivity.class);
        startActivity(intent);
    }

    public void toMapArah(View view){
        Intent intent = new Intent(this, ArahActivity.class);
        startActivity(intent);
    }
}