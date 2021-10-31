package com.example.posrudyproject.ui.keranjang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.adapter.KeranjangAdapter;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.pesananTunggu.activity.PesananTungguActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class KeranjangActivity extends AppCompatActivity implements View.OnClickListener {

    SearchView searchView;
    Dialog dialog;
    AppCompatImageButton btnBarcode;
    MaterialToolbar mToolbar;
    MaterialButton btnPotonganHarga, btnSimpanPesanan, btnCustom, btnKonfirmasi;
    RecyclerView rvKeranjang;
    KeranjangAdapter adapter;
    List<KeranjangItem> keranjangItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        //INIT VIEW
        initComponent();

        initToolbar();

        //SET LISTENER
        btnPotonganHarga.setOnClickListener(this);
        btnSimpanPesanan.setOnClickListener(this);
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
        searchView = findViewById(R.id.search_barang);
        btnBarcode = findViewById(R.id.btn_barcode);
        btnPotonganHarga = findViewById(R.id.btn_potongan_harga);
        btnSimpanPesanan = findViewById(R.id.btn_simpan_pesanan);
        btnCustom = findViewById(R.id.btn_custom_item_keranjang);
        btnKonfirmasi = findViewById(R.id.btn_konfirmasi_keranjang);
        rvKeranjang = findViewById(R.id.rv_keranjang);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_simpan_pesanan:
                openDialog();
                break;
            case R.id.btn_potongan_harga:
                //Function Button
                break;
            case R.id.btn_custom_item_keranjang:
                //Function Button
                break;
            case R.id.btn_konfirmasi_keranjang:
                //Function Button
                break;
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void openDialog() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(KeranjangActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_simpan_pesanan,null);

        //init view
        final TextInputEditText etKetPesanan = mView.findViewById(R.id.et_ket_simpan_pesanan);
        MaterialButton btnSimpan = mView.findViewById(R.id.btn_simpan);
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
}