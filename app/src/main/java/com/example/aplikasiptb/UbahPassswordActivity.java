package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.model.ResponseRegister;
import com.example.aplikasiptb.retrofit.PortalClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPassswordActivity extends AppCompatActivity {

    ImageView iconBack;
    EditText textPassLama,textPassBaru,textPassConf;
    CheckBox checkBox;
    TextView warPassLama,warPassBaru,warPassConf;
    String token;
    Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_passsword);

        textPassLama = findViewById(R.id.pass_lama);
        textPassBaru = findViewById(R.id.pass_baru);
        textPassConf = findViewById(R.id.pass_conf);

        warPassLama = findViewById(R.id.warPasslama);
        warPassBaru = findViewById(R.id.warPassBaru);
        warPassConf = findViewById(R.id.warPassConf);

        textPassLama.setText("Password");
        textPassLama.setEnabled(false);

        checkBox = findViewById(R.id.checkBox);

        userId = getIntent().getIntExtra("userId",0);

        textPassBaru.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()<6){
                    warPassBaru.setVisibility(View.VISIBLE);
                    warPassBaru.setText("Password minimal 6 karakter");
                }else{
                    warPassBaru.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textPassConf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = textPassBaru.getText().toString();

                if (password.equals(s.toString())){
                    warPassConf.setVisibility(View.GONE);

                }else{
                    warPassConf.setVisibility(View.VISIBLE);
                    warPassConf.setText("Password belum sama");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        token = preferences.getString("TOKEN","");

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                textPassLama.setEnabled(false);
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

    public void toDetailProfil(View view){
        String passLama = textPassBaru.getText().toString();
        String passBaru = textPassBaru.getText().toString();
        String passConf = textPassConf.getText().toString();

        if (!checkBox.isChecked()&&passLama.equals("")){
            warPassLama.setVisibility(View.VISIBLE);
            warPassLama.setText("Password Lama Masih Kosong");
        }else if(passBaru.equals("")){
            warPassBaru.setVisibility(View.VISIBLE);
            warPassBaru.setText("Password Baru Masih Kosong");
        }else if (passConf.equals("")){
            warPassConf.setVisibility(View.VISIBLE);
            warPassConf.setText("Konfirmasi Password Masih Kosong");
        }else if (!passConf.equals(passBaru)){
            warPassBaru.setVisibility(View.VISIBLE);
            warPassBaru.setText("Password Belum Sama");
            warPassConf.setVisibility(View.VISIBLE);
            warPassConf.setText("Password Belum Sama");
        }else{
//            Toast.makeText(getApplicationContext(),userId.toString(),Toast.LENGTH_SHORT).show();
            Authent authent = new Authent();
            PortalClient portalClient = authent.setPortalClient(getString(R.string.apiUrlLumen));

            Call<ResponseRegister> call = portalClient.updatePass(token,userId,passBaru);
            call.enqueue(new Callback<ResponseRegister>() {
                @Override
                public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                    ResponseRegister responseRegister = response.body();
                    if (responseRegister != null){
                        if (responseRegister.getMessage()!=null){
                            String message = responseRegister.getMessage();

                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), DetailProfilActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Tidak ada response",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseRegister> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DetailProfilActivity.class);
        startActivity(intent);
        finish();
    }
}