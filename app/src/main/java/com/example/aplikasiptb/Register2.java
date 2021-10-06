package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Register2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
    }

    public void toRegister3(View view){
        Intent intent = new Intent(this, Register3.class);
        startActivity(intent);
    }
}