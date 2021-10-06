package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginPhone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
    }

    public void toMap(View view){
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    public void toLoginEmail(View view){
        Intent intent = new Intent(this, LoginEmail.class);
        startActivity(intent);
    }
}