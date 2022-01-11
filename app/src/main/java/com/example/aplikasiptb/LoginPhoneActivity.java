package com.example.aplikasiptb;

import androidx.annotation.NonNull;
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

import com.example.aplikasiptb.model.ResponseRegister;
import com.example.aplikasiptb.retrofit.PortalClient;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPhoneActivity extends AppCompatActivity {

    ImageView iconBack;
    EditText noHp;
    String baseUrl;
    PortalClient portalClient;
    TextView warNohp;
    Integer idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        noHp = findViewById(R.id.nomorhp);
        warNohp = findViewById(R.id.warningNohp);

        iconBack = findViewById(R.id.backImg);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        baseUrl = getString(R.string.apiUrlLumen);

        Authent authent = new Authent();
        portalClient = authent.setPortalClient(baseUrl);
    }

    public void toOtp(View view){
        String phoneNum = noHp.getText().toString();
        if (phoneNum.equals("")){
            warNohp.setText("Nomor Handphone harus di isi");
            warNohp.setVisibility(View.VISIBLE);
        }else{
            warNohp.setVisibility(View.GONE);
            Call<ResponseRegister> call = portalClient.checkPhone(phoneNum);
            updateViewProgress(true);
            call.enqueue(new Callback<ResponseRegister>() {
                @Override
                public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                    ResponseRegister responseRegister = response.body();
                    if(responseRegister!=null){
                        idUser = responseRegister.getId();
                        if(idUser==0){
                            warNohp.setText("Nomor Handphone ini tidak teraftar");
                            warNohp.setVisibility(View.VISIBLE);
                        }else{
                            warNohp.setVisibility(View.GONE);
                            toMap();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Ada yang salah", Toast.LENGTH_SHORT).show();
                    }
                    updateViewProgress(false);
                }

                @Override
                public void onFailure(Call<ResponseRegister> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Periksa Jaringan Internet Anda", Toast.LENGTH_SHORT).show();
                    updateViewProgress(false);
                }
            });
        }

    }

    public void toMap(){
        String phoneNum = "+62"+noHp.getText().toString();

        // Whenever verification is triggered with the whitelisted number,
        // provided it is not set for auto-retrieval, onCodeSent will be triggered.
        FirebaseAuth auth = FirebaseAuth.getInstance();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNum)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                Intent intent = new Intent(getApplicationContext(), OtpActivity.class);
                                intent.putExtra("verificationId",verificationId);
                                intent.putExtra("idUser",idUser);
                                startActivity(intent);
                                finish();
                        // Save the verification id somewhere
                        // ...

                        // The corresponding whitelisted code above should be used to complete sign-in.
//                        LoginPhoneActivity.this.enableUserManuallyInputCode();
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(getApplicationContext(), "Verifikasi compleate masuk sini", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getApplicationContext(), "Verifikasi Failed", Toast.LENGTH_SHORT).show();
                    }

                })
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    public void toLoginEmail(View view){
        Intent intent = new Intent(this, LoginEmailActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, WalkhthroughActivity.class);
        startActivity(intent);
        finish();
    }

    public void updateViewProgress(Boolean active){
        Button buttonLogin = findViewById(R.id.btnLogin);
        ProgressBar progressBar = findViewById(R.id.progressLogin);
        TextView textLogin = findViewById(R.id.textLoginUserEmail);
        if (active){
            noHp.setEnabled(false);
            buttonLogin.setVisibility(View.GONE);
            textLogin.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            noHp.setEnabled(true);
            buttonLogin.setVisibility(View.VISIBLE);
            textLogin.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}