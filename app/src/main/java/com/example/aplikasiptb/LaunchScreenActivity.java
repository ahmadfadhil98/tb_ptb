package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;

public class LaunchScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
    }

    public void pindahkeReview(View view){
        Intent intent = new Intent(this, Review.class);
        startActivity(intent);
    }

    public void profil(View view){
        Intent intent = new Intent(this, Profil.class);
        startActivity(intent);
    }

    public void map(View view){
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }
}