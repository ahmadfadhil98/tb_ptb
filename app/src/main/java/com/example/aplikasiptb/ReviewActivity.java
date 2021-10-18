package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aplikasiptb.adapter.ReviewAdapter;
import com.example.aplikasiptb.model.Review;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    RecyclerView rvReview;
    ReviewAdapter reviewAdapter;

    ImageView iconBack;
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
}