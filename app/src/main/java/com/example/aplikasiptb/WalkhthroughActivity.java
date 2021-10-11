package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class WalkhthroughActivity extends AppCompatActivity {

    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkhthrough);
    }

    public void toRegister(View view){
        Intent intent = new Intent(this, Register1Activity.class);
        startActivity(intent);
    }

    public void toLoginPhone(View view){
        Intent intent = new Intent(this, LoginPhoneActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }

    }
}