package com.example.aplikasiptb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.model.Auth;
import com.example.aplikasiptb.model.AuthData;
import com.example.aplikasiptb.model.DUser;
import com.example.aplikasiptb.model.DetailUserItem;
import com.example.aplikasiptb.retrofit.PortalClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {

    OtpEditText otpEditText;
    String codeOtp,verificationId;
    Button masuk;
    Integer idUser;
    PortalClient portalClient;
    String token,fcm_token;
    ImageView iconBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        verificationId = getIntent().getStringExtra("verificationId");
        idUser = getIntent().getIntExtra("idUser",0);

        otpEditText = findViewById(R.id.et_otp);
        masuk = findViewById(R.id.btnMasuk);

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    fcm_token = task.getResult();
                }
            }
        });

        Authent authent = new Authent();
        portalClient = authent.setPortalClient(getString(R.string.apiUrlLumen));

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeOtp = otpEditText.getText().toString();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, codeOtp);
                FirebaseAuth mauth = FirebaseAuth.getInstance();
                mauth.signInWithCredential(credential).
                        addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    login();
                                }else{
                                    Log.w("Coba", "signInWithCredential:failure", task.getException());
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(getApplicationContext(), "Kode yang anda masukkan salah", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
//                Toast.makeText(getApplicationContext(), codeOtp, Toast.LENGTH_SHORT).show();
            }
        });

        iconBack = findViewById(R.id.backImgOtp);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void login(){
        Call<Auth> call = portalClient.loginHp(idUser,fcm_token);
        updateViewProgress(true);
        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                Auth auth = response.body();
                if(auth!=null){
                    AuthData authData = auth.getData();
                    token = authData.getToken();

                    SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("TOKEN",token);
                    editor.putInt("IDUSER",idUser);
                    editor.apply();

                    checkDetail();
                }else{
                    Toast.makeText(getApplicationContext(), "Ada yang salah", Toast.LENGTH_SHORT).show();
                }
                updateViewProgress(false);
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Cek koneki internet anda", Toast.LENGTH_SHORT).show();
                updateViewProgress(false);
            }
        });
    }

    public void checkDetail(){
        Call<DUser> call = portalClient.getDUser(token,token);
        updateViewProgress(true);
        call.enqueue(new Callback<DUser>() {
            @Override
            public void onResponse(Call<DUser> call, Response<DUser> response) {
                DUser dUser = response.body();
                if (dUser!=null){
//                    Toast.makeText(getApplicationContext(),"duser ngak kosong",Toast.LENGTH_SHORT).show();
                    List<DetailUserItem> detailUserItems = dUser.getDetailUser();
                    int idUser = dUser.getIdUser();

                    if(detailUserItems.isEmpty()){
//                        Toast.makeText(getApplicationContext(),"duser kosong",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),RegisterAvatarActivity.class);
                        intent.putExtra("userIdLaunch",idUser);
                        finish();
                        startActivity(intent);
                    }else{
                        for (DetailUserItem item : detailUserItems){
                            if(item.getFoto()==null){
                                Intent intent = new Intent(getApplicationContext(),RegisterAvatarActivity.class);
                                intent.putExtra("idUserLaunch",idUser);
                                finish();
                                startActivity(intent);
                            }else if(item.getNama()==null){
                                Intent intent = new Intent(getApplicationContext(),Register1Activity.class);
                                intent.putExtra("idUser",idUser);
                                finish();
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                                intent.putExtra("idUser",idUser);
                                intent.putExtra("srcActivity","LoginEmailActivity");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                                startActivity(intent);
                            }
                        }
                    }
                }else{
                    Intent intent = new Intent(getApplicationContext(),WalkhthroughActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
                updateViewProgress(false);
            }

            @Override
            public void onFailure(Call<DUser> call, Throwable t) {
                updateViewProgress(false);
                Intent intent = new Intent(getApplicationContext(),WalkhthroughActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        });
    }

    public void updateViewProgress(Boolean active){
        ProgressBar progressBar = findViewById(R.id.progressOtp);
        if (active){
            otpEditText.setEnabled(false);
            masuk.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            otpEditText.setEnabled(true);
            masuk.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginPhoneActivity.class);
        startActivity(intent);
        finish();
    }
}