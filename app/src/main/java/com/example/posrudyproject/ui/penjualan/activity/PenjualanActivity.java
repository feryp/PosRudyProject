package com.example.posrudyproject.ui.penjualan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualanEndpoint;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterTipeFragment;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.main.MainActivity;
import com.example.posrudyproject.ui.penjual.activity.PenjualActivity;
import com.example.posrudyproject.ui.penjual.adapter.PenjualAdapter;
import com.example.posrudyproject.ui.penjual.model.PenjualItem;
import com.example.posrudyproject.ui.penjualan.adapter.PenjualanAdapter;
import com.example.posrudyproject.ui.penjualan.model.PenjualanItem;
import com.example.posrudyproject.ui.penyimpanan.activity.PenyimpananActivity;
import com.example.posrudyproject.ui.penyimpanan.adapter.ProdukTersediaAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualanActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar mToolbar;
    SearchView searchView;
    AppCompatImageButton btnBarcode;
    MaterialButton btnMasukKeranjang;
    RecyclerView rvPenjualan;
    PenjualanAdapter adapter;
    List<PenjualanItem> penjualanItems;
    PenjualanEndpoint penjualanEndpoint;
    PenyimpananEndpoint penyimpananEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        int id_store = preferences.getInt("id_store", 0);
        String auth_token = ("Bearer ").concat(token);

        penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);

        rvPenjualan = findViewById(R.id.rv_penjualan);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        rvPenjualan.setLayoutManager(manager);
        rvPenjualan.setHasFixedSize(true);

        //INIT VIEW
        initComponent();

        initToolbar();

        //SET LISTENER
        btnMasukKeranjang.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        SetupSearchView(auth_token, id_store, String.valueOf(extras.getInt("id_kategori")));
        if (extras != null) {
            Call<List<ProdukTersediaItem>> call_stock = penyimpananEndpoint.stockAvailPerStoreByCategory(auth_token,id_store,String.valueOf(extras.getInt("id_kategori")));
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
                                    response.body().get(i).getArtikelBarang(),
                                    response.body().get(i).getNamaBarang(),
                                    response.body().get(i).getHargaBarang(),
                                    "0"
                            ));
                        }
                        PenjualanAdapter adapter = new PenjualanAdapter(penjualanItems, PenjualanActivity.this);
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

        btnBarcode = findViewById(R.id.btn_barcode);
        btnMasukKeranjang = findViewById(R.id.btn_masuk_keranjang);

    }

    @Override
    public void onClick(View view) {
        Intent masukKeranjang = new Intent(this, KeranjangActivity.class);
        startActivity(masukKeranjang);
    }

    private void SetupSearchView(String authToken, int id_store, String kategori){

        final SearchView searchView = findViewById(R.id.search_barang);
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
}