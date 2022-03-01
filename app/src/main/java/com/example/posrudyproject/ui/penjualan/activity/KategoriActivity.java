package com.example.posrudyproject.ui.penjualan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.KategoriEndpoint;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.main.MainActivity;
import com.example.posrudyproject.ui.penjualan.adapter.KategoriAdapter;
import com.example.posrudyproject.ui.penjualan.model.KategoriItem;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KategoriActivity extends AppCompatActivity implements OnItemClickListener {

    MaterialToolbar mToolbar;
    SearchView searchView;
    AppCompatImageButton btnBarcode;
    RecyclerView rvKategori;
    KategoriAdapter adapter;
    List<KategoriItem> kategoriItems;
    KategoriEndpoint kategoriEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        String auth_token = ("Bearer ").concat(token);

        rvKategori = findViewById(R.id.rv_kategori);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rvKategori.setLayoutManager(manager);
        rvKategori.setHasFixedSize(true);

        kategoriEndpoint = ApiClient.getClient().create(KategoriEndpoint.class);
        //INIT VIEW
        initComponent();

        initToolbar();

        //Kategori List
        SetupSearchView(auth_token);

        Call<List<KategoriItem>> call = kategoriEndpoint.getAll(auth_token);
        SweetAlertDialog pDialog = new SweetAlertDialog(KategoriActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<List<KategoriItem>>() {
            @Override
            public void onResponse(Call<List<KategoriItem>> call, Response<List<KategoriItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(KategoriActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                }else{
                    pDialog.dismiss();
                    kategoriItems = new ArrayList<>();
                    for (int i=0; i<response.body().size(); i++){
                        kategoriItems.add(new KategoriItem(
                                response.body().get(i).getId(),
                                response.body().get(i).getNamaKetegori()
                        ));
                    }
                    //Setup Adapter
                    KategoriAdapter adapter = new KategoriAdapter(kategoriItems, KategoriActivity.this);
                    rvKategori.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<KategoriItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(KategoriActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KategoriActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initToolbar() {
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_keranjang) {
                Intent keranjang = new Intent(this, KeranjangActivity.class);
                startActivity(keranjang);
                return true;
            }
            return false;
        });
    }

    private void initComponent() {
        // init
        mToolbar = findViewById(R.id.toolbar_kategori);
        btnBarcode = findViewById(R.id.btn_barcode_kategori);
        rvKategori = findViewById(R.id.rv_kategori);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        Toast.makeText(this, "Pilih " + kategoriItems.get(position).getNamaKetegori(), Toast.LENGTH_SHORT).show();
        Intent kategori = new Intent(this, PenjualanActivity.class);
        kategori.putExtra("id_kategori", kategoriItems.get(position).getId());
        kategori.putExtra("id_store", preferences.getInt("id_store", 0));
        startActivity(kategori);
    }

    private void SetupSearchView(String authToken){
        final SearchView searchView = findViewById(R.id.search_kategori);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<List<KategoriItem>> call = kategoriEndpoint.search(authToken,query);
                call.enqueue(new Callback<List<KategoriItem>>() {
                    @Override
                    public void onResponse(Call<List<KategoriItem>> call, Response<List<KategoriItem>> response) {
                        kategoriItems = new ArrayList<>();
                        for (int i=0; i<response.body().size(); i++){
                            kategoriItems.add(new KategoriItem(
                                            response.body().get(i).getId(),
                                            response.body().get(i).getNamaKetegori()
                                    )
                            );
                        }
                        KategoriAdapter adapter = new KategoriAdapter(kategoriItems, KategoriActivity.this);
                        rvKategori.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<KategoriItem>> call, Throwable t) {
                        new SweetAlertDialog(KategoriActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    Call<List<KategoriItem>> call = kategoriEndpoint.search(authToken,"");
                    call.enqueue(new Callback<List<KategoriItem>>() {
                        @Override
                        public void onResponse(Call<List<KategoriItem>> call, Response<List<KategoriItem>> response) {
                            kategoriItems = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                kategoriItems.add(new KategoriItem(
                                                response.body().get(i).getId(),
                                                response.body().get(i).getNamaKetegori()
                                        )
                                );
                            }
                            KategoriAdapter adapter = new KategoriAdapter(kategoriItems, KategoriActivity.this);
                            rvKategori.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<KategoriItem>> call, Throwable t) {
                            new SweetAlertDialog(KategoriActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                } else {
                    Call<List<KategoriItem>> call = kategoriEndpoint.search(authToken,newText);
                    call.enqueue(new Callback<List<KategoriItem>>() {
                        @Override
                        public void onResponse(Call<List<KategoriItem>> call, Response<List<KategoriItem>> response) {
                            kategoriItems = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                kategoriItems.add(new KategoriItem(
                                                response.body().get(i).getId(),
                                                response.body().get(i).getNamaKetegori()
                                        )
                                );
                            }
                            KategoriAdapter adapter = new KategoriAdapter(kategoriItems, KategoriActivity.this);
                            rvKategori.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<KategoriItem>> call, Throwable t) {
                            new SweetAlertDialog(KategoriActivity.this, SweetAlertDialog.ERROR_TYPE)
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