package com.example.posrudyproject.ui.penjual.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualEndpoint;
import com.example.posrudyproject.retrofit.PenjualanEndpoint;
import com.example.posrudyproject.retrofit.TokoEndpoint;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.laporan.activity.DetailLaporanPenjualActivity;
import com.example.posrudyproject.ui.laporan.activity.LaporanPenjualActivity;
import com.example.posrudyproject.ui.laporan.adapter.LaporanPenjualAdapter;
import com.example.posrudyproject.ui.laporan.model.LaporanPenjualItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerTipeItem;
import com.example.posrudyproject.ui.main.MainActivity;
import com.example.posrudyproject.ui.pelanggan.activity.PelangganActivity;
import com.example.posrudyproject.ui.pelanggan.activity.TambahPelangganActivity;
import com.example.posrudyproject.ui.penjual.adapter.PenjualAdapter;
import com.example.posrudyproject.ui.penjual.fragment.BotSheetTokoTujuanFragment;
import com.example.posrudyproject.ui.penjual.model.PenjualItem;
import com.example.posrudyproject.ui.penjual.model.TokoItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualActivity extends AppCompatActivity implements OnItemClickListener, View.OnClickListener {

    MaterialToolbar mToolbar;
    RecyclerView rvPenjual;
    MaterialButton btnPindahPenjual;
    List<PenjualItem> penjualItems;
    List<TokoItem> tokoItems;
    List<KeranjangItem> keranjangItems;
    PenjualEndpoint penjualEndpoint;
    TokoEndpoint tokoEndpoint;
    int penjual_terpilih = 0;
    String noHpPelanggan,namaPelanggan,namaPenjual, auth_token;
    Integer idPenjual,isPenjualan, id_store;
    PenjualanEndpoint penjualanEndpoint;
    String NominalTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjual);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        id_store = preferences.getInt("id_store", 0);
        auth_token = ("Bearer ").concat(token);
        rvPenjual = findViewById(R.id.rv_penjual);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rvPenjual.setLayoutManager(manager);
        penjualEndpoint = ApiClient.getClient().create(PenjualEndpoint.class);
        tokoEndpoint = ApiClient.getClient().create(TokoEndpoint.class);

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
        } else {
            keranjangItems = new ArrayList<>();
        }

        //INIT VIEW
        initComponent();

        initToolbar();

        SetupSearchView(auth_token, id_store);

        //SET LISTENER
        btnPindahPenjual.setOnClickListener(this);

        //Penjual List
        Call<List<PenjualItem>> call = penjualEndpoint.getAllByIdStore(auth_token, id_store);
        SweetAlertDialog pDialog = new SweetAlertDialog(PenjualActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<List<PenjualItem>>() {
            @Override
            public void onResponse(Call<List<PenjualItem>> call, Response<List<PenjualItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(PenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    penjualItems = new ArrayList<>();
                    for (int i=0; i<response.body().size(); i++){
                        penjualItems.add(new PenjualItem(
                                response.body().get(i).getId(),
                                response.body().get(i).getTanggal_join(),
                                response.body().get(i).getNama_karyawan(),
                                response.body().get(i).getId_office(),
                                response.body().get(i).getLokasi_office(),
                                response.body().get(i).getId_store(),
                                response.body().get(i).getLokasi_store(),
                                response.body().get(i).getJabatan(),
                                response.body().get(i).getNo_hp(),
                                response.body().get(i).getEmail(),
                                response.body().get(i).getAlamat(),
                                response.body().get(i).getTotal_transaksi(),
                                response.body().get(i).getRowstatus(),
                                response.body().get(i).getImage()
                        ));
                    }
                    PenjualAdapter adapter = new PenjualAdapter(penjualItems,PenjualActivity.this);
                    rvPenjual.setAdapter(adapter);
                    rvPenjual.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<List<PenjualItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(PenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPenjualan == 1) {
                    Intent pilihPenjual = new Intent(PenjualActivity.this, KeranjangActivity.class);
                    pilihPenjual.putExtra("namaPenjual",namaPenjual);
                    pilihPenjual.putExtra("idPenjual",idPenjual);
                    pilihPenjual.putExtra("itemForBuyAddPenjual", (Serializable) keranjangItems);
                    if (namaPelanggan != "") {
                        pilihPenjual.putExtra("namaPelangganFromPenjual", namaPelanggan);
                        pilihPenjual.putExtra("noHpPelangganFromPenjual", noHpPelanggan);
                    }


                    startActivity(pilihPenjual);
                    penjual_terpilih = idPenjual;
                    finish();
                } else {
                    Intent intent = new Intent(PenjualActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        });
    }

    private void initComponent() {
        //init
        mToolbar = findViewById(R.id.toolbar_penjual);
        btnPindahPenjual = findViewById(R.id.btn_pindahkan_penjual);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemClickListener(View view, int position) {
        if (isPenjualan != 0) {
            Toast.makeText(this, "Pilih " + penjualItems.get(position).getNama_karyawan(), Toast.LENGTH_SHORT).show();
            Intent pilihPenjual = new Intent(this, KeranjangActivity.class);

            pilihPenjual.putExtra("namaPenjual",penjualItems.get(position).getNama_karyawan());
            pilihPenjual.putExtra("idPenjual",penjualItems.get(position).getId());
            pilihPenjual.putExtra("itemForBuyAddPenjual", (Serializable) keranjangItems);
            if (namaPelanggan != "") {
                pilihPenjual.putExtra("namaPelangganFromPenjual", namaPelanggan);
                pilihPenjual.putExtra("noHpPelangganFromPenjual", noHpPelanggan);
            }


            startActivity(pilihPenjual);
        } else {
            Intent detailLaporan = new Intent(PenjualActivity.this, LaporanPenjualActivity.class);
            detailLaporan.putExtra("idPenjual",penjualItems.get(position).getId());
            startActivity(detailLaporan);
        }

        penjual_terpilih = penjualItems.get(position).getId();
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Pindahkan Penjual", Toast.LENGTH_SHORT).show();
        //function pindahkan karyawan dengan action klik salah satu penjual/karyawan dahulu,
        //lalu pilih toko tujuan.

        //Toko List
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        String auth_token = ("Bearer ").concat(token);
        Call<List<TokoItem>> call = tokoEndpoint.getAll(auth_token);
        call.enqueue(new Callback<List<TokoItem>>() {
            @Override
            public void onResponse(Call<List<TokoItem>> call, Response<List<TokoItem>> response) {
                if (response.isSuccessful()){
                    tokoItems = new ArrayList<>();
                    for (int i=0; i<response.body().size(); i++){
                        tokoItems.add(new TokoItem(
                                response.body().get(i).getId(),
                                response.body().get(i).getNamaToko()
                        ));
                    }
                    BotSheetTokoTujuanFragment botSheetToko = new BotSheetTokoTujuanFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("tokoItems", (Serializable) tokoItems);
                    botSheetToko.setArguments(bundle);
                    botSheetToko.show(getSupportFragmentManager(), botSheetToko.getTag());
                }
            }

            @Override
            public void onFailure(Call<List<TokoItem>> call, Throwable t) {
                new SweetAlertDialog(PenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });


    }

    private void SetupSearchView(String authToken, int id_store){
        final SearchView searchView = findViewById(R.id.search_penjual);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<List<PenjualItem>> call = penjualEndpoint.searchForStore(authToken, query, id_store);
                call.enqueue(new Callback<List<PenjualItem>>() {
                    @Override
                    public void onResponse(Call<List<PenjualItem>> call, Response<List<PenjualItem>> response) {
                        penjualItems = new ArrayList<>();
                        for (int i=0; i<response.body().size(); i++){
                            penjualItems.add(new PenjualItem(
                                    response.body().get(i).getId(),
                                    response.body().get(i).getTanggal_join(),
                                    response.body().get(i).getNama_karyawan(),
                                    response.body().get(i).getId_office(),
                                    response.body().get(i).getLokasi_office(),
                                    response.body().get(i).getId_store(),
                                    response.body().get(i).getLokasi_store(),
                                    response.body().get(i).getJabatan(),
                                    response.body().get(i).getNo_hp(),
                                    response.body().get(i).getEmail(),
                                    response.body().get(i).getAlamat(),
                                    response.body().get(i).getTotal_transaksi(),
                                    response.body().get(i).getRowstatus(),
                                    response.body().get(i).getImage()
                            ));
                        }
                        PenjualAdapter adapter = new PenjualAdapter(penjualItems,PenjualActivity.this);
                        rvPenjual.setAdapter(adapter);
                        rvPenjual.setHasFixedSize(true);
                    }

                    @Override
                    public void onFailure(Call<List<PenjualItem>> call, Throwable t) {
                        new SweetAlertDialog(PenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                    Call<List<PenjualItem>> call = penjualEndpoint.searchForStore(authToken, "", id_store);
                    call.enqueue(new Callback<List<PenjualItem>>() {
                        @Override
                        public void onResponse(Call<List<PenjualItem>> call, Response<List<PenjualItem>> response) {
                            penjualItems = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                penjualItems.add(new PenjualItem(
                                        response.body().get(i).getId(),
                                        response.body().get(i).getTanggal_join(),
                                        response.body().get(i).getNama_karyawan(),
                                        response.body().get(i).getId_office(),
                                        response.body().get(i).getLokasi_office(),
                                        response.body().get(i).getId_store(),
                                        response.body().get(i).getLokasi_store(),
                                        response.body().get(i).getJabatan(),
                                        response.body().get(i).getNo_hp(),
                                        response.body().get(i).getEmail(),
                                        response.body().get(i).getAlamat(),
                                        response.body().get(i).getTotal_transaksi(),
                                        response.body().get(i).getRowstatus(),
                                        response.body().get(i).getImage()
                                ));
                            }
                            PenjualAdapter adapter = new PenjualAdapter(penjualItems,PenjualActivity.this);
                            rvPenjual.setAdapter(adapter);
                            rvPenjual.setHasFixedSize(true);
                        }

                        @Override
                        public void onFailure(Call<List<PenjualItem>> call, Throwable t) {
                            new SweetAlertDialog(PenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                } else {
                    Call<List<PenjualItem>> call = penjualEndpoint.searchForStore(authToken, newText, id_store);
                    call.enqueue(new Callback<List<PenjualItem>>() {
                        @Override
                        public void onResponse(Call<List<PenjualItem>> call, Response<List<PenjualItem>> response) {
                            penjualItems = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                penjualItems.add(new PenjualItem(
                                        response.body().get(i).getId(),
                                        response.body().get(i).getTanggal_join(),
                                        response.body().get(i).getNama_karyawan(),
                                        response.body().get(i).getId_office(),
                                        response.body().get(i).getLokasi_office(),
                                        response.body().get(i).getId_store(),
                                        response.body().get(i).getLokasi_store(),
                                        response.body().get(i).getJabatan(),
                                        response.body().get(i).getNo_hp(),
                                        response.body().get(i).getEmail(),
                                        response.body().get(i).getAlamat(),
                                        response.body().get(i).getTotal_transaksi(),
                                        response.body().get(i).getRowstatus(),
                                        response.body().get(i).getImage()
                                ));
                            }
                            PenjualAdapter adapter = new PenjualAdapter(penjualItems,PenjualActivity.this);
                            rvPenjual.setAdapter(adapter);
                            rvPenjual.setHasFixedSize(true);
                        }

                        @Override
                        public void onFailure(Call<List<PenjualItem>> call, Throwable t) {
                            new SweetAlertDialog(PenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
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

    public void onUserSelectValue(int id_toko, String nama_toko){
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        String auth_token = ("Bearer ").concat(token);

        List<PenjualItem> update = new ArrayList<>();

        update.add(new PenjualItem(
                penjual_terpilih,
                null,
                "",
                0,
                "",
                id_toko,
                nama_toko,
                "",
                "",
                "",
                "",
                0,
                1,
                ""
        ));

        Call<Map> call = penjualEndpoint.pindahStore(auth_token,update.get(0));
        SweetAlertDialog pDialog = new SweetAlertDialog(PenjualActivity.this, SweetAlertDialog.WARNING_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<Map>(){
            @Override
            public void onResponse(Call<Map> call, Response<Map> response){
                pDialog.dismiss();
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(PenjualActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText(response.body().get("message").toString());
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent intent = new Intent(PenjualActivity.this, PenjualActivity.class);
                        startActivity(intent);
                    }
                });
                sweetAlertDialog.show();
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(PenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Intent intent = new Intent(PenjualActivity.this, PenjualActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

    }
}