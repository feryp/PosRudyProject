package com.example.posrudyproject.ui.laporan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualanEndpoint;
import com.example.posrudyproject.ui.laporan.adapter.RiwayatTransaksiPelangganAdapter;
import com.example.posrudyproject.ui.laporan.model.LaporanPelangganItem;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiPelangganItem;
import com.example.posrudyproject.ui.laporan.model.SubRiwayatTransaksiPelangganItem;
import com.example.posrudyproject.ui.pesananTunggu.adapter.PesananTungguAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLaporanPelangganActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvRiwayatTransaksi;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;
    AppCompatTextView tvNamaPelanggan,tvTotalPoin,tvNoHpPelanggan;
    PenjualanEndpoint penjualanEndpoint;
    RiwayatTransaksiPelangganAdapter adapter;
    int id_store;
    String auth_token,DateFrom,DateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan_pelanggan);
        penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        auth_token = ("Bearer ").concat(token);
        id_store = preferences.getInt("id_store", 0);
        tvNamaPelanggan=findViewById(R.id.tv_nama_pelanggan_detail_laporan);
        tvTotalPoin=findViewById(R.id.tv_total_poin_pelanggan_detail_laporan);
        tvNoHpPelanggan=findViewById(R.id.tv_no_hp_pelanggan_detail_laporan);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvNamaPelanggan.setText(bundle.getString("namaPelanggan"));
            tvNoHpPelanggan.setText(bundle.getString("noHpPelanggan"));
            Call<Double> call = penjualanEndpoint.totalPoin(auth_token,bundle.getString("namaPelanggan"), bundle.getString("noHpPelanggan"));
            SweetAlertDialog pDialog = new SweetAlertDialog(DetailLaporanPelangganActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            call.enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    if (!response.isSuccessful()){
                        pDialog.dismiss();
                        new SweetAlertDialog(DetailLaporanPelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(String.valueOf(response.code()))
                                .setContentText(response.message())
                                .show();
                    } else {
                        pDialog.dismiss();
                        tvTotalPoin.setText(String.valueOf(response.body()));
                    }
                }

                @Override
                public void onFailure(Call<Double> call, Throwable t) {
                    pDialog.dismiss();
                    new SweetAlertDialog(DetailLaporanPelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(t.getMessage())
                            .show();
                }
            });

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            // Create first day of year
            Calendar firstDayOfCurrentYear = Calendar.getInstance();
            firstDayOfCurrentYear.set(Calendar.DATE, 1);
            firstDayOfCurrentYear.set(Calendar.MONTH, 0);

            // Create last day of year
            Calendar lastDayOfCurrentYear = Calendar.getInstance();
            lastDayOfCurrentYear.set(Calendar.DATE, 31);
            lastDayOfCurrentYear.set(Calendar.MONTH, 11);

            Call<List<RiwayatTransaksiPelangganItem>> callPelanggan = penjualanEndpoint.rekapPelangganPerTanggal(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()),bundle.getString("noHpPelanggan",""));

            callPelanggan.enqueue(new Callback<List<RiwayatTransaksiPelangganItem>>() {
                @Override
                public void onResponse(Call<List<RiwayatTransaksiPelangganItem>> call, Response<List<RiwayatTransaksiPelangganItem>> response) {
                    if (!response.isSuccessful()){
                        pDialog.dismiss();
                        new SweetAlertDialog(DetailLaporanPelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(String.valueOf(response.code()))
                                .setContentText(response.message())
                                .show();
                    } else {
                        pDialog.dismiss();
                        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                        df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS

                        List<RiwayatTransaksiPelangganItem> riwayatTransaksiPelangganItems = new ArrayList<>();

                        for (int i=0; i<response.body().size(); i++){
                            riwayatTransaksiPelangganItems.add(new RiwayatTransaksiPelangganItem(
                                    response.body().get(i).getTglTransaksi(),
                                    ("Rp").concat(df.format(Double.valueOf(response.body().get(i).getTotalTransaksi()))),
                                    response.body().get(i).getSubRiwayatTransaksiPelangganItems()
                            ));
                        }

                        // Removes blinks
                        ((SimpleItemAnimator) rvRiwayatTransaksi.getItemAnimator()).setSupportsChangeAnimations(false);

                        //Setup Adapter
                        adapter = new RiwayatTransaksiPelangganAdapter(riwayatTransaksiPelangganItems, DetailLaporanPelangganActivity.this);
                        rvRiwayatTransaksi.setLayoutManager(new LinearLayoutManager(DetailLaporanPelangganActivity.this));
                        rvRiwayatTransaksi.setAdapter(adapter);
                        rvRiwayatTransaksi.setHasFixedSize(true);
                    }
                }

                @Override
                public void onFailure(Call<List<RiwayatTransaksiPelangganItem>> call, Throwable t) {
                    pDialog.dismiss();
                    new SweetAlertDialog(DetailLaporanPelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(t.getMessage())
                            .show();
                }
            });
        }
        //INIT VIEW
        initComponent();

        initToolbar();

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

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long,Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long,Long> selection) {
                mSelectedPeriod.setText(materialDatePicker.getHeaderText()); //SET PICKER
                DateFrom = new SimpleDateFormat("yyyy-MM-dd").format(new Date(selection.first));
                DateTo = new SimpleDateFormat("yyyy-MM-dd").format(new Date(selection.second));

                SweetAlertDialog pDialog = new SweetAlertDialog(DetailLaporanPelangganActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();

                Call<List<RiwayatTransaksiPelangganItem>> callPelanggan = penjualanEndpoint.rekapPelangganPerTanggal(auth_token, id_store,DateFrom,DateTo,bundle.getString("noHpPelanggan",""));

                callPelanggan.enqueue(new Callback<List<RiwayatTransaksiPelangganItem>>() {
                    @Override
                    public void onResponse(Call<List<RiwayatTransaksiPelangganItem>> call, Response<List<RiwayatTransaksiPelangganItem>> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(DetailLaporanPelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                            df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS

                            List<RiwayatTransaksiPelangganItem> riwayatTransaksiPelangganItems = new ArrayList<>();

                            for (int i=0; i<response.body().size(); i++){
                                    riwayatTransaksiPelangganItems.add(new RiwayatTransaksiPelangganItem(
                                            response.body().get(i).getTglTransaksi(),
                                            ("Rp").concat(df.format(Double.valueOf(response.body().get(i).getTotalTransaksi()))),
                                            response.body().get(i).getSubRiwayatTransaksiPelangganItems()
                                    ));
                            }

                            // Removes blinks
                            ((SimpleItemAnimator) rvRiwayatTransaksi.getItemAnimator()).setSupportsChangeAnimations(false);

                            //Setup Adapter
                            adapter = new RiwayatTransaksiPelangganAdapter(riwayatTransaksiPelangganItems, DetailLaporanPelangganActivity.this);
                            rvRiwayatTransaksi.setLayoutManager(new LinearLayoutManager(DetailLaporanPelangganActivity.this));
                            rvRiwayatTransaksi.setAdapter(adapter);
                            rvRiwayatTransaksi.setHasFixedSize(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RiwayatTransaksiPelangganItem>> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(DetailLaporanPelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });


            }
        });


    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_detail_pelanggan_laporan);
        btnPilihPeriode = findViewById(R.id.btn_pilih_periode);
        mSelectedPeriod = findViewById(R.id.tv_selected_period);
        rvRiwayatTransaksi = findViewById(R.id.rv_riwayat_transaksi_pelanggan_laporan);

    }

}