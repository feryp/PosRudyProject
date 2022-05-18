package com.example.posrudyproject.ui.penjualan.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualanEndpoint;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterTipeFragment;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import android.widget.Toast;

import com.example.posrudyproject.ui.barcode.ScannerActivity;
import com.example.posrudyproject.ui.penjualan.adapter.PenjualanAdapter;
import com.example.posrudyproject.ui.penjualan.model.PenjualanItem;
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.example.posrudyproject.ui.produk.activity.ProdukActivity;
import com.example.posrudyproject.ui.produk.adapter.ProdukAdapter;
import com.example.posrudyproject.ui.produk.model.ProdukItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualanActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar mToolbar;
    AppCompatImageButton btnBarcode;
    AppCompatTextView totalQty;
    MaterialButton btnMasukKeranjang;
    RecyclerView rvPenjualan, rvKeranjang;
    List<PenjualanItem> penjualanItems;
    List<KeranjangItem> keranjangItems;
    PenjualanEndpoint penjualanEndpoint;
    PenyimpananEndpoint penyimpananEndpoint;
    String auth_token,id_kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        id_kategori = preferences.getString("id_kategori","");
        String token = preferences.getString("token","");
        int id_store = preferences.getInt("id_store", 0);
        auth_token = ("Bearer ").concat(token);

        penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);

        rvPenjualan = findViewById(R.id.rv_penjualan);
        rvKeranjang = findViewById(R.id.rv_keranjang);
        totalQty = findViewById(R.id.tv_total_qty);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        rvPenjualan.setLayoutManager(manager);
        rvPenjualan.setHasFixedSize(true);

        //INIT VIEW
        initComponent();

        initToolbar();
        //SET LISTENER
        btnBarcode.setOnClickListener(this);
        btnMasukKeranjang.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();

        SetupSearchView(auth_token, id_store, id_kategori);
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        if (extras != null) {
            Call<List<ProdukTersediaItem>> call_stock = penyimpananEndpoint.stockAvailPerStoreByCategory(auth_token,id_store,id_kategori);
            SweetAlertDialog pDialog = new SweetAlertDialog(PenjualanActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            call_stock.enqueue(new Callback<List<ProdukTersediaItem>>() {
                @Override
                public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                    if (!response.isSuccessful()){
                        pDialog.dismiss();
                        new SweetAlertDialog(PenjualanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(String.valueOf(response.code()))
                                .setContentText(response.message())
                                .show();
                    }else{
                        pDialog.dismiss();
                        penjualanItems = new ArrayList<>();
                        for (int i=0; i<response.body().size(); i++){
                            penjualanItems.add(new PenjualanItem(
                                    response.body().get(i).getFoto_barang(),
                                    response.body().get(i).getTipeBarang(),
                                    response.body().get(i).getSkuCode(),
                                    response.body().get(i).getArtikelBarang(),
                                    response.body().get(i).getNamaBarang(),
                                    response.body().get(i).getHargaBarang(),
                                    "0"
                            ));
                        }
                        PenjualanAdapter adapter = new PenjualanAdapter(penjualanItems, PenjualanActivity.this);
                        rvPenjualan.setAdapter(adapter);
                        rvPenjualan.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                final Integer itemCount = adapter.getItemCount();
                                keranjangItems = new ArrayList<>();
                                if (itemCount != null) {
                                    for (int i = 0; i<itemCount; i++) {
                                        TextView total = rvPenjualan.getChildAt(i).findViewById(R.id.tv_qty_item_penjualan);
                                        ImageView foto = rvPenjualan.getChildAt(i).findViewById(R.id.im_barang_penjualan);
                                        TextView tipeBarang = rvPenjualan.getChildAt(i).findViewById(R.id.tv_tipe_barang_penjualan);
                                        TextView skuCode = rvPenjualan.getChildAt(i).findViewById(R.id.tv_skuCode_barang_penjualan);
                                        TextView artikelBarang = rvPenjualan.getChildAt(i).findViewById(R.id.tv_artikel_barang_penjualan);
                                        TextView namaBarang = rvPenjualan.getChildAt(i).findViewById(R.id.tv_nama_barang_penjualan);
                                        TextView hargaBarang = rvPenjualan.getChildAt(i).findViewById(R.id.tv_harga_barang_penjualan);

                                        Button btnMinus = rvPenjualan.getChildAt(i).findViewById(R.id.btn_minus);
                                        Button btnPlus = rvPenjualan.getChildAt(i).findViewById(R.id.btn_plus);
                                        btnPlus.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                total.setText(String.valueOf(Integer.parseInt(String.valueOf(total.getText())) + 1));
                                                totalQty.setText(String.valueOf(Integer.parseInt(String.valueOf(totalQty.getText() == ""? 0:totalQty.getText())) + 1));
                                            }
                                        });
                                        btnMinus.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                if (Integer.parseInt(String.valueOf(total.getText())) != 0) {
                                                    total.setText(String.valueOf(Integer.parseInt(String.valueOf(total.getText())) - 1));
                                                    totalQty.setText(String.valueOf(Integer.parseInt(String.valueOf(totalQty.getText())) - 1));
                                                }
                                            }
                                        });
                                        keranjangItems.add(new KeranjangItem(
                                                penjualanItems.get(i).getFoto_barang(),
                                                tipeBarang.getText().toString(),
                                                skuCode.getText().toString(),
                                                artikelBarang.getText().toString(),
                                                namaBarang.getText().toString(),
                                                hargaBarang.getText().toString(),
                                                total.getText().toString(),
                                                String.valueOf(Double.valueOf(hargaBarang.getText().toString()) * Double.valueOf(total.getText().toString())),
                                                total.getText().toString()
                                        ));
                                    }
                                }

                            }
                        });
                        btnMasukKeranjang.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent masukKeranjang = new Intent(PenjualanActivity.this, KeranjangActivity.class);
                                masukKeranjang.putExtra("itemForBuy", (Serializable) keranjangItems);
                                masukKeranjang.putExtra("id_kategori",id_kategori);
                                startActivity(masukKeranjang);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                    pDialog.dismiss();
                    new SweetAlertDialog(PenjualanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(t.getMessage())
                            .show();
                }
            });
        } else {
            Call<List<ProdukTersediaItem>> call_all = penyimpananEndpoint.stockAvailPerStore(auth_token,id_store);
            SweetAlertDialog pDialog = new SweetAlertDialog(PenjualanActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            call_all.enqueue(new Callback<List<ProdukTersediaItem>>() {
                @Override
                public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                    if (!response.isSuccessful()){
                        pDialog.dismiss();
                        new SweetAlertDialog(PenjualanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(String.valueOf(response.code()))
                                .setContentText(response.message())
                                .show();
                    }else{
                        pDialog.dismiss();
                        penjualanItems = new ArrayList<>();
                        for (int i=0; i<response.body().size(); i++){
                            penjualanItems.add(new PenjualanItem(
                                    response.body().get(i).getFoto_barang(),
                                    response.body().get(i).getTipeBarang(),
                                    response.body().get(i).getSkuCode(),
                                    response.body().get(i).getArtikelBarang(),
                                    response.body().get(i).getNamaBarang(),
                                    response.body().get(i).getHargaBarang(),
                                    "0"
                            ));
                        }
                        PenjualanAdapter adapter = new PenjualanAdapter(penjualanItems, PenjualanActivity.this);
                        rvPenjualan.setAdapter(adapter);
                        rvPenjualan.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                final Integer itemCount = adapter.getItemCount();
                                keranjangItems = new ArrayList<>();
                                if (itemCount != null) {
                                    for (int i = 0; i<itemCount; i++) {
                                        TextView total = rvPenjualan.getChildAt(i).findViewById(R.id.tv_qty_item_penjualan);
                                        ImageView foto = rvPenjualan.getChildAt(i).findViewById(R.id.im_barang_penjualan);
                                        TextView tipeBarang = rvPenjualan.getChildAt(i).findViewById(R.id.tv_tipe_barang_penjualan);
                                        TextView skuCode = rvPenjualan.getChildAt(i).findViewById(R.id.tv_skuCode_barang_penjualan);
                                        TextView artikelBarang = rvPenjualan.getChildAt(i).findViewById(R.id.tv_artikel_barang_penjualan);
                                        TextView namaBarang = rvPenjualan.getChildAt(i).findViewById(R.id.tv_nama_barang_penjualan);
                                        TextView hargaBarang = rvPenjualan.getChildAt(i).findViewById(R.id.tv_harga_barang_penjualan);

                                        Button btnMinus = rvPenjualan.getChildAt(i).findViewById(R.id.btn_minus);
                                        Button btnPlus = rvPenjualan.getChildAt(i).findViewById(R.id.btn_plus);
                                        btnPlus.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                total.setText(String.valueOf(Integer.parseInt(String.valueOf(total.getText())) + 1));
                                                totalQty.setText(String.valueOf(Integer.parseInt(String.valueOf(totalQty.getText() == ""? 0:totalQty.getText())) + 1));
                                            }
                                        });
                                        btnMinus.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                if (Integer.parseInt(String.valueOf(total.getText())) != 0) {
                                                    total.setText(String.valueOf(Integer.parseInt(String.valueOf(total.getText())) - 1));
                                                    totalQty.setText(String.valueOf(Integer.parseInt(String.valueOf(totalQty.getText())) - 1));
                                                }
                                            }
                                        });
                                        keranjangItems.add(new KeranjangItem(
                                                penjualanItems.get(i).getFoto_barang(),
                                                tipeBarang.getText().toString(),
                                                skuCode.getText().toString(),
                                                artikelBarang.getText().toString(),
                                                namaBarang.getText().toString(),
                                                hargaBarang.getText().toString(),
                                                total.getText().toString(),
                                                String.valueOf(Double.valueOf(hargaBarang.getText().toString()) * Double.valueOf(total.getText().toString())),
                                                total.getText().toString()
                                        ));
                                    }
                                }

                            }
                        });
                        btnMasukKeranjang.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent masukKeranjang = new Intent(PenjualanActivity.this, KeranjangActivity.class);
                                masukKeranjang.putExtra("itemForBuy", (Serializable) keranjangItems);
                                masukKeranjang.putExtra("id_kategori",id_kategori);
                                startActivity(masukKeranjang);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                    pDialog.dismiss();
                    new SweetAlertDialog(PenjualanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(t.getMessage())
                            .show();
                }
            });
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PenjualanActivity.this, KategoriActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initToolbar() {
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_filter){
                BotSheetFilterTipeFragment botSheetTipe = new BotSheetFilterTipeFragment();
                botSheetTipe.setCancelable(false);
                botSheetTipe.show(getSupportFragmentManager(), botSheetTipe.getTag());
                return true;
            }
            return false;
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_penjualan);
        btnBarcode = findViewById(R.id.btn_barcode_penjualan);
        btnMasukKeranjang = findViewById(R.id.btn_masuk_keranjang);

    }

    private void SetupSearchView(String authToken, int id_store, String kategori){

        final SearchView searchView = findViewById(R.id.search_barang_penjualan);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchByCategory(authToken,id_store,kategori,query);
                call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                    @Override
                    public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                        penjualanItems = new ArrayList<>();
                        for (int i=0; i<response.body().size(); i++){
                            penjualanItems.add(new PenjualanItem(
                                    response.body().get(i).getFoto_barang(),
                                    response.body().get(i).getTipeBarang(),
                                    response.body().get(i).getSkuCode(),
                                    response.body().get(i).getArtikelBarang(),
                                    response.body().get(i).getNamaBarang(),
                                    response.body().get(i).getHargaBarang(),
                                    "0"
                            ));
                        }
                        PenjualanAdapter adapter = new PenjualanAdapter(penjualanItems, PenjualanActivity.this);
                        rvPenjualan.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                        new SweetAlertDialog(PenjualanActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                    Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchByCategory(authToken,id_store,kategori,"");
                    call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                        @Override
                        public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                            penjualanItems = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                penjualanItems.add(new PenjualanItem(
                                        response.body().get(i).getFoto_barang(),
                                        response.body().get(i).getTipeBarang(),
                                        response.body().get(i).getSkuCode(),
                                        response.body().get(i).getArtikelBarang(),
                                        response.body().get(i).getNamaBarang(),
                                        response.body().get(i).getHargaBarang(),
                                        "0"
                                ));
                            }
                            PenjualanAdapter adapter = new PenjualanAdapter(penjualanItems, PenjualanActivity.this);
                            rvPenjualan.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                            new SweetAlertDialog(PenjualanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                } else {
                    Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchByCategory(authToken,id_store,kategori,newText);
                    call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                        @Override
                        public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                            penjualanItems = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                penjualanItems.add(new PenjualanItem(
                                        response.body().get(i).getFoto_barang(),
                                        response.body().get(i).getTipeBarang(),
                                        response.body().get(i).getSkuCode(),
                                        response.body().get(i).getArtikelBarang(),
                                        response.body().get(i).getNamaBarang(),
                                        response.body().get(i).getHargaBarang(),
                                        "0"
                                ));
                            }
                            PenjualanAdapter adapter = new PenjualanAdapter(penjualanItems, PenjualanActivity.this);
                            rvPenjualan.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                            new SweetAlertDialog(PenjualanActivity.this, SweetAlertDialog.ERROR_TYPE)
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
        if (view == btnBarcode){
            if (ContextCompat.checkSelfPermission(PenjualanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(PenjualanActivity.this, Manifest.permission.CAMERA)){
                    startScan();
                } else {
                    ActivityCompat.requestPermissions(PenjualanActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                }
            } else {
                startScan();
            }
        } else if (view == btnMasukKeranjang){
                Intent masukKeranjang = new Intent(this, KeranjangActivity.class);
                startActivity(masukKeranjang);
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