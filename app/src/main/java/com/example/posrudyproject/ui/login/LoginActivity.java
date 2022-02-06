package com.example.posrudyproject.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.AuthEndpoint;
import com.example.posrudyproject.ui.lupaSandi.LupaSandiActivity;
import com.example.posrudyproject.ui.main.MainActivity;
import com.example.posrudyproject.ui.pelanggan.activity.PelangganActivity;
import com.example.posrudyproject.ui.pelanggan.activity.TambahPelangganActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText etEmail, etPassword;
    MaterialButton btnLogin, btnLupaPassword;
    TextInputLayout etLayout;
    AuthEndpoint authEndpoint;
    String USER = "admin";
    String PASSWORD = "admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authEndpoint = ApiClient.getClient().create(AuthEndpoint.class);
        etLayout = findViewById(R.id.layout_et_nama_pengguna);
        btnLogin = findViewById(R.id.btn_masuk);
        btnLupaPassword = findViewById(R.id.btn_lupa_sandi);

        //SET LISTENER
        btnLogin.setOnClickListener(this);
        btnLupaPassword.setOnClickListener(this);


    }

    public void validation(){
        if(Objects.requireNonNull(etEmail.getText()).toString().equals(USER) &&
                Objects.requireNonNull(etPassword.getText()).toString().equals(PASSWORD)){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        etEmail = findViewById(R.id.et_nama_pengguna_login);
        etPassword = findViewById(R.id.et_kata_sandi_login);
        etLayout = findViewById(R.id.layout_et_nama_pengguna);
        btnLogin = findViewById(R.id.btn_masuk);
        btnLupaPassword = findViewById(R.id.btn_lupa_sandi);
        switch (view.getId()){
            case R.id.btn_masuk:
//                validation();
                User user = new User(
                        etEmail.getEditableText().toString(),
                        etPassword.getEditableText().toString());
                SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();
                Call<ResponseItem> call = authEndpoint.login(user);
                call.enqueue(new Callback<ResponseItem>() {
                    @Override
                    public void onResponse(Call<ResponseItem> call, Response<ResponseItem> response) {
                        if (response.isSuccessful()){
                            SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", response.body().getToken());
                            editor.putInt("id_office", response.body().getId_office());
                            editor.putInt("id_store", response.body().getId_store());
                            editor.putString("lokasi_office", response.body().getLokasi_office());
                            editor.putString("lokasi_store", response.body().getLokasi_store());
                            editor.apply();
                            Intent login = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(login);
                            pDialog.hide();
                        } else {
                            pDialog.hide();
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Username or Password is wrong!")
                                    .setContentText(response.message())
                                    .show();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseItem> call, Throwable t) {
                        pDialog.hide();
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

                break;

            case R.id.btn_lupa_sandi:
                Intent lupaSandi = new Intent(this, LupaSandiActivity.class);
                startActivity(lupaSandi);
                break;
        }
    }
}