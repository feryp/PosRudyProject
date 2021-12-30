package com.example.posrudyproject.ui.akun.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.posrudyproject.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class UbahAkunActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    TextInputEditText etNamaPengguna, etNoHp, etEmail, etAlamatToko;
    MaterialButton btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_akun);

        //INIT VIEW
        initComponent();

        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());

    }

    private void initComponent() {
        //init
        mToolbar = findViewById(R.id.toolbar_ubah_akun);
        etNamaPengguna = findViewById(R.id.et_nama_pengguna_ubah);
        etNoHp = findViewById(R.id.et_no_hp_ubah);
        etEmail = findViewById(R.id.et_email_ubah);
        etAlamatToko = findViewById(R.id.et_alamat_toko_ubah);
        btnSimpan = findViewById(R.id.btn_simpan_ubah_akun);
    }
}