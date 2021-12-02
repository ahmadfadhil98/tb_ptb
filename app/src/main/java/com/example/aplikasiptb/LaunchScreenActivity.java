package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class LaunchScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        int baseUrl = R.string.apiUrlLumen;
        String urlBase = getString(R.string.apiUrlLumen);
        Toast.makeText(getApplicationContext(),baseUrl, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),urlBase, Toast.LENGTH_LONG).show();
    }

    public void toWalkhthrough(View view){
        Intent intent = new Intent(this, WalkhthroughActivity.class);
        startActivity(intent);
        finish();
    }

}