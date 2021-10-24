package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.aplikasiptb.adapter.ReviewAdapter;
import com.example.aplikasiptb.model.Review;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    RecyclerView rvReview;
    ReviewAdapter reviewAdapter;
    AppCompatRatingBar ratingBar;
    TextView textStar;
    ImageView iconBack;
    Button uploadBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        iconBack = findViewById(R.id.backImg);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        reviewAdapter = new ReviewAdapter();
        reviewAdapter.setListReview(
                generateData()
        );
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);

        rvReview = findViewById(R.id.rvReview);
        rvReview.setAdapter(reviewAdapter);
        rvReview.setLayoutManager(layoutManager);

        ratingBar = findViewById(R.id.star);
        textStar = findViewById(R.id.textStar);
        uploadBtn = findViewById(R.id.uploadBtn);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                ratingBar = ratingBar;
//                ratingBar.getRating() = rating;
                if (rating==5){
                    textStar.setText("Sangat Bagus");
                }else if (rating<5&&rating>1){
                    textStar.setText("Bagus");
                }else if (rating>0&&rating<2){
                    textStar.setText("Kurang Bagus");
                }else{
                    textStar.setText("");
                }
            }
        });

    }

    public ArrayList<Review> generateData(){
        ArrayList<Review> listReview = new ArrayList<>();
        listReview.add(new Review("Ahmad Fadhil",
                "Temnpat nya bagus",
                1,
                "1 Februari 2012"));
        listReview.add(new Review("Ahmad Fadhil",
                "Temnpat nya bersih",
                2,
                "1 Februari 2012"));
        listReview.add(new Review("Ahmad Fadhil",
                "Temnpat nya mantap",
                3,
                "1 Februari 2012"));
        listReview.add(new Review("Ahmad Fadhil",
                "Temnpat nya keren",
                4,
                "1 Februari 2012"));
        return listReview;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DetailHomestayActivity.class);
        startActivity(intent);
        finish();
    }
}