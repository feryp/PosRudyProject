package com.example.posrudyproject.ui.pesananTunggu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PesananTungguEndpoint;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
import com.example.posrudyproject.ui.pesananTunggu.adapter.PesananTungguAdapter;
import com.example.posrudyproject.ui.pesananTunggu.model.BarangPesananTungguItem;
import com.example.posrudyproject.ui.pesananTunggu.model.PesananTungguItem;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananTungguActivity extends AppCompatActivity implements OnItemClickListener {

    MaterialToolbar mToolbar;
    RecyclerView rvPesananTunggu;
    PesananTungguAdapter pesananTungguAdapter;
    PesananTungguEndpoint pesananTungguEndpoint;
    String auth_token;
    List<PesananTungguItem> pesananTungguItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_tunggu);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        auth_token = ("Bearer ").concat(token);
        pesananTungguEndpoint = ApiClient.getClient().create(PesananTungguEndpoint.class);

        //INIT VIEW
        initComponent();

        initToolbar();

        Call<List<PesananTungguItem>> call = pesananTungguEndpoint.getAll(auth_token);
        SweetAlertDialog pDialog = new SweetAlertDialog(PesananTungguActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        call.enqueue(new Callback<List<PesananTungguItem>>() {
            @Override
            public void onResponse(Call<List<PesananTungguItem>> call, Response<List<PesananTungguItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(PesananTungguActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    pesananTungguItems = new ArrayList<>();
                    DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                    df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                    for (int i=0; i<response.body().size(); i++) {
                        pesananTungguItems.add(new PesananTungguItem(
                                response.body().get(i).getId(),
                                response.body().get(i).getNoPesanan(),
                                response.body().get(i).getTglPesanan(),
                                response.body().get(i).getId_store(),
                                response.body().get(i).getLokasi_store(),
                                response.body().get(i).getNo_hp_pelanggan(),
                                response.body().get(i).getPelangganPesanan(),
                                ("Rp").concat(df.format(Double.valueOf(response.body().get(i).getTotalHargaPesanan()))),
                                response.body().get(i).getKetPesanan(),
                                response.body().get(i).getBarangPesanan()
                        ));
                    }
                    //Setup Adapter
                    pesananTungguAdapter = new PesananTungguAdapter(pesananTungguItems,auth_token, PesananTungguActivity.this);
                    rvPesananTunggu.setLayoutManager(new LinearLayoutManager(PesananTungguActivity.this));
                    rvPesananTunggu.setAdapter(pesananTungguAdapter);
                    rvPesananTunggu.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<List<PesananTungguItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(PesananTungguActivity.this, SweetAlertDialog.ERROR_TYPE)
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
        // init
        mToolbar = findViewById(R.id.toolbar_pesanan_tunggu);
        rvPesananTunggu = findViewById(R.id.rv_pesanan_tunggu);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent masukKeranjang = new Intent(PesananTungguActivity.this, KeranjangActivity.class);
        masukKeranjang.putExtra("itemFromQueue", (Serializable) pesananTungguItems.get(position).getBarangPesanan());
        masukKeranjang.putExtra("namaPelanggan",pesananTungguItems.get(position).getPelangganPesanan());
        masukKeranjang.putExtra("noHp",pesananTungguItems.get(position).getNo_hp_pelanggan());
        Call<Map> delete = pesananTungguEndpoint.delete(auth_token,pesananTungguItems.get(position).getId());
        delete.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                startActivity(masukKeranjang);
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                new SweetAlertDialog(PesananTungguActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });
    }
}