package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.model.ResponseRegister;
import com.example.aplikasiptb.retrofit.PortalClient;
import com.google.android.material.chip.Chip;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register3Activity extends AppCompatActivity {

    ImageView iconBack;
    EditText textUsername,textPassword,textEmail,textKonfirmasi;
    TextView warUsername,warEmail,warPassword,warKonfirmasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        textUsername = findViewById(R.id.textUsername);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        textKonfirmasi = findViewById(R.id.konfirmasi);

        warUsername = findViewById(R.id.warningUsername);
        warEmail = findViewById(R.id.warningEmail);
        warPassword = findViewById(R.id.warningPassword);
        warKonfirmasi = findViewById(R.id.warningKonfirmasi);

        textPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()<6){
                    warPassword.setVisibility(View.VISIBLE);
                    warPassword.setText("Password minimal 6 karakter");
                }else{
                    warPassword.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textKonfirmasi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = textPassword.getText().toString();

                if (password.equals(s.toString())){
                    warKonfirmasi.setVisibility(View.GONE);

                }else{
                    warKonfirmasi.setVisibility(View.VISIBLE);
                    warKonfirmasi.setText("Password belum sama");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    public void toRegister2(View view){
        String username = textUsername.getText().toString();
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();
        String konfirmasi = textKonfirmasi.getText().toString();

        warUsername.setVisibility(View.GONE);
        warEmail.setVisibility(View.GONE);
        warPassword.setVisibility(View.GONE);
        warKonfirmasi.setVisibility(View.GONE);

        if (username.equals("")){
            warUsername.setVisibility(View.VISIBLE);
            warUsername.setText("Username masih kosong");
        }else if (email.equals("")){
            warEmail.setVisibility(View.VISIBLE);
            warEmail.setText("Email masih kosong");
        }else if (password.equals("")){
            warPassword.setVisibility(View.VISIBLE);
            warPassword.setText("Username masih kosong");
        }else if (password.length()<6){
            warPassword.setVisibility(View.VISIBLE);
            warPassword.setText("Password minimal 6 karakter");
        }else if (konfirmasi.equals("")){
            warKonfirmasi.setVisibility(View.VISIBLE);
            warKonfirmasi.setText("Konfirmasi Password masih kosong");
        }else if (!konfirmasi.equals(password)){
            warPassword.setVisibility(View.VISIBLE);
            warPassword.setText("Password belum sama");
            warKonfirmasi.setVisibility(View.VISIBLE);
            warKonfirmasi.setText("Password belum sama");
        }else{
            Authent authent = new Authent();
            PortalClient portalClient = authent.setPortalClient(getString(R.string.apiUrlLumen));

            Call<ResponseRegister> call = portalClient.register(username,email,password);
            updateViewProgress(true);
            call.enqueue(new Callback<ResponseRegister>() {
                @Override
                public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                    ResponseRegister responseRegister = response.body();
                    if (responseRegister != null){
                        List<String> user = responseRegister.getUsername();
                        List<String> emailList = responseRegister.getEmail();

                        if (user!=null&& !user.isEmpty()){
                            warUsername.setVisibility(View.VISIBLE);
                            warUsername.setText("Username sudah terdaftar");
                        }else if(emailList!=null&& !emailList.isEmpty()){
                            warEmail.setVisibility(View.VISIBLE);
                            warEmail.setText("Alamat Email sudah terdaftar");
                        }else if (responseRegister.getMessage()!=null){
                            String message = responseRegister.getMessage();
                            Integer idUser = responseRegister.getId();
                            String token = responseRegister.getToken();

                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                            SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("TOKEN",token);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), RegisterAvatarActivity.class);
                            intent.putExtra("userId",idUser);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Tidak ada response",Toast.LENGTH_SHORT).show();
                    }
                    updateViewProgress(false);
                }

                @Override
                public void onFailure(Call<ResponseRegister> call, Throwable t) {
                    updateViewProgress(false);
                    Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WalkhthroughActivity.class);
        startActivity(intent);
        finish();
    }

    public void updateViewProgress(Boolean active){
        Button button = findViewById(R.id.buttonDaftar3);
        ProgressBar progressBar = findViewById(R.id.progressRegister3);
        if (active){
            textUsername.setEnabled(false);
            textEmail.setEnabled(false);
            textPassword.setEnabled(false);
            textKonfirmasi.setEnabled(false);
            button.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            textUsername.setEnabled(true);
            textEmail.setEnabled(true);
            textPassword.setEnabled(true);
            textKonfirmasi.setEnabled(true);
            button.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}