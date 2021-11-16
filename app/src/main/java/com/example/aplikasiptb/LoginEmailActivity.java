package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.model.AuthClass;
import com.example.aplikasiptb.retrofit.PortalClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginEmailActivity extends AppCompatActivity {

    ImageView iconBack;
    EditText editUsername;
    EditText editPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        editUsername = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginPhoneActivity.class);
        startActivity(intent);
        finish();
    }

    public void checkLogin(View view){


        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tbptbklp4.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PortalClient portalClient = retrofit.create(PortalClient.class);

        Call<AuthClass> call = portalClient.checkLogin(username,password);
        updateViewProgress(true);
        call.enqueue(new Callback<AuthClass>() {
            @Override
            public void onResponse(Call<AuthClass> call, Response<AuthClass> response) {
                AuthClass authClass = response.body();
                if (authClass != null ){


                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Kombinasi Username dan Password anda salah",Toast.LENGTH_SHORT).show();
                }
                updateViewProgress(false);
            }

            @Override
            public void onFailure(Call<AuthClass> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal menghubungi server",Toast.LENGTH_SHORT).show();
                updateViewProgress(false);
            }
        });

//        if (username.equals("admin")&& password.equals("admin")){
//            Intent intent = new Intent(this, MapActivity.class);
//            startActivity(intent);
//            finish();
//        }else {
//            Toast.makeText(this,"Password/Username Anda salah",Toast.LENGTH_SHORT).show();
//        }
    }

    public void updateViewProgress(Boolean active){
        Button buttonLogin = findViewById(R.id.buttonLogin);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView kebijakan = findViewById(R.id.kebijakan);
        if (active){
            editUsername.setEnabled(false);
            editPassword.setEnabled(false);
            buttonLogin.setVisibility(View.GONE);
            kebijakan.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            editUsername.setEnabled(true);
            editPassword.setEnabled(true);
            buttonLogin.setVisibility(View.VISIBLE);
            kebijakan.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}