package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.model.DUser;
import com.example.aplikasiptb.model.DetailUserItem;
import com.example.aplikasiptb.retrofit.PortalClient;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProfilActivity extends AppCompatActivity {

    ImageView iconBack,imgAvatar;
    TextView textNama,textUsername,textEmail,textPassword,textJk,textHp,textLahir;
    Integer userId;
    String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profil);

        imgAvatar = findViewById(R.id.imgAvatarDProfil);
        textNama = findViewById(R.id.textNamaDProfil);
        textUsername = findViewById(R.id.textUserDProfil);
        textEmail = findViewById(R.id.textEmailDProfil);
        textPassword = findViewById(R.id.textPasswordProfil);
        textJk = findViewById(R.id.textJk);
        textHp = findViewById(R.id.textHpProfil);
        textLahir = findViewById(R.id.textLahir);

        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");

        baseUrl = getString(R.string.apiUrlLumen);
        Authent authent = new Authent();
        PortalClient portalClient = authent.setPortalClient(baseUrl);

        Call<DUser> call = portalClient.getDUser(token,token);
        updateViewProgress(true);
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

                        Picasso.get().load(baseUrl+item.getFoto()).into(imgAvatar);
                        textNama.setText(item.getNama());
                        textUsername.setText(item.getUsername());
                        textEmail.setText(item.getEmail());
                        textJk.setText(jk);
                        textHp.setText(item.getNoHp());

                        String tlhr = item.getTglLahir();
                        SimpleDateFormat formatawal = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat formatakhir = new SimpleDateFormat("dd MMMM yyyy");
                        Date dtlhr = null;
                        try {
                            dtlhr = formatawal.parse(tlhr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        textLahir.setText(item.getTempatLahir()+", "+formatakhir.format(dtlhr));
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Tidak ada response", Toast.LENGTH_SHORT).show();
                }
                updateViewProgress(false);
            }

            @Override
            public void onFailure(Call<DUser> call, Throwable t) {
                updateViewProgress(false);
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

    public void updateViewProgress(Boolean active){
        FrameLayout progress = findViewById(R.id.layputProgressDProfil);
        if (active){
            progress.setVisibility(View.VISIBLE);
        }else{
            progress.setVisibility(View.GONE);
        }
    }
}