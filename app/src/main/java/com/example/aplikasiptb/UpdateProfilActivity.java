package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.model.DUser;
import com.example.aplikasiptb.model.DetailUserItem;
import com.example.aplikasiptb.model.ResponseRegister;
import com.example.aplikasiptb.retrofit.PortalClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfilActivity extends AppCompatActivity {

    ImageView iconBack;
    private ImageView imageView;
    Button uploadBtn;
    EditText tglLahir,fullname,email,noHp,username,tempatLahir;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Integer userId;
    String token;
    PortalClient portalClient;
    TextView warFullname,warUsername,warEmail,warHp,warTempatLhr,warTglLhr;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil);

        userId = getIntent().getIntExtra("userId",0);

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        noHp = findViewById(R.id.no_hp);
        username = findViewById(R.id.username);
        tempatLahir = findViewById(R.id.tempatLahir);

        radioGroup = findViewById(R.id.radio_group_jk);

        warFullname = findViewById(R.id.warFullname);
        warUsername = findViewById(R.id.warUsername);
        warEmail = findViewById(R.id.warEmail);
        warHp = findViewById(R.id.warHp);
        warTempatLhr = findViewById(R.id.warTempatLhr);
        warTglLhr = findViewById(R.id.warTglLhr);

        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        token = preferences.getString("TOKEN","");

        Authent authent = new Authent();
        portalClient = authent.setPortalClient(getString(R.string.apiUrlLumen));

        setData();
        iconBack = findViewById(R.id.backImg);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tglLahir = findViewById(R.id.tglLahir);
        disableEditText(tglLahir);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        tglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        imageView = (ImageView) findViewById(R.id.upload);
        uploadBtn = findViewById(R.id.uploadBtn);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(UpdateProfilActivity.this);
            }
        });
    }

    public void setData(){
        Call<DUser> call = portalClient.getDUser(token,token);
        call.enqueue(new Callback<DUser>() {
            @Override
            public void onResponse(Call<DUser> call, Response<DUser> response) {
                DUser dUser = response.body();
                if(dUser!=null){
                    List<DetailUserItem> detailUserItems = dUser.getDetailUser();
                    userId = dUser.getIdUser();
                    for (DetailUserItem item : detailUserItems){

                        if (item.getJk()==1){
                            radioGroup.check(R.id.radio1jk);
                        }else{
                            radioGroup.check(R.id.radio2jk);
                        }

                        fullname.setText(item.getNama());
                        username.setText(item.getUsername());
                        email.setText(item.getEmail());
                        noHp.setText(item.getNoHp());
                        tempatLahir.setText(item.getTempatLahir());
                        tglLahir.setText(item.getTglLahir());
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
    }

    private void disableEditText(EditText editText) {
//        editText.setFocusable(false);
//        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }

    public void toDetailProfil(View view){
        String namaUser = fullname.getText().toString();
        String hp = noHp.getText().toString();
        String tmptLahir = tempatLahir.getText().toString();
        String mail = email.getText().toString();
        String user = username.getText().toString();
        String tglLhr = tglLahir.getText().toString();

        Integer jk;
        Integer jenisKelamin;

        warFullname.setVisibility(View.GONE);
        warUsername.setVisibility(View.GONE);
        warEmail.setVisibility(View.GONE);
        warHp.setVisibility(View.GONE);
        warTempatLhr.setVisibility(View.GONE);
        warTglLhr.setVisibility(View.GONE);

        if (namaUser.equals("")){
            warFullname.setVisibility(View.VISIBLE);
            warFullname.setText("Nama Masih Kosong");
        }else if (user.equals("")){
            username.setVisibility(View.VISIBLE);
            username.setText("Username Masih Kosong");
        }else if (mail.equals("")){
            warEmail.setVisibility(View.VISIBLE);
            warEmail.setText("Email Masih Kosong");
        }else if (hp.equals("")){
            warHp.setVisibility(View.VISIBLE);
            warHp.setText("Nomor Handphone Masih Kosong");
        }else if (tmptLahir.equals("")){
            warTempatLhr.setVisibility(View.VISIBLE);
            warTempatLhr.setText("Tempat Lahir Masih Kosong");
        }else if (tglLhr.equals("")){
            warTglLhr.setVisibility(View.VISIBLE);
            warTglLhr.setText("Tanggal Lahir Masih Kosong");
        }else{
            jk = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(jk);

            if (radioButton.getText().equals("Laki-laki")){
                jenisKelamin = 1;
            }else{
                jenisKelamin = 2;
            }

            Call<ResponseRegister> call = portalClient.updateProfil(
                    token,
                    userId,
                    namaUser,
                    mail,
                    jenisKelamin,
                    hp,
                    tmptLahir,
                    tglLhr
            );
            call.enqueue(new Callback<ResponseRegister>() {
                @Override
                public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                    ResponseRegister responseRegister = response.body();
                    if(responseRegister!=null){
                        String message = responseRegister.getMessage();
                        List<String> respEmail = responseRegister.getEmail();
                        List<String> respHp = responseRegister.getNo_hp();
                        if (respEmail!=null&&!respEmail.isEmpty()){
                            warEmail.setVisibility(View.VISIBLE);
                            warEmail.setText("Email Sudah Terdaftar");
                        }else if (respHp!=null&&!respHp.isEmpty()){
                            warHp.setVisibility(View.VISIBLE);
                            warHp.setText("Nomor Handphone Sudah Terdaftar");
                        }else if (message!=null){
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), DetailProfilActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Tidak ada response", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseRegister> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tglLahir.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DetailProfilActivity.class);
        startActivity(intent);
        finish();
    }
}