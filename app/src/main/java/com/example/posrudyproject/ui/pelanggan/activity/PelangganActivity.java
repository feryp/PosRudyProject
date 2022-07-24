package com.example.posrudyproject.ui.pelanggan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PelangganEndpoint;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.main.MainActivity;
import com.example.posrudyproject.ui.pelanggan.adapter.PelangganAdapter;
import com.example.posrudyproject.ui.pelanggan.model.Pelanggan;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PelangganActivity extends AppCompatActivity implements OnItemClickListener, View.OnClickListener {

    MaterialToolbar mToolbar;
    SearchView searchView;
    RecyclerView rvPelanggan;
    MaterialButton btnTambahPelanggan;

    List<Pelanggan> pelangganItems;
    List<KeranjangItem> keranjangItems;
    PelangganEndpoint pelangganEndpoint;
    String noHpPelanggan,namaPelanggan,namaPenjual,ongkir,ekspedisi,diskonPersen,diskonRupiah,diskon_remark;
    Integer idPenjual,isPenjualan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelanggan);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        String auth_token = ("Bearer ").concat(token);
        rvPelanggan = findViewById(R.id.rv_pelanggan);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rvPelanggan.setLayoutManager(manager);
        pelangganEndpoint = ApiClient.getClient().create(PelangganEndpoint.class);

        Bundle bundle = getIntent().getExtras();
        keranjangItems = new ArrayList<>();
        isPenjualan = 0;
        if (bundle != null){
            keranjangItems = (List<KeranjangItem>) bundle.getSerializable("itemForBuy");
            isPenjualan = bundle.getInt("isPenjualan",0);
            if (bundle.getString("namaPenjual") != null) {
                namaPenjual = bundle.getString("namaPenjual");
                idPenjual = bundle.getInt("idPenjual");
            }
            if (bundle.getString("namaPelanggan") != null) {
                namaPelanggan = bundle.getString("namaPelanggan");
                noHpPelanggan = bundle.getString("noHpPelanggan");
            }
            if (bundle.getString("ongkir") != null) {
                ongkir = bundle.getString("ongkir");
                ekspedisi = bundle.getString("ekspedisi");
            }
            if (bundle.getString("diskonPersen") != null) {
                diskonPersen = bundle.getString("diskonPersen");
                diskon_remark = bundle.getString("diskon_remark");
            }
            if (bundle.getString("diskonRupiah") != null) {
                diskonRupiah = bundle.getString("diskonRupiah");
                diskon_remark = bundle.getString("diskon_remark");
            }
        } else {
            keranjangItems = new ArrayList<>();
        }
        //INIT VIEW
        initComponent();

        initToolbar();
        btnTambahPelanggan.setOnClickListener(this);
        //SET LISTENER

        //Pelanggan List
        SetupSearchView(auth_token);

        Call<List<Pelanggan>> call = pelangganEndpoint.getAll(auth_token);
        SweetAlertDialog pDialog = new SweetAlertDialog(PelangganActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<List<Pelanggan>>() {
            @Override
            public void onResponse(Call<List<Pelanggan>> call, Response<List<Pelanggan>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(PelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                }else{
                    pDialog.dismiss();
                    pelangganItems = new ArrayList<>();
                    for (int i=0; i<response.body().size(); i++){
                        pelangganItems.add(new Pelanggan(
                                response.body().get(i).getId(),
                                response.body().get(i).getNama_pelanggan(),
                                response.body().get(i).getNik(),
                                response.body().get(i).getNo_hp(),
                                response.body().get(i).getEmail(),
                                response.body().get(i).getAlamat(),
                                response.body().get(i).getTotal_kunjungan(),
                                response.body().get(i).getKuantitas(),
                                response.body().get(i).getPoin(),
                                response.body().get(i).getTotal_pembelian()
                                )
                        );
                    }
                    PelangganAdapter adapter = new PelangganAdapter(pelangganItems,PelangganActivity.this);
                    rvPelanggan.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Pelanggan>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(PelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPenjualan == 1) {
                    Intent tambahPelanggan = new Intent(PelangganActivity.this, KeranjangActivity.class);
                    tambahPelanggan.putExtra("namaPelanggan",namaPelanggan);
                    tambahPelanggan.putExtra("noHp",noHpPelanggan);
                    tambahPelanggan.putExtra("itemForBuyAddPelanggan", (Serializable) keranjangItems);

                    if (namaPenjual != "") {
                        tambahPelanggan.putExtra("namaPenjualFromPelanggan", namaPenjual);
                        tambahPelanggan.putExtra("idPenjualFromPelanggan", idPenjual);
                    }
                    if (ongkir != "") {
                        tambahPelanggan.putExtra("ongkir", ongkir);
                        tambahPelanggan.putExtra("ekspedisi", ekspedisi);
                    }
                    if (diskonPersen != "") {
                        tambahPelanggan.putExtra("diskonPersen", diskonPersen);
                        tambahPelanggan.putExtra("diskon_remark", diskon_remark);
                    }
                    if (diskonRupiah != "") {
                        tambahPelanggan.putExtra("diskonRupiah", diskonRupiah);
                        tambahPelanggan.putExtra("diskon_remark", diskon_remark);
                    }
                    startActivity(tambahPelanggan);
                    finish();
                } else {
                    Intent intent = new Intent(PelangganActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initToolbar() {

        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_riwayat_pelanggan){
                Intent riwayatPelanggan = new Intent(this, RiwayatPelangganActivity.class);
                startActivity(riwayatPelanggan);
                return true;
            }
            return false;
        });
    }

    private void initComponent() {
        //init
        mToolbar = findViewById(R.id.toolbar_pelanggan);
        //searchView = findViewById(R.id.search_pelanggan);
        rvPelanggan = findViewById(R.id.rv_pelanggan);
        btnTambahPelanggan = findViewById(R.id.btn_tambah_pelanggan);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemClickListener(View view, int position) {
        switch (view.getId()){
            case R.id.item_pelanggan:
                Toast.makeText(this, "Pilih " + pelangganItems.get(position).getNama_pelanggan(), Toast.LENGTH_SHORT).show();
                Intent tambahPelanggan = new Intent(this, KeranjangActivity.class);
                tambahPelanggan.putExtra("namaPelanggan",pelangganItems.get(position).getNama_pelanggan());
                tambahPelanggan.putExtra("noHp",pelangganItems.get(position).getNo_hp());
                tambahPelanggan.putExtra("itemForBuyAddPelanggan", (Serializable) keranjangItems);

                if (namaPenjual != "") {
                    tambahPelanggan.putExtra("namaPenjualFromPelanggan", namaPenjual);
                    tambahPelanggan.putExtra("idPenjualFromPelanggan", idPenjual);
                }
                if (ongkir != "") {
                    tambahPelanggan.putExtra("ongkir", ongkir);
                    tambahPelanggan.putExtra("ekspedisi", ekspedisi);
                }
                if (diskonPersen != "") {
                    tambahPelanggan.putExtra("diskonPersen", diskonPersen);
                    tambahPelanggan.putExtra("diskon_remark", diskon_remark);
                }
                if (diskonRupiah != "") {
                    tambahPelanggan.putExtra("diskonRupiah", diskonRupiah);
                    tambahPelanggan.putExtra("diskon_remark", diskon_remark);
                }
                startActivity(tambahPelanggan);
                break;
            case R.id.btn_edit_pelanggan:
                Toast.makeText(this, "Ubah " + pelangganItems.get(position).getId(), Toast.LENGTH_SHORT).show();
                Intent editPelanggan = new Intent(this, TambahPelangganActivity.class);

                editPelanggan.putExtra("id",pelangganItems.get(position).getId());
                editPelanggan.putExtra("namaPelanggan",pelangganItems.get(position).getNama_pelanggan());
                editPelanggan.putExtra("nik",pelangganItems.get(position).getNik());
                editPelanggan.putExtra("noHp",pelangganItems.get(position).getNo_hp());
                editPelanggan.putExtra("email",pelangganItems.get(position).getEmail());
                editPelanggan.putExtra("alamat",pelangganItems.get(position).getAlamat());
                editPelanggan.putExtra("totalKunjungan",pelangganItems.get(position).getTotal_kunjungan());
                editPelanggan.putExtra("kuantitas",pelangganItems.get(position).getKuantitas());
                editPelanggan.putExtra("poin",pelangganItems.get(position).getPoin());
                editPelanggan.putExtra("totalPembelian",pelangganItems.get(position).getTotal_pembelian());
                if (ongkir != "") {
                    editPelanggan.putExtra("ongkir", ongkir);
                    editPelanggan.putExtra("ekspedisi", ekspedisi);
                }
                if (diskonPersen != "") {
                    editPelanggan.putExtra("diskonPersen", diskonPersen);
                    editPelanggan.putExtra("diskon_remark", diskon_remark);
                }
                if (diskonRupiah != "") {
                    editPelanggan.putExtra("diskonRupiah", diskonRupiah);
                    editPelanggan.putExtra("diskon_remark", diskon_remark);
                }
                startActivity(editPelanggan);
                break;
        }

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Tambah Pelanggan", Toast.LENGTH_SHORT).show();
        Intent tambahPelanggan = new Intent(this, TambahPelangganActivity.class);
        tambahPelanggan.putExtra("isPenjualan", isPenjualan);
        tambahPelanggan.putExtra("itemForBuy", (Serializable) keranjangItems);
        if (ongkir != "") {
            tambahPelanggan.putExtra("ongkir", ongkir);
            tambahPelanggan.putExtra("ekspedisi", ekspedisi);
        }
        if (diskonPersen != "") {
            tambahPelanggan.putExtra("diskonPersen", diskonPersen);
            tambahPelanggan.putExtra("diskon_remark", diskon_remark);
        }
        if (diskonRupiah != "") {
            tambahPelanggan.putExtra("diskonRupiah", diskonRupiah);
            tambahPelanggan.putExtra("diskon_remark", diskon_remark);
        }
        startActivity(tambahPelanggan);
    }

    private void SetupSearchView(String authToken){
        final SearchView searchView = findViewById(R.id.search_pelanggan);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<List<Pelanggan>> call = pelangganEndpoint.search(authToken,query);
                call.enqueue(new Callback<List<Pelanggan>>() {
                    @Override
                    public void onResponse(Call<List<Pelanggan>> call, Response<List<Pelanggan>> response) {
                        pelangganItems = new ArrayList<>();
                        for (int i=0; i<response.body().size(); i++){
                            pelangganItems.add(new Pelanggan(
                                    response.body().get(i).getId(),
                                    response.body().get(i).getNama_pelanggan(),
                                    response.body().get(i).getNik(),
                                    response.body().get(i).getNo_hp(),
                                    response.body().get(i).getEmail(),
                                    response.body().get(i).getAlamat(),
                                    response.body().get(i).getTotal_kunjungan(),
                                    response.body().get(i).getKuantitas(),
                                    response.body().get(i).getPoin(),
                                    response.body().get(i).getTotal_pembelian()
                                    )
                            );
                        }
                        PelangganAdapter adapter = new PelangganAdapter(pelangganItems,PelangganActivity.this);
                        rvPelanggan.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Pelanggan>> call, Throwable t) {
                        new SweetAlertDialog(PelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                    Call<List<Pelanggan>> call = pelangganEndpoint.search(authToken,"");
                    call.enqueue(new Callback<List<Pelanggan>>() {
                        @Override
                        public void onResponse(Call<List<Pelanggan>> call, Response<List<Pelanggan>> response) {
                            pelangganItems = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                pelangganItems.add(new Pelanggan(
                                        response.body().get(i).getId(),
                                        response.body().get(i).getNama_pelanggan(),
                                        response.body().get(i).getNik(),
                                        response.body().get(i).getNo_hp(),
                                        response.body().get(i).getEmail(),
                                        response.body().get(i).getAlamat(),
                                        response.body().get(i).getTotal_kunjungan(),
                                        response.body().get(i).getKuantitas(),
                                        response.body().get(i).getPoin(),
                                        response.body().get(i).getTotal_pembelian()
                                        )
                                );
                            }
                            PelangganAdapter adapter = new PelangganAdapter(pelangganItems,PelangganActivity.this);
                            rvPelanggan.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<Pelanggan>> call, Throwable t) {
                            new SweetAlertDialog(PelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                } else {
                    Call<List<Pelanggan>> call = pelangganEndpoint.search(authToken,newText);
                    call.enqueue(new Callback<List<Pelanggan>>() {
                        @Override
                        public void onResponse(Call<List<Pelanggan>> call, Response<List<Pelanggan>> response) {
                            pelangganItems = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                pelangganItems.add(new Pelanggan(
                                        response.body().get(i).getId(),
                                        response.body().get(i).getNama_pelanggan(),
                                        response.body().get(i).getNik(),
                                        response.body().get(i).getNo_hp(),
                                        response.body().get(i).getEmail(),
                                        response.body().get(i).getAlamat(),
                                        response.body().get(i).getTotal_kunjungan(),
                                        response.body().get(i).getKuantitas(),
                                        response.body().get(i).getPoin(),
                                        response.body().get(i).getTotal_pembelian()
                                        )
                                );
                            }
                            PelangganAdapter adapter = new PelangganAdapter(pelangganItems,PelangganActivity.this);
                            rvPelanggan.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<List<Pelanggan>> call, Throwable t) {
                            new SweetAlertDialog(PelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
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