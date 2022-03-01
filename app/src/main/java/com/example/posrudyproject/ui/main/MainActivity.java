package com.example.posrudyproject.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.akun.fragment.AkunFragment;
import com.example.posrudyproject.ui.barcode.ScannerActivity;
import com.example.posrudyproject.ui.beranda.fragment.BerandaFragment;
import com.example.posrudyproject.ui.keranjang.fragment.KeranjangFragment;
import com.example.posrudyproject.ui.rekapKas.fragment.RekapKasFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Fragment selectedFragment = null;
    FloatingActionButton btnBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INIT VIEW
        initComponent();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new BerandaFragment()).commit();


        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        //SET NAVIGATION ITEM LISTENER
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        //SET LISTENER
        btnBarcode.setOnClickListener(this);

    }

    private void initComponent() {
        // init
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.main_container);
        btnBarcode = findViewById(R.id.btn_barcode_nav);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
            String token = preferences.getString("token","");
            String auth_token = ("Bearer ").concat(token);
            Integer id_toko = preferences.getInt("id_store",0);
            String nama_toko = preferences.getString("lokasi_store","");
            Integer id_pengguna = preferences.getInt("id_pengguna",0);
            String nama_pengguna = preferences.getString("nama_pengguna","");
            switch (item.getItemId()){
                case R.id.nav_beranda:
                    selectedFragment = new BerandaFragment();
                    break;
                case R.id.nav_rekap_kas:
                    RekapKasFragment rekapKasFragment = new RekapKasFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("auth_token",auth_token);
                    bundle.putInt("id_toko", id_toko);
                    bundle.putString("nama_toko", nama_toko);
                    bundle.putInt("id_pengguna", id_pengguna);
                    bundle.putString("nama_pengguna", nama_pengguna);
                    rekapKasFragment.setArguments(bundle);
                    selectedFragment = rekapKasFragment;
                    break;
                case R.id.nav_keranjang:
                    selectedFragment = new KeranjangFragment();
                    break;
                case R.id.nav_akun:
                    selectedFragment = new AkunFragment();
                    break;
            }
            if (selectedFragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, selectedFragment).commit();
            }

            return true;
        }
    };

    @Override
    public void onClick(View view) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)){
                startScan();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
            }
        } else {
            startScan();
        }
    }

    private void startScan(){
        Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
        startActivityForResult(intent, 20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20){
            if (resultCode == RESULT_OK && data != null){
                String code = data.getStringExtra("result");
                //SET CODE
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startScan();
            } else {
                Toast.makeText(this, "Gagal membuka kamera!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}