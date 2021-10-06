package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Walkhthrough extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkhthrough);
    }

    public void toRegister(View view){
        Intent intent = new Intent(this, Register1.class);
        startActivity(intent);
    }

    public void toLoginPhone(View view){
        Intent intent = new Intent(this, LoginPhone.class);
        startActivity(intent);
    }
}