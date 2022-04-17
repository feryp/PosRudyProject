package com.example.posrudyproject.ui.laporan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualanEndpoint;
import com.example.posrudyproject.ui.laporan.adapter.KategoriTerlarisAdapter;
import com.example.posrudyproject.ui.laporan.adapter.ProdukTerlarisAdapter;
import com.example.posrudyproject.ui.laporan.adapter.TipeTerlarisAdapter;
import com.example.posrudyproject.ui.laporan.adapter.TransaksiTerakhirAdapter;
import com.example.posrudyproject.ui.laporan.model.KategoriTerlarisItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerKategoriItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerProdukItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerTipeItem;
import com.example.posrudyproject.ui.laporan.model.ProdukTerlarisItem;
import com.example.posrudyproject.ui.laporan.model.TipeTerlarisItem;
import com.example.posrudyproject.ui.laporan.model.TransaksiTerakhirItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporanRangkumanActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar mToolbar;
    AppCompatTextView produkTerlarisEmpty, kategoriTerlarisEmpty, tipeTerlarisEmpty, transaksiEmpty, totalTransaksi;
    RecyclerView rvProdukTerlaris, rvKategoriTerlaris, rvTipeTerlaris, rvTransaksiTerakhir;
    MaterialButton btnDetailProduk, btnDetailKategori, btnDetailTipe, btnDetailTotalTransaksi, btnDetailTransaksiTerakhir;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;
    PenjualanEndpoint penjualanEndpoint;
    int id_store;
    String auth_token,DateFrom,DateTo;

    //PRODUK TERLARIS
    List<ProdukTerlarisItem> produkTerlarisItems;
    ProdukTerlarisAdapter produkTerlarisAdapter;

    //KATEGORI TERLARIS
    List<KategoriTerlarisItem> kategoriTerlarisItems;
    KategoriTerlarisAdapter kategoriTerlarisAdapter;

    //TIPE TERLARIS
    List<TipeTerlarisItem> tipeTerlarisItems;
    TipeTerlarisAdapter tipeTerlarisAdapter;

    //TRANSAKSI TERAKHIR
    List<TransaksiTerakhirItem> transaksiTerakhirItems;
    TransaksiTerakhirAdapter transaksiTerakhirAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_rangkuman);
        penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        auth_token = ("Bearer ").concat(token);
        id_store = preferences.getInt("id_store", 0);

        produkTerlarisItems = new ArrayList<>();
        kategoriTerlarisItems = new ArrayList<>();
        transaksiTerakhirItems = new ArrayList<>();
        tipeTerlarisItems = new ArrayList<>();

        //INIT VIEW
        initComponent();

        initToolbar();

        //SET LISTENER
        btnDetailProduk.setOnClickListener(this);
        btnDetailKategori.setOnClickListener(this);
        btnDetailTipe.setOnClickListener(this);
        btnDetailTotalTransaksi.setOnClickListener(this);
        btnDetailTransaksiTerakhir.setOnClickListener(this);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // Create first day of year
        Calendar firstDayOfCurrentYear = Calendar.getInstance();
        firstDayOfCurrentYear.set(Calendar.DATE, 1);
        firstDayOfCurrentYear.set(Calendar.MONTH, 0);

        // Create last day of year
        Calendar lastDayOfCurrentYear = Calendar.getInstance();
        lastDayOfCurrentYear.set(Calendar.DATE, 31);
        lastDayOfCurrentYear.set(Calendar.MONTH, 11);

        SweetAlertDialog pDialog = new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<Map> callTotalTransaksi = penjualanEndpoint.rangkumanPenjualanMobile(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        callTotalTransaksi.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                    DecimalFormat decim = new DecimalFormat("#,###.##");
                    totalTransaksi.setText(decim.format(Float.valueOf(String.valueOf(inner.get("jmlPenjualan")))));

                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        Call<List<PenjualanPerProdukItem>> callProdukTerlaris = penjualanEndpoint.rekapProduk(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        callProdukTerlaris.enqueue(new Callback<List<PenjualanPerProdukItem>>() {
            @Override
            public void onResponse(Call<List<PenjualanPerProdukItem>> call, Response<List<PenjualanPerProdukItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    DecimalFormat decim = new DecimalFormat("#,###.##");
                    //DATA PRODUK TERLARIS LIST
                    for (int i=0; i<response.body().size(); i++){

                        produkTerlarisItems.add(new ProdukTerlarisItem(
                                (i+1)+". "+ response.body().get(i).getNamaProduk()));
                    }

                    //SET ADAPTER PRODUK TERLARIS
                    produkTerlarisAdapter = new ProdukTerlarisAdapter(produkTerlarisItems, LaporanRangkumanActivity.this);
                    rvProdukTerlaris.setLayoutManager(new LinearLayoutManager(LaporanRangkumanActivity.this));
                    rvProdukTerlaris.setAdapter(produkTerlarisAdapter);
                    rvProdukTerlaris.setHasFixedSize(true);

                    //JIKA ADA PRODUK TERLARIS, TEXT EMPTY HILANG
                    if (produkTerlarisAdapter.getItemCount() > 0){
                        produkTerlarisEmpty.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<PenjualanPerProdukItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        Call<List<PenjualanPerKategoriItem>> callKategoriTerlaris = penjualanEndpoint.rekapKategoriTerlaris(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        callKategoriTerlaris.enqueue(new Callback<List<PenjualanPerKategoriItem>>() {
            @Override
            public void onResponse(Call<List<PenjualanPerKategoriItem>> call, Response<List<PenjualanPerKategoriItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    DecimalFormat decim = new DecimalFormat("#,###.##");
                    //DATA KATEGORI TERLARIS LIST
                    for (int i=0; i<response.body().size(); i++){

                        kategoriTerlarisItems.add(new KategoriTerlarisItem(
                                (i+1)+". "+ response.body().get(i).getNamaKategori()));
                    }

                    //SET ADAPTER KATEGORI TERLARIS
                    kategoriTerlarisAdapter = new KategoriTerlarisAdapter(kategoriTerlarisItems, LaporanRangkumanActivity.this);
                    rvKategoriTerlaris.setLayoutManager(new LinearLayoutManager(LaporanRangkumanActivity.this));
                    rvKategoriTerlaris.setAdapter(kategoriTerlarisAdapter);
                    rvKategoriTerlaris.setHasFixedSize(true);

                    //JIKA ADA KATEGORI TERLARIS, TEXT EMPTY HILANG
                    if (kategoriTerlarisAdapter.getItemCount() > 0){
                        kategoriTerlarisEmpty.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PenjualanPerKategoriItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        Call<List<PenjualanPerTipeItem>> callTipeTerlaris = penjualanEndpoint.rekapTipeTerlaris(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        callTipeTerlaris.enqueue(new Callback<List<PenjualanPerTipeItem>>() {
            @Override
            public void onResponse(Call<List<PenjualanPerTipeItem>> call, Response<List<PenjualanPerTipeItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    DecimalFormat decim = new DecimalFormat("#,###.##");
                    //DATA KATEGORI TERLARIS LIST
                    for (int i=0; i<response.body().size(); i++){

                        tipeTerlarisItems.add(new TipeTerlarisItem(
                                (i+1)+". "+ response.body().get(i).getNamaTipe()));
                    }

                    //SET ADAPTER TIPE TERLARIS
                    tipeTerlarisAdapter = new TipeTerlarisAdapter(tipeTerlarisItems, LaporanRangkumanActivity.this);
                    rvTipeTerlaris.setLayoutManager(new LinearLayoutManager(LaporanRangkumanActivity.this));
                    rvTipeTerlaris.setAdapter(tipeTerlarisAdapter);
                    rvTipeTerlaris.setHasFixedSize(true);

                    //JIKA ADA TIPE TERLARIS, TEXT EMPTY HILANG
                    if (tipeTerlarisAdapter.getItemCount() > 0){
                        tipeTerlarisEmpty.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PenjualanPerTipeItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        Call<List<TransaksiTerakhirItem>> callTransaksiTerakhir = penjualanEndpoint.subRiwayatTerakhir(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        callTransaksiTerakhir.enqueue(new Callback<List<TransaksiTerakhirItem>>() {
            @Override
            public void onResponse(Call<List<TransaksiTerakhirItem>> call, Response<List<TransaksiTerakhirItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    DecimalFormat decim = new DecimalFormat("#,###.##");
                    //DATA TRANSAKSI TERAKHIR LIST

                    for (int i=0; i<response.body().size(); i++){
                        transaksiTerakhirItems.add(new TransaksiTerakhirItem(
                                ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getNominalTransaksi()))),
                                response.body().get(i).getInvoiceTransaksi(),
                                response.body().get(i).getStatusTransaksi(),
                                response.body().get(i).getWaktuTransaksi()));
                    }
                    //SET ADAPTER TRANSAKSI TERAKHIR
                    transaksiTerakhirAdapter = new TransaksiTerakhirAdapter(transaksiTerakhirItems, LaporanRangkumanActivity.this);
                    rvTransaksiTerakhir.setLayoutManager(new LinearLayoutManager(LaporanRangkumanActivity.this));
                    rvTransaksiTerakhir.setAdapter(transaksiTerakhirAdapter);
                    rvTransaksiTerakhir.setHasFixedSize(true);

                    //JIKA ADA TRANSAKSI TERAKHIR, TEXT EMPTY HILANG
                    if (transaksiTerakhirAdapter.getItemCount() > 0){
                        transaksiEmpty.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiTerakhirItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        //Material Date Picker
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Pilih Periode");
        final MaterialDatePicker materialDatePicker = builder.build();

        btnPilihPeriode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long,Long> selection) {
                mSelectedPeriod.setText(materialDatePicker.getHeaderText()); //SET PICKER
                DateFrom = new SimpleDateFormat("yyyy-MM-dd").format(new Date(selection.first));
                DateTo = new SimpleDateFormat("yyyy-MM-dd").format(new Date(selection.second));

                SweetAlertDialog pDialog = new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();

                Call<Map> callTotalTransaksi = penjualanEndpoint.rangkumanPenjualanMobile(auth_token, id_store,DateFrom,DateTo);
                callTotalTransaksi.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                            DecimalFormat decim = new DecimalFormat("#,###.##");
                            totalTransaksi.setText(decim.format(Float.valueOf(String.valueOf(inner.get("jmlPenjualan")))));

                        }
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

                Call<List<PenjualanPerProdukItem>> callProdukTerlaris = penjualanEndpoint.rekapProduk(auth_token, id_store,DateFrom,DateTo);
                callProdukTerlaris.enqueue(new Callback<List<PenjualanPerProdukItem>>() {
                    @Override
                    public void onResponse(Call<List<PenjualanPerProdukItem>> call, Response<List<PenjualanPerProdukItem>> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            DecimalFormat decim = new DecimalFormat("#,###.##");
                            //DATA PRODUK TERLARIS LIST
                            for (int i=0; i<response.body().size(); i++){

                                produkTerlarisItems.add(new ProdukTerlarisItem(
                                        (i+1)+". "+ response.body().get(i).getNamaProduk()));
                            }

                            //SET ADAPTER PRODUK TERLARIS
                            produkTerlarisAdapter = new ProdukTerlarisAdapter(produkTerlarisItems, LaporanRangkumanActivity.this);
                            rvProdukTerlaris.setLayoutManager(new LinearLayoutManager(LaporanRangkumanActivity.this));
                            rvProdukTerlaris.setAdapter(produkTerlarisAdapter);
                            rvProdukTerlaris.setHasFixedSize(true);

                            //JIKA ADA PRODUK TERLARIS, TEXT EMPTY HILANG
                            if (produkTerlarisAdapter.getItemCount() > 0){
                                produkTerlarisEmpty.setVisibility(View.GONE);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<PenjualanPerProdukItem>> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

                Call<List<PenjualanPerKategoriItem>> callKategoriTerlaris = penjualanEndpoint.rekapKategoriTerlaris(auth_token, id_store,DateFrom,DateTo);
                callKategoriTerlaris.enqueue(new Callback<List<PenjualanPerKategoriItem>>() {
                    @Override
                    public void onResponse(Call<List<PenjualanPerKategoriItem>> call, Response<List<PenjualanPerKategoriItem>> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            DecimalFormat decim = new DecimalFormat("#,###.##");
                            //DATA KATEGORI TERLARIS LIST
                            for (int i=0; i<response.body().size(); i++){

                                kategoriTerlarisItems.add(new KategoriTerlarisItem(
                                        (i+1)+". "+ response.body().get(i).getNamaKategori()));
                            }

                            //SET ADAPTER KATEGORI TERLARIS
                            kategoriTerlarisAdapter = new KategoriTerlarisAdapter(kategoriTerlarisItems, LaporanRangkumanActivity.this);
                            rvKategoriTerlaris.setLayoutManager(new LinearLayoutManager(LaporanRangkumanActivity.this));
                            rvKategoriTerlaris.setAdapter(kategoriTerlarisAdapter);
                            rvKategoriTerlaris.setHasFixedSize(true);

                            //JIKA ADA KATEGORI TERLARIS, TEXT EMPTY HILANG
                            if (kategoriTerlarisAdapter.getItemCount() > 0){
                                kategoriTerlarisEmpty.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PenjualanPerKategoriItem>> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

                Call<List<PenjualanPerTipeItem>> callTipeTerlaris = penjualanEndpoint.rekapTipeTerlaris(auth_token, id_store,DateFrom,DateTo);
                callTipeTerlaris.enqueue(new Callback<List<PenjualanPerTipeItem>>() {
                    @Override
                    public void onResponse(Call<List<PenjualanPerTipeItem>> call, Response<List<PenjualanPerTipeItem>> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            DecimalFormat decim = new DecimalFormat("#,###.##");
                            //DATA KATEGORI TERLARIS LIST
                            for (int i=0; i<response.body().size(); i++){

                                tipeTerlarisItems.add(new TipeTerlarisItem(
                                        (i+1)+". "+ response.body().get(i).getNamaTipe()));
                            }

                            //SET ADAPTER TIPE TERLARIS
                            tipeTerlarisAdapter = new TipeTerlarisAdapter(tipeTerlarisItems, LaporanRangkumanActivity.this);
                            rvTipeTerlaris.setLayoutManager(new LinearLayoutManager(LaporanRangkumanActivity.this));
                            rvTipeTerlaris.setAdapter(tipeTerlarisAdapter);
                            rvTipeTerlaris.setHasFixedSize(true);

                            //JIKA ADA TIPE TERLARIS, TEXT EMPTY HILANG
                            if (tipeTerlarisAdapter.getItemCount() > 0){
                                tipeTerlarisEmpty.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PenjualanPerTipeItem>> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

                Call<List<TransaksiTerakhirItem>> callTransaksiTerakhir = penjualanEndpoint.subRiwayatTerakhir(auth_token, id_store,DateFrom,DateTo);
                callTransaksiTerakhir.enqueue(new Callback<List<TransaksiTerakhirItem>>() {
                    @Override
                    public void onResponse(Call<List<TransaksiTerakhirItem>> call, Response<List<TransaksiTerakhirItem>> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            DecimalFormat decim = new DecimalFormat("#,###.##");
                            //DATA TRANSAKSI TERAKHIR LIST

                            for (int i=0; i<response.body().size(); i++){
                                transaksiTerakhirItems.add(new TransaksiTerakhirItem(
                                        ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getNominalTransaksi()))),
                                        response.body().get(i).getInvoiceTransaksi(),
                                        response.body().get(i).getStatusTransaksi(),
                                        response.body().get(i).getWaktuTransaksi()));
                            }
                            //SET ADAPTER TRANSAKSI TERAKHIR
                            transaksiTerakhirAdapter = new TransaksiTerakhirAdapter(transaksiTerakhirItems, LaporanRangkumanActivity.this);
                            rvTransaksiTerakhir.setLayoutManager(new LinearLayoutManager(LaporanRangkumanActivity.this));
                            rvTransaksiTerakhir.setAdapter(transaksiTerakhirAdapter);
                            rvTransaksiTerakhir.setHasFixedSize(true);

                            //JIKA ADA TRANSAKSI TERAKHIR, TEXT EMPTY HILANG
                            if (transaksiTerakhirAdapter.getItemCount() > 0){
                                transaksiEmpty.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TransaksiTerakhirItem>> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(LaporanRangkumanActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

            }
        });

        //JIKA ADA PRODUK TERLARIS, TEXT EMPTY HILANG
        if (produkTerlarisItems.size() > 0){
            produkTerlarisEmpty.setVisibility(View.GONE);
        }

        //JIKA ADA KATEGORI TERLARIS, TEXT EMPTY HILANG
        if (kategoriTerlarisItems.size() > 0){
            kategoriTerlarisEmpty.setVisibility(View.GONE);
        }

        //JIKA ADA TIPE TERLARIS, TEXT EMPTY HILANG
        if (tipeTerlarisItems.size() > 0){
            tipeTerlarisEmpty.setVisibility(View.GONE);
        }

        //JIKA ADA TRANSAKSI TERAKHIR, TEXT EMPTY HILANG
        if (transaksiTerakhirItems.size() > 0){
            transaksiEmpty.setVisibility(View.GONE);
        }

    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_rangkuman_laporan);
        btnPilihPeriode = findViewById(R.id.btn_pilih_periode);
        mSelectedPeriod = findViewById(R.id.tv_selected_period);
        produkTerlarisEmpty = findViewById(R.id.tv_no_data_produk);
        kategoriTerlarisEmpty = findViewById(R.id.tv_no_data_kategori);
        tipeTerlarisEmpty = findViewById(R.id.tv_no_data_tipe);
        transaksiEmpty = findViewById(R.id.tv_no_data_transaksi);
        rvProdukTerlaris = findViewById(R.id.rv_produk_terlaris_rangkuman);
        rvKategoriTerlaris = findViewById(R.id.rv_kategori_terlaris_rangkuman);
        rvTipeTerlaris = findViewById(R.id.rv_tipe_terlaris_rangkuman);
        rvTransaksiTerakhir = findViewById(R.id.rv_transaksi_terakhir_rangkuman);
        btnDetailProduk = findViewById(R.id.btn_lihat_detail_produk);
        btnDetailKategori = findViewById(R.id.btn_lihat_detail_kategori);
        btnDetailTipe = findViewById(R.id.btn_lihat_detail_tipe);
        btnDetailTotalTransaksi = findViewById(R.id.btn_lihat_detail_total_transaksi);
        btnDetailTransaksiTerakhir = findViewById(R.id.btn_lihat_detail_transaksi_terakhir);
        totalTransaksi = findViewById(R.id.tv_total_transaksi_laporan);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_lihat_detail_produk:
                Intent detailProduk = new Intent(this, PenjualanPerProdukActivity.class);
                startActivity(detailProduk);
                break;
            case R.id.btn_lihat_detail_kategori:
                Intent detailKategori = new Intent(this, PenjualanPerKategoriActivity.class);
                startActivity(detailKategori);
                break;
            case R.id.btn_lihat_detail_tipe:
                Intent detailTipe = new Intent(this, PenjualanPerTipeActivity.class);
                startActivity(detailTipe);
                break;
            case R.id.btn_lihat_detail_total_transaksi:
            case R.id.btn_lihat_detail_transaksi_terakhir:
                Intent detailRiwayatTransaksi = new Intent(this, DetailRiwayatTransaksiActivity.class);
                startActivity(detailRiwayatTransaksi);
                break;
        }
    }
}