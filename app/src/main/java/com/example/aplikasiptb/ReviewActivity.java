package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.adapter.ReviewAdapter;
import com.example.aplikasiptb.model.Review;
import com.example.aplikasiptb.model.ReviewItem;
import com.example.aplikasiptb.model.ReviewList;
import com.example.aplikasiptb.retrofit.PortalClient;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {

    RecyclerView rvReview;
    ReviewAdapter reviewAdapter;
    AppCompatRatingBar ratingBar;
    TextView textStar;
    ImageView iconBack;
    Button uploadBtn;

    Integer idHomestay;
    PortalClient portalClient;
    String token;
    String baseUrl;

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

        idHomestay = getIntent().getIntExtra("idHomestay",0);
        reviewAdapter = new ReviewAdapter();

        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        token = preferences.getString("TOKEN","");

        baseUrl = getString(R.string.apiUrlLumen);
        Authent authent = new Authent();
        portalClient = authent.setPortalClient(baseUrl);

        Call<ReviewList> call = portalClient.getDReview(token,idHomestay);
        updateViewProgress(true);
        call.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {
                ReviewList reviewList = response.body();
                ArrayList<Review> reviews = new ArrayList<>();
                if(reviewList!=null){
                    List<ReviewItem> reviewItems = reviewList.getReview();
                    for (ReviewItem item: reviewItems){
                            Review review = new Review(
                                    item.getFoto(),
                                    item.getNama(),
                                    item.getKomentar(),
                                    item.getRating(),
                                    item.getUpdatedAt()
                            );
                            reviews.add(review);
                    }
                }
                reviewAdapter.setListReview(reviews);
                updateViewProgress(false);
            }

            @Override
            public void onFailure(Call<ReviewList> call, Throwable t) {
                updateViewProgress(false);
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });

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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DetailHomestayActivity.class);
        intent.putExtra("idHomestay",idHomestay);
        startActivity(intent);
        finish();
    }

    public void updateViewProgress(Boolean active){
        ProgressBar progressBar = findViewById(R.id.progressReview);
        if (active){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }
}