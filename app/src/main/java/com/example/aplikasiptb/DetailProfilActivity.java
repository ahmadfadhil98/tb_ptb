package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.model.DUser;
import com.example.aplikasiptb.model.DetailUserItem;
import com.example.aplikasiptb.retrofit.PortalClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProfilActivity extends AppCompatActivity {

    ImageView iconBack;
    TextView textNama,textUsername,textEmail,textPassword,textJk,textHp,textLahir;
    Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profil);

        textNama = findViewById(R.id.textNamaDProfil);
        textUsername = findViewById(R.id.textUserDProfil);
        textEmail = findViewById(R.id.textEmailDProfil);
        textPassword = findViewById(R.id.textPasswordProfil);
        textJk = findViewById(R.id.textJk);
        textHp = findViewById(R.id.textHpProfil);
        textLahir = findViewById(R.id.textLahir);

        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");

        Authent authent = new Authent();
        PortalClient portalClient = authent.setPortalClient(getString(R.string.apiUrlLumen));

        Call<DUser> call = portalClient.getDUser(token,token);
        call.enqueue(new Callback<DUser>() {
            @Override
            public void onResponse(Call<DUser> call, Response<DUser> response) {
                DUser dUser = response.body();
                if(dUser!=null){
                    List<DetailUserItem> detailUserItems = dUser.getDetailUser();
                    userId = dUser.getIdUser();
                    String jk;
                    for (DetailUserItem item : detailUserItems){

                        if (item.getJk()==1){
                            jk ="Laki-laki";
                        }else{
                            jk = "Perempuan";
                        }

                        textNama.setText(item.getNama());
                        textUsername.setText(item.getUsername());
                        textEmail.setText(item.getEmail());
                        textJk.setText(jk);
                        textHp.setText(item.getNoHp());
                        textLahir.setText(item.getTempatLahir()+", "+item.getTglLahir());
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Tidak ada response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DUser> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
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

    public void toUbahPassword(View view){

        Intent intent = new Intent(this, UbahPassswordActivity.class);
        intent.putExtra("userId",userId);
        startActivity(intent);
        finish();
    }

    public void toEditProfil(View view){
        Intent intent = new Intent(this, UpdateProfilActivity.class);
        intent.putExtra("userId",userId);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
        finish();
    }
}