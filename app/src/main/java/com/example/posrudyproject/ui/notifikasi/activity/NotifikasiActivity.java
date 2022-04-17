package com.example.posrudyproject.ui.notifikasi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.keranjang.adapter.KeranjangAdapter;
import com.example.posrudyproject.ui.laporan.adapter.RiwayatTransaksiPenjualAdapter;
import com.example.posrudyproject.ui.notifikasi.adapter.NotifikasiAdapter;
import com.example.posrudyproject.ui.notifikasi.model.NotifikasiItem;
import com.example.posrudyproject.ui.penyimpanan.activity.RiwayatPenyimpananActivity;
import com.example.posrudyproject.ui.penyimpanan.adapter.PagerMenuAdapter;
import com.example.posrudyproject.ui.penyimpanan.fragment.BarangKeluarFragment;
import com.example.posrudyproject.ui.penyimpanan.fragment.BarangMasukFragment;
import com.example.posrudyproject.ui.penyimpanan.fragment.BarangPindahFragment;
import com.example.posrudyproject.ui.penyimpanan.model.BarangKeluarItem;
import com.example.posrudyproject.ui.penyimpanan.model.BarangMasukItem;
import com.example.posrudyproject.ui.penyimpanan.model.DokumenBarangPindahItem;
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.internal.LinkedTreeMap;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifikasiActivity extends AppCompatActivity implements OnItemClickListener {

    MaterialToolbar mToolbar;
    RecyclerView rvNotifikasi;

    List<NotifikasiItem> notifikasiItems;
    NotifikasiAdapter adapter;
    PenyimpananEndpoint penyimpananEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        int id_store = preferences.getInt("id_store", 0);
        String auth_token = ("Bearer ").concat(token);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);
        //INIT VIEW
        initComponent();

        initToolbar();

        //NOTIFIKASI LIST
        notifikasiItems = new ArrayList<>();
        Call<Map> call = penyimpananEndpoint.notifikasi(auth_token, id_store);
        SweetAlertDialog pDialog = new SweetAlertDialog(NotifikasiActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(NotifikasiActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                    for (int i=0; i<((List<BarangMasukItem>) inner.get("masuk")).size(); i++){
                        Object item = ((List<BarangMasukItem>) inner.get("masuk")).get(i);
                        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) item;

                        notifikasiItems.add(new NotifikasiItem(
                                "Stok Masuk",
                                "Barang " + t.get("nama_barang").toString() + " telah masuk sebanyak " + t.get("kuantitas").toString(),
                                t.get("tanggal_masuk").toString()
                        ));
                    }

                    for (int i=0; i<((List<BarangKeluarItem>) inner.get("keluar")).size(); i++){
                        Object item = ((List<BarangKeluarItem>) inner.get("keluar")).get(i);
                        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) item;
                        notifikasiItems.add(new NotifikasiItem(
                                "Stok Keluar",
                                "Barang " + t.get("nama_barang").toString() + " telah keluar sebanyak " + t.get("kuantitas").toString(),
                                t.get("tanggal_keluar").toString()
                        ));
                    }

                    for (int i = 0; i<((List<ProdukTersediaItem>) inner.get("minimum")).size(); i++){
                        Object item = ((List<ProdukTersediaItem>) inner.get("minimum")).get(i);
                        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) item;
                        notifikasiItems.add(new NotifikasiItem(
                                "Stok Masuk",
                                "Barang " + t.get("nama_barang").toString() + " telah masuk sebanyak " + t.get("kuantitas").toString(),
                                new SimpleDateFormat("dd-MMM-yyyy").format(new Date())
                        ));
                    }

                    //Setup Adapter
                    NotifikasiAdapter adapter = new NotifikasiAdapter(notifikasiItems,NotifikasiActivity.this);
                    rvNotifikasi.setAdapter(adapter);
                    rvNotifikasi.setLayoutManager(new LinearLayoutManager(NotifikasiActivity.this));
                    rvNotifikasi.setHasFixedSize(true);

                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(NotifikasiActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_notifikasi);
        rvNotifikasi = findViewById(R.id.rv_notifikasi);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(this, notifikasiItems.get(position).getJudulNotif(), Toast.LENGTH_SHORT).show();
    }
}