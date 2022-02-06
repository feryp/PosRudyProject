package com.example.posrudyproject.ui.pelanggan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PelangganEndpoint;
import com.example.posrudyproject.ui.pelanggan.model.Pelanggan;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPelangganActivity extends AppCompatActivity {

    PelangganEndpoint pelangganEndpoint;

    MaterialToolbar mToolbar;
    TextInputEditText etNamaPelanggan, etNoHpPelanggan, etEmailPelanggan, etAlamatPelanggan;
    MaterialButton btnSimpan;
    Long id;
    Double totalKunjungan,kuantitas,poin,totalPembelian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pelanggan);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        String auth_token = ("Bearer ").concat(token);
        pelangganEndpoint = ApiClient.getClient().create(PelangganEndpoint.class);

        mToolbar = findViewById(R.id.toolbar_tambah_pelanggan);
        etNamaPelanggan = findViewById(R.id.et_nama_pelanggan);
        etNoHpPelanggan = findViewById(R.id.et_no_hp_pelanggan);
        etEmailPelanggan = findViewById(R.id.et_email_pelanggan);
        etAlamatPelanggan = findViewById(R.id.et_alamat_pelanggan);
        btnSimpan = findViewById(R.id.btn_simpan_pelanggan);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong("id");
            etNamaPelanggan.setText(extras.getString("namaPelanggan"));
            etNoHpPelanggan.setText(extras.getString("noHp"));
            etEmailPelanggan.setText(extras.getString("email"));
            etAlamatPelanggan.setText(extras.getString("alamat"));
            totalKunjungan = extras.getDouble("totalKunjungan");
            kuantitas = extras.getDouble("kuantitas");
            poin = extras.getDouble("poin");
            totalPembelian = extras.getDouble("totalPembelian");
        }
        btnSimpan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean validation = true;
                if (validation){
                    Pelanggan pelanggan = new Pelanggan(
                            id == null? null: id,
                            etNamaPelanggan.getEditableText().toString(),
                            etNoHpPelanggan.getEditableText().toString(),
                            etEmailPelanggan.getEditableText().toString(),
                            etAlamatPelanggan.getEditableText().toString(),
                            totalKunjungan == null? 1: totalKunjungan,
                            kuantitas == null? 0: kuantitas,
                            poin == null? 0: poin,
                            totalPembelian == null? 0: totalPembelian);
                    SweetAlertDialog pDialog = new SweetAlertDialog(TambahPelangganActivity.this, SweetAlertDialog.WARNING_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading ...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    if (id == null) {
                        Call<Map> call = pelangganEndpoint.savePelanggan(
                                auth_token,
                                pelanggan);
                        call.enqueue(new Callback<Map>(){
                            @Override
                            public void onResponse(Call<Map> call, Response<Map> response){
                                pDialog.dismiss();
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(TambahPelangganActivity.this, SweetAlertDialog.WARNING_TYPE);
                                sweetAlertDialog.setTitleText(response.body().get("message").toString());
                                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        Intent intent = new Intent(TambahPelangganActivity.this, PelangganActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                sweetAlertDialog.show();
                            }

                            @Override
                            public void onFailure(Call<Map> call, Throwable t) {
                                pDialog.dismiss();
                                new SweetAlertDialog(TambahPelangganActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText(t.getMessage())
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                Intent intent = new Intent(TambahPelangganActivity.this, PelangganActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            }
                        });
                    } else {
                        Call<Map> call = pelangganEndpoint.update(
                                auth_token,
                                pelanggan);
                        call.enqueue(new Callback<Map>(){
                            @Override
                            public void onResponse(Call<Map> call, Response<Map> response){
                                pDialog.dismiss();
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(TambahPelangganActivity.this, SweetAlertDialog.WARNING_TYPE);
                                sweetAlertDialog.setTitleText(response.body().get("message").toString());
                                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        Intent intent = new Intent(TambahPelangganActivity.this, PelangganActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                sweetAlertDialog.show();
                            }

                            @Override
                            public void onFailure(Call<Map> call, Throwable t) {
                                pDialog.dismiss();
                                new SweetAlertDialog(TambahPelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText(t.getMessage())
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                Intent intent = new Intent(TambahPelangganActivity.this, PelangganActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            }
                        });
                    }

                }
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahPelangganActivity.this, PelangganActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean checkValidation() {

        boolean validation = true;

        if(etNamaPelanggan.toString().trim().length()<1){
            etNamaPelanggan.setError("Pls, Enter Fullname");
            validation = false;
        }
        if(etAlamatPelanggan.toString().trim().length()<1){
            etAlamatPelanggan.setError("Pls, Enter Address");
            validation = false;
        }
        if(etNoHpPelanggan.toString().trim().length()<1){
            etNoHpPelanggan.setError("Pls, Enter Mobile Number");
            validation = false;
        }

        if(etEmailPelanggan.toString().trim().length()<1){
            etEmailPelanggan.setError("Pls, Enter Email");
            validation = false;
        }

        return validation;
    }
}