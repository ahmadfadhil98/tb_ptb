package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginPhoneActivity extends AppCompatActivity {

    ImageView iconBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        iconBack = findViewById(R.id.backImg);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void toMap(View view){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
        finish();
    }

    public void toLoginEmail(View view){
        Intent intent = new Intent(this, LoginEmailActivity.class);
        startActivity(intent);
    }
}