package com.example.posrudyproject.ui.produk.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.barcode.ScannerActivity;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterProdukFragment;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterTipeFragment;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
import com.example.posrudyproject.ui.penyimpanan.adapter.ProdukTersediaAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.example.posrudyproject.ui.produk.adapter.ProdukAdapter;
import com.example.posrudyproject.ui.produk.model.ProdukItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar mToolbar;
    SearchView searchView;
    AppCompatImageButton btnBarcode;
    RecyclerView rvProduk;
    MaterialButton btnCustomBarang;
    PenyimpananEndpoint penyimpananEndpoint;
    List<ProdukItem> produkItems;
    ProdukAdapter adapter;
    String auth_token;
    int id_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        id_store = preferences.getInt("id_store", 0);
        auth_token = ("Bearer ").concat(token);

        //INIT VIEW
        initComponent();

        initToolbar();
        SetupSearchView();
        //Produk Tersedia
        Call<List<ProdukTersediaItem>> call_stock = penyimpananEndpoint.stockAvailPerStore(auth_token,id_store);
        SweetAlertDialog pDialog = new SweetAlertDialog(ProdukActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call_stock.enqueue(new Callback<List<ProdukTersediaItem>>() {
            @Override
            public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(ProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                }else{
                    pDialog.dismiss();
                    produkItems = new ArrayList<>();
                    for (int i=0; i<response.body().size(); i++){
                        produkItems.add(new ProdukItem(
                                response.body().get(i).getFoto_barang(),
                                response.body().get(i).getTipeBarang(),
                                response.body().get(i).getSkuCode(),
                                response.body().get(i).getArtikelBarang(),
                                response.body().get(i).getNamaBarang(),
                                response.body().get(i).getJumlahStok()
                        ));
                    }
                    //Setup Adapter Produk Tersedia
                    ProdukAdapter adapter = new ProdukAdapter(produkItems,ProdukActivity.this);
                    rvProduk.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(ProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        //SET LISTENER BUTTON
        btnCustomBarang.setOnClickListener(this);
        btnBarcode.setOnClickListener(this);
        rvProduk.setLayoutManager(new LinearLayoutManager(this));
        rvProduk.setHasFixedSize(true);
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_produk);
        btnBarcode = findViewById(R.id.btn_barcode_produk);
        btnCustomBarang = findViewById(R.id.btn_custom_barang);
        rvProduk = findViewById(R.id.rv_produk);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_filter){
                BotSheetFilterProdukFragment botSheetProduk = new BotSheetFilterProdukFragment();
                botSheetProduk.setCancelable(false);
                botSheetProduk.show(getSupportFragmentManager(), botSheetProduk.getTag());
                return true;
            }
            return false;
        });
    }

    private void SetupSearchView(){
        final SearchView searchView = findViewById(R.id.search_barang_produk);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchStockStore(auth_token,id_store,query);
                call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                    @Override
                    public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                        produkItems = new ArrayList<>();
                        for (int i=0; i<response.body().size(); i++){
                            produkItems.add(new ProdukItem(
                                    response.body().get(i).getFoto_barang(),
                                    response.body().get(i).getTipeBarang(),
                                    response.body().get(i).getSkuCode(),
                                    response.body().get(i).getArtikelBarang(),
                                    response.body().get(i).getNamaBarang(),
                                    response.body().get(i).getJumlahStok()
                            ));
                        }
                        //Setup Adapter Produk Tersedia
                        ProdukAdapter adapter = new ProdukAdapter(produkItems,ProdukActivity.this);
                        rvProduk.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                        new SweetAlertDialog(ProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchStockStore(auth_token,id_store,"");
                    call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                        @Override
                        public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                            produkItems = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                produkItems.add(new ProdukItem(
                                        response.body().get(i).getFoto_barang(),
                                        response.body().get(i).getTipeBarang(),
                                        response.body().get(i).getSkuCode(),
                                        response.body().get(i).getArtikelBarang(),
                                        response.body().get(i).getNamaBarang(),
                                        response.body().get(i).getJumlahStok()
                                ));
                            }
                            //Setup Adapter Produk Tersedia
                            ProdukAdapter adapter = new ProdukAdapter(produkItems,ProdukActivity.this);
                            rvProduk.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                            new SweetAlertDialog(ProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                } else {
                    Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchStockStore(auth_token,id_store,newText);
                    call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                        @Override
                        public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                            produkItems = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                produkItems.add(new ProdukItem(
                                        response.body().get(i).getFoto_barang(),
                                        response.body().get(i).getTipeBarang(),
                                        response.body().get(i).getSkuCode(),
                                        response.body().get(i).getArtikelBarang(),
                                        response.body().get(i).getNamaBarang(),
                                        response.body().get(i).getJumlahStok()
                                ));
                            }
                            //Setup Adapter Produk Tersedia
                            ProdukAdapter adapter = new ProdukAdapter(produkItems,ProdukActivity.this);
                            rvProduk.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                            new SweetAlertDialog(ProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                }
                return false;
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view == btnCustomBarang){
           Intent customBarang = new Intent(this, CustomBarangActivity.class);
           startActivity(customBarang);
        } else if (view == btnBarcode){
            if (ContextCompat.checkSelfPermission(ProdukActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(ProdukActivity.this, Manifest.permission.CAMERA)){
                    startScan();
                } else {
                    ActivityCompat.requestPermissions(ProdukActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                }
            } else {
                startScan();
            }
        }
    }

    private void startScan() {
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