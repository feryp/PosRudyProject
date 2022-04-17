package com.example.posrudyproject.ui.laporan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualanEndpoint;
import com.example.posrudyproject.ui.laporan.adapter.RiwayatTransaksiPenjualAdapter;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiPenjualItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLaporanPenjualActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvRiwayatTransaksi;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;
    PenjualanEndpoint penjualanEndpoint;
    List<RiwayatTransaksiPenjualItem> items;
    RiwayatTransaksiPenjualAdapter adapter;
    AppCompatTextView namaPenjual,totalTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan_penjual);
        Bundle extras = getIntent().getExtras();
        items = new ArrayList<>();
        penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);
        namaPenjual = findViewById(R.id.tv_nama_penjual_detail_laporan);
        totalTransaksi = findViewById(R.id.tv_total_transaksi_penjual_detail_laporan);
        //INIT VIEW
        initComponent();

        initToolbar();

        //Material Date Picker
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Pilih Periode");
        final MaterialDatePicker materialDatePicker = builder.build();

        if (extras != null) {
            // Removes blinks
            ((SimpleItemAnimator) rvRiwayatTransaksi.getItemAnimator()).setSupportsChangeAnimations(false);
            namaPenjual.setText(extras.getString("nama_karyawan"));
            totalTransaksi.setText(extras.getString("tota_transaksi"));

            //DATA RIWAYAT TRANSAKSI PENJUAL
            Call<List<RiwayatTransaksiPenjualItem>> call = penjualanEndpoint.findByKaryawanId(extras.getString("auth_token"),extras.getInt("id_store"),extras.getInt("id_karyawan"),extras.getString("StartDate"),extras.getString("EndDate"));
            SweetAlertDialog pDialog = new SweetAlertDialog(DetailLaporanPenjualActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            call.enqueue(new Callback<List<RiwayatTransaksiPenjualItem>>() {
                @Override
                public void onResponse(Call<List<RiwayatTransaksiPenjualItem>> call, Response<List<RiwayatTransaksiPenjualItem>> response) {
                    if (!response.isSuccessful()){
                        pDialog.dismiss();
                        new SweetAlertDialog(DetailLaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(String.valueOf(response.code()))
                                .setContentText(response.message())
                                .show();
                    } else {
                        pDialog.dismiss();
                        DecimalFormat decim = new DecimalFormat("#,###.##");
                        for (int i=0; i<response.body().size(); i++){
                            items.add(new RiwayatTransaksiPenjualItem(
                                    response.body().get(i).getWaktuTransaksi(),
                                    ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getNominalTransaksi()))),
                                    response.body().get(i).getNamaToko(),
                                    response.body().get(i).getProdukTerjual()
                            ));
                        }

                        //SET ADAPTER
                        adapter = new RiwayatTransaksiPenjualAdapter(items, DetailLaporanPenjualActivity.this);
                        rvRiwayatTransaksi.setLayoutManager(new LinearLayoutManager(DetailLaporanPenjualActivity.this));
                        rvRiwayatTransaksi.setAdapter(adapter);
                        rvRiwayatTransaksi.setHasFixedSize(true);

                    }
                }

                @Override
                public void onFailure(Call<List<RiwayatTransaksiPenjualItem>> call, Throwable t) {
                    pDialog.dismiss();
                    new SweetAlertDialog(DetailLaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(t.getMessage())
                            .show();
                }
            });

        }

        btnPilihPeriode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long,Long> selection) {
                Long startDate = selection.first;
                Long endDate = selection.second;
                mSelectedPeriod.setText(materialDatePicker.getHeaderText()); //SET PICKER
                if (extras != null) {
                    // Removes blinks
                    ((SimpleItemAnimator) rvRiwayatTransaksi.getItemAnimator()).setSupportsChangeAnimations(false);

                    //DATA RIWAYAT TRANSAKSI PENJUAL
                    Call<List<RiwayatTransaksiPenjualItem>> call = penjualanEndpoint.findByKaryawanId(extras.getString("auth_token"),extras.getInt("id_store"),extras.getInt("id_karyawan"),new SimpleDateFormat("yyyy-MM-dd").format(new Date(startDate)),new SimpleDateFormat("yyyy-MM-dd").format(new Date(endDate)));
                    SweetAlertDialog pDialog = new SweetAlertDialog(DetailLaporanPenjualActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading ...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    call.enqueue(new Callback<List<RiwayatTransaksiPenjualItem>>() {
                        @Override
                        public void onResponse(Call<List<RiwayatTransaksiPenjualItem>> call, Response<List<RiwayatTransaksiPenjualItem>> response) {
                            if (!response.isSuccessful()){
                                pDialog.dismiss();
                                new SweetAlertDialog(DetailLaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText(String.valueOf(response.code()))
                                        .setContentText(response.message())
                                        .show();
                            } else {
                                pDialog.dismiss();
                                DecimalFormat decim = new DecimalFormat("#,###.##");
                                for (int i=0; i<response.body().size(); i++){
                                    items.add(new RiwayatTransaksiPenjualItem(
                                            response.body().get(i).getWaktuTransaksi(),
                                            ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getNominalTransaksi()))),
                                            response.body().get(i).getNamaToko(),
                                            response.body().get(i).getProdukTerjual()
                                    ));
                                }

                                //SET ADAPTER
                                adapter = new RiwayatTransaksiPenjualAdapter(items, DetailLaporanPenjualActivity.this);
                                rvRiwayatTransaksi.setLayoutManager(new LinearLayoutManager(DetailLaporanPenjualActivity.this));
                                rvRiwayatTransaksi.setAdapter(adapter);
                                rvRiwayatTransaksi.setHasFixedSize(true);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<RiwayatTransaksiPenjualItem>> call, Throwable t) {
                            pDialog.dismiss();
                            new SweetAlertDialog(DetailLaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });

                }
            }
        });

    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_detail_penjual_laporan);
        btnPilihPeriode = findViewById(R.id.btn_pilih_periode);
        mSelectedPeriod = findViewById(R.id.tv_selected_period);
        rvRiwayatTransaksi = findViewById(R.id.rv_riwayat_transaksi_penjual_laporan);
    }
}