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
import com.example.aplikasiptb.model.ResponseRegister;
import com.example.aplikasiptb.retrofit.PortalClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilActivity extends AppCompatActivity {

    ImageView iconBack,imgAvatar;
    int idUser;
    TextView textNama,textEmail;
    PortalClient portalClient;
    String token,baseUrl;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        imgAvatar = findViewById(R.id.imgAvatarProfil);

        idUser = getIntent().getIntExtra("idUser",0);
        textNama = findViewById(R.id.textNamaProfil);
        textEmail = findViewById(R.id.textEmailProfil);

        preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        token = preferences.getString("TOKEN","");

        baseUrl = getString(R.string.apiUrlLumen);
        Authent authent = new Authent();
        portalClient = authent.setPortalClient(baseUrl);

        Call<DUser> call = portalClient.getDUser(token,token);
        call.enqueue(new Callback<DUser>() {
            @Override
            public void onResponse(Call<DUser> call, Response<DUser> response) {
                DUser dUser = response.body();
                if(dUser!=null){
                    List<DetailUserItem> detailUserItems = dUser.getDetailUser();
                    for (DetailUserItem item : detailUserItems){
                        textNama.setText(item.getNama());
                        textEmail.setText(item.getEmail());
                        Picasso.get().load(baseUrl+item.getFoto()).into(imgAvatar);
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

    public void toDetailProfil(View view){
        Intent intent = new Intent(this, DetailProfilActivity.class);
        intent.putExtra("idUser",idUser);
        startActivity(intent);
        finish();
    }

    public void toHome(View view){
        Call<ResponseRegister> call = portalClient.logout(token);
        call.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                ResponseRegister responseRegister = response.body();
                if(responseRegister!=null){
                    String message =responseRegister.getMessage();
                    if(message!=null){
                        preferences.edit().clear().commit();
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), WalkhthroughActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Tidak ada response",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void toNotifikasi(View view){
        Intent intent = new Intent(this, NotifikasiActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
        finish();
    }
}