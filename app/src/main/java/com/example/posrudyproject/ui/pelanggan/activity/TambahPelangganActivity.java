package com.example.posrudyproject.ui.pelanggan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.posrudyproject.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class TambahPelangganActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    TextInputEditText etNamaPelanggan, etNoHpPelanggan, etEmailPelanggan, etAlamatPelanggan;
    MaterialButton btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pelanggan);

        //INIT VIEW
        initComponent();

        initToolbar();
    }

    private void initComponent() {
        //init
        mToolbar = findViewById(R.id.toolbar_tambah_pelanggan);
        etNamaPelanggan = findViewById(R.id.et_nama_pelanggan);
        etNoHpPelanggan = findViewById(R.id.et_no_hp_pelanggan);
        etEmailPelanggan = findViewById(R.id.et_email_pelanggan);
        etAlamatPelanggan = findViewById(R.id.et_alamat_pelanggan);
        btnSimpan = findViewById(R.id.btn_simpan_pelanggan);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }
}