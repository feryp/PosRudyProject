package com.example.posrudyproject.ui.keranjang.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.barcode.ScannerActivity;
import com.example.posrudyproject.ui.diskon.fragment.BotSheetDiskonFragment;
import com.example.posrudyproject.ui.keranjang.adapter.KeranjangAdapter;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.pelanggan.activity.PelangganActivity;
import com.example.posrudyproject.ui.pembayaran.activity.PembayaranActivity;
import com.example.posrudyproject.ui.penjual.activity.PenjualActivity;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
import com.example.posrudyproject.ui.pesananTunggu.activity.PesananTungguActivity;
import com.example.posrudyproject.ui.produk.activity.CustomBarangActivity;
import com.example.posrudyproject.ui.ubahHarga.activity.UbahHargaActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class KeranjangActivity extends AppCompatActivity implements View.OnClickListener {

    SearchView searchView;
    AppCompatImageButton btnBarcode;
    MaterialToolbar mToolbar;
    MaterialButton btnPotonganHarga, btnSimpanPesanan, btnAddPelanggan, btnAddPenjual, btnAddDiskon, btnAddOngkir, btnCustom, btnKonfirmasi;
    RecyclerView rvKeranjang;
    KeranjangAdapter adapter;
    List<KeranjangItem> keranjangItems;
    ConstraintLayout layoutKeranjang, layoutEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        //INIT VIEW
        initComponent();

        initToolbar();

        //SET LISTENER
        btnBarcode.setOnClickListener(this);
        btnPotonganHarga.setOnClickListener(this);
        btnSimpanPesanan.setOnClickListener(this);
        btnAddPelanggan.setOnClickListener(this);
        btnAddPenjual.setOnClickListener(this);
        btnAddDiskon.setOnClickListener(this);
        btnAddOngkir.setOnClickListener(this);
        btnCustom.setOnClickListener(this);
        btnKonfirmasi.setOnClickListener(this);

        //SETTING BUTTON
        btnPotonganHarga.setPaintFlags(btnPotonganHarga.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnSimpanPesanan.setPaintFlags(btnSimpanPesanan.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        //Keranjang List
        keranjangItems = new ArrayList<>();
        for (int i=0; i<5; i++){
            keranjangItems.add(new KeranjangItem(
                    R.drawable.im_example,
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "Rp 1.000.000",
                    "2",
                    "Rp 2.000.000",
                    "2"
            ));
        }

        //Setup Adapter
        adapter = new KeranjangAdapter(keranjangItems, this);
        rvKeranjang.setLayoutManager(new LinearLayoutManager(this));
        rvKeranjang.setAdapter(adapter);
        rvKeranjang.setHasFixedSize(true);

        //Jika ada list item ilustrasi hilang
        if (adapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
            layoutKeranjang.setVisibility(View.VISIBLE);
        }

    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_notes_order){
                Intent pesananTunggu = new Intent(this, PesananTungguActivity.class);
                startActivity(pesananTunggu);
                return true;
            }

            return false;
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_keranjang);
        searchView = findViewById(R.id.search_barang_keranjang);
        btnBarcode = findViewById(R.id.btn_barcode_keranjang);
        btnPotonganHarga = findViewById(R.id.btn_potongan_harga);
        btnSimpanPesanan = findViewById(R.id.btn_simpan_pesanan);
        btnAddPelanggan = findViewById(R.id.btn_add_pelanggan);
        btnAddPenjual = findViewById(R.id.btn_add_penjual);
        btnAddDiskon = findViewById(R.id.btn_add_diskon);
        btnAddOngkir = findViewById(R.id.btn_add_ongkir);
        btnCustom = findViewById(R.id.btn_custom_item_keranjang);
        btnKonfirmasi = findViewById(R.id.btn_konfirmasi_keranjang);
        rvKeranjang = findViewById(R.id.rv_keranjang);
        layoutKeranjang = findViewById(R.id.layout_keranjang);
        layoutEmpty = findViewById(R.id.layout_ilustrasi_empty_keranjang);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_simpan_pesanan:
                dialogSimpanPesanan();
                break;
            case R.id.btn_potongan_harga:
                Intent potonganHarga = new Intent(this, UbahHargaActivity.class);
                startActivity(potonganHarga);
                break;
            case R.id.btn_add_pelanggan:
                Intent tambahPelanggan = new Intent(this, PelangganActivity.class);
                startActivity(tambahPelanggan);
                break;
            case R.id.btn_add_penjual:
                Intent tambahPenjual = new Intent(this, PenjualActivity.class);
                startActivity(tambahPenjual);
                break;
            case R.id.btn_add_diskon:
                BotSheetDiskonFragment botSheetDiskon = new BotSheetDiskonFragment();
                botSheetDiskon.setCancelable(false);
                botSheetDiskon.show(getSupportFragmentManager(), botSheetDiskon.getTag());
                break;
            case R.id.btn_add_ongkir:
                dialogOngkir();
                break;
            case R.id.btn_custom_item_keranjang:
                Intent custom = new Intent(this, CustomBarangActivity.class);
                startActivity(custom);
                break;
            case R.id.btn_konfirmasi_keranjang:
                Intent konfirmasi = new Intent(this, PembayaranActivity.class);
                startActivity(konfirmasi);
                break;
            case R.id.btn_barcode_keranjang:
                if (ContextCompat.checkSelfPermission(KeranjangActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.shouldShowRequestPermissionRationale(KeranjangActivity.this, Manifest.permission.CAMERA)){
                        startScan();
                    } else {
                        ActivityCompat.requestPermissions(KeranjangActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                    }
                } else {
                    startScan();
                }
                break;
        }
    }

    private void startScan() {
        Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
        startActivityForResult(intent, 20);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void dialogOngkir() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(KeranjangActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_ongkir,null);

        //init view
        final TextInputEditText etNamaEkspedisi = mView.findViewById(R.id.et_nama_ekspedisi);
        final TextInputEditText etOngkosKirim = mView.findViewById(R.id.et_ongkos_kirim);
        MaterialButton btnSimpan = mView.findViewById(R.id.btn_simpan_ongkir);
        MaterialButton btnCancel = mView.findViewById(R.id.btn_cancel_dialog);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_white));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(KeranjangActivity.this, "Simpan", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void dialogSimpanPesanan() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(KeranjangActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_simpan_pesanan,null);

        //init view
        final TextInputEditText etKetPesanan = mView.findViewById(R.id.et_ket_simpan_pesanan);
        MaterialButton btnSimpan = mView.findViewById(R.id.btn_simpan_keterangan_pesanan);
        MaterialButton btnCancel = mView.findViewById(R.id.btn_cancel_dialog);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_white));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(KeranjangActivity.this, "Simpan", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
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