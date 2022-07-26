package com.example.posrudyproject.ui.penjualan.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
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
import com.example.posrudyproject.ui.laporan.activity.PenjualanPerTipeActivity;
import com.example.posrudyproject.ui.laporan.adapter.PenjualanPerTipeAdapter;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerTipeItem;
import com.example.posrudyproject.ui.main.MainActivity;
import com.example.posrudyproject.ui.penjualan.adapter.PenjualanAdapter;
import com.example.posrudyproject.ui.penjualan.model.PenjualanItem;
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.example.posrudyproject.ui.produk.activity.ProdukActivity;
import com.example.posrudyproject.ui.produk.adapter.ProdukAdapter;
import com.example.posrudyproject.ui.produk.model.ProdukItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.Result;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
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
    Integer id_store;
    PenjualanAdapter adapter;
    Map<String,Integer> qty = new HashMap<>();
    public static final int REQUEST_CODE = 1;
    public static final String INTENT_FILTER_TIPE = "INTENT_FILTER_TIPE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        SharedPreferences preferencesCart = getSharedPreferences("keranjang", Context.MODE_PRIVATE);

        id_kategori = preferences.getString("id_kategori","");
        String token = preferences.getString("token","");
        id_store = preferences.getInt("id_store", 0);
        auth_token = ("Bearer ").concat(token);
        keranjangItems = new ArrayList<>();
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

                    adapter = new PenjualanAdapter(penjualanItems, PenjualanActivity.this);
                    rvPenjualan.setAdapter(adapter);
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

        btnMasukKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(PenjualanActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Lanjutkan Transaksi?")
                        .setContentText("Kembali untuk memilih Kategori yang lain!")
                        .setCancelText("Kembali")
                        .setConfirmText("Lanjut")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                for (int i=0; i<penjualanItems.size(); i++) {
                                    keranjangItems.add(new KeranjangItem(
                                            penjualanItems.get(i).getFoto_barang(),
                                            penjualanItems.get(i).getTipeBarang(),
                                            penjualanItems.get(i).getSkuCode(),
                                            penjualanItems.get(i).getArtikelBarang(),
                                            penjualanItems.get(i).getNamaBarang(),
                                            penjualanItems.get(i).getHargaBarang(),
                                            penjualanItems.get(i).getHargaBarang(),
                                            "",
                                            preferencesCart.getString(penjualanItems.get(i).getArtikelBarang(), "0"),
                                            String.valueOf(Double.valueOf(penjualanItems.get(i).getHargaBarang()) * Double.valueOf(preferencesCart.getString(penjualanItems.get(i).getArtikelBarang(), "0"))),
                                            preferencesCart.getString(penjualanItems.get(i).getArtikelBarang(), "0")
                                    ));
                                }
                                Intent masukKeranjang = new Intent(PenjualanActivity.this, KeranjangActivity.class);
                                startActivity(masukKeranjang);
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Intent intent = new Intent(PenjualanActivity.this, KategoriActivity.class);
                                startActivity(intent);
                                sDialog.cancel();
                            }
                        })
                        .show();

            }
        });
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
        SharedPreferences preferencesCart = getSharedPreferences("keranjang", Context.MODE_PRIVATE);
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
                                    preferencesCart.getString(response.body().get(i).getArtikelBarang(), "0")
                            ));
                        }
                        adapter = new PenjualanAdapter(penjualanItems, PenjualanActivity.this);
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
                                        preferencesCart.getString(response.body().get(i).getArtikelBarang(), "0")
                                ));
                            }
                            adapter = new PenjualanAdapter(penjualanItems, PenjualanActivity.this);
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

    private BroadcastReceiver someBroadcastReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO extract extras from intent

            SharedPreferences preferencesCart = getSharedPreferences("keranjang", Context.MODE_PRIVATE);

            if (intent.getStringExtra("nama_tipe") != "") {
                Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchByCategory(auth_token,id_store,id_kategori,intent.getStringExtra("nama_tipe"));
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
                                    preferencesCart.getString(response.body().get(i).getArtikelBarang(), "0")
                            ));
                        }
                        adapter = new PenjualanAdapter(penjualanItems, PenjualanActivity.this);
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


        }
    };
    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(someBroadcastReceiver,
                new IntentFilter(INTENT_FILTER_TIPE));
    }
    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(someBroadcastReceiver);
        super.onPause();
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
        Intent intent = new Intent(PenjualanActivity.this,ScannerActivity.class);
        startActivityForResult(intent , REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {
                SharedPreferences preferencesCart = getSharedPreferences("keranjang", Context.MODE_PRIVATE);
                Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchByCategory(auth_token,id_store,id_kategori,data.getStringExtra("key"));
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
                                    preferencesCart.getString(response.body().get(i).getArtikelBarang(), "0")
                            ));
                        }
                        adapter = new PenjualanAdapter(penjualanItems, PenjualanActivity.this);
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
        } catch (Exception ex) {
            Toast.makeText(PenjualanActivity.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
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