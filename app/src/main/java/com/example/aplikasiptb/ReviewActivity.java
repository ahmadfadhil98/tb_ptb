package com.example.aplikasiptb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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
import com.example.aplikasiptb.model.DUser;
import com.example.aplikasiptb.model.DetailUserItem;
import com.example.aplikasiptb.model.ResponseRegister;
import com.example.aplikasiptb.model.Review;
import com.example.aplikasiptb.model.ReviewItem;
import com.example.aplikasiptb.model.ReviewList;
import com.example.aplikasiptb.retrofit.PortalClient;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

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
    Button reviewBtn;

    Integer idHomestay,idUser;
    PortalClient portalClient;
    String token,baseUrl,komentar;
    TextInputEditText textKomentar;
    float rate;

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

        getIdUser();

        setReview();

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        rvReview = findViewById(R.id.rvReview);
        rvReview.setAdapter(reviewAdapter);
        rvReview.setLayoutManager(layoutManager);

        ratingBar = findViewById(R.id.star);
        textStar = findViewById(R.id.textStar);
        reviewBtn = findViewById(R.id.reviewBtn);
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingReview();
            }
        });
        textKomentar = findViewById(R.id.textKomentar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                ratingBar = ratingBar;
//                ratingBar.getRating() = rating;
                rate = rating;
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
    public void setReview(){
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
                                baseUrl+item.getFoto(),
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
    }

    public void getIdUser(){
        Call<DUser> call = portalClient.getDUser(token,token);
        call.enqueue(new Callback<DUser>() {
            @Override
            public void onResponse(Call<DUser> call, Response<DUser> response) {
                DUser dUser = response.body();
                if(dUser!=null){
                    idUser = dUser.getIdUser();
                    getReview();
                }else{
                    Toast.makeText(getApplicationContext(), "Tidak ada response untuk User", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DUser> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getReview(){
        Call<ResponseRegister> call = portalClient.reviewItem(token,idUser,idHomestay);
        call.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                ResponseRegister responseRegister = response.body();
                if(responseRegister!=null){
                    float ratingReview = responseRegister.getRating();
                    String komenReview = responseRegister.getKomentar();

                    ratingBar.setRating(ratingReview);
                    if(komenReview!=null){
                        textKomentar.setText(komenReview);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Belum ada memberikan rating",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ratingReview(){
        komentar = textKomentar.getText().toString();

        if(rate==0){
            textStar.setText("Nilai Rating Belum dimasukkan");
//            textStar.setTextColor();
            Toast.makeText(getApplicationContext(),"Nilai Rating Belum dimasukkan",Toast.LENGTH_SHORT).show();
        }else if (komentar.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Konfirmasi");
            builder.setMessage("Apakah anda yakin memberikan rating tanpa memberi komentar?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    Toast.makeText(getApplicationContext(),komentar,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    ratingPush();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }else{
            ratingPush();
        }
    }

    public void ratingPush(){

        Call<ResponseRegister> call = portalClient.review(
                token,
                idHomestay,
                idUser,
                rate,
                komentar
        );
        call.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                ResponseRegister responseRegister = response.body();
                if(responseRegister!=null){
                    String message = responseRegister.getMessage();
                    if(message!=null){
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), DetailHomestayActivity.class);
                        intent.putExtra("idHomestay",idHomestay);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Tidak ada response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
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