package com.example.posrudyproject.ui.laporan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
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
import com.example.posrudyproject.ui.laporan.adapter.RiwayatTransaksiAdapter;
import com.example.posrudyproject.ui.laporan.adapter.RiwayatTransaksiPelangganAdapter;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiItem;
import com.example.posrudyproject.ui.laporan.model.SubRiwayatTransaksiItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRiwayatTransaksiActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvRiwayatTransaksi;
    AppCompatTextView tvTotalTerjual, tvTotalPenjualan;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;
    PenjualanEndpoint penjualanEndpoint;
    RiwayatTransaksiAdapter adapter;
    List<RiwayatTransaksiItem> riwayatTransaksiItems;
    int id_store;
    String auth_token,DateFrom,DateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat_transaksi);
        penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        auth_token = ("Bearer ").concat(token);
        id_store = preferences.getInt("id_store", 0);
        riwayatTransaksiItems = new ArrayList<>();
        //INIT VIEW
        initComponent();

        initToolbar();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // Create first day of year
        Calendar firstDayOfCurrentYear = Calendar.getInstance();
        firstDayOfCurrentYear.set(Calendar.DATE, 1);
        firstDayOfCurrentYear.set(Calendar.MONTH, 0);

        // Create last day of year
        Calendar lastDayOfCurrentYear = Calendar.getInstance();
        lastDayOfCurrentYear.set(Calendar.DATE, 31);
        lastDayOfCurrentYear.set(Calendar.MONTH, 11);

        SweetAlertDialog pDialog = new SweetAlertDialog(DetailRiwayatTransaksiActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<Map> call = penjualanEndpoint.rangkumanPenjualanMobile(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(DetailRiwayatTransaksiActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                    DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                    df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                    tvTotalTerjual.setText(df.format(Double.valueOf(String.valueOf(inner.get("jmlPenjualan")))));
                    tvTotalPenjualan.setText(("Rp").concat(df.format(Double.valueOf(String.valueOf(inner.get("omset"))))));

                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(DetailRiwayatTransaksiActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        Call<List<RiwayatTransaksiItem>> callTransaksi = penjualanEndpoint.riwayatPertanggal(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        callTransaksi.enqueue(new Callback<List<RiwayatTransaksiItem>>() {
            @Override
            public void onResponse(Call<List<RiwayatTransaksiItem>> call, Response<List<RiwayatTransaksiItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(DetailRiwayatTransaksiActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();

                    DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                    df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                    for (int i=0; i<response.body().size(); i++){
                        riwayatTransaksiItems.add(new RiwayatTransaksiItem(
                                response.body().get(i).getTglTransaksi(),
                                ("Rp").concat(df.format(Double.valueOf(response.body().get(i).getTotalTransaksi()))),
                                response.body().get(i).getSubRiwayatTransaksiItems()

                        ));
                    }

                    // Removes blinks
                    ((SimpleItemAnimator) rvRiwayatTransaksi.getItemAnimator()).setSupportsChangeAnimations(false);

                    //Setup Adapter
                    adapter = new RiwayatTransaksiAdapter(riwayatTransaksiItems, DetailRiwayatTransaksiActivity.this);
                    rvRiwayatTransaksi.setLayoutManager(new LinearLayoutManager(DetailRiwayatTransaksiActivity.this));
                    rvRiwayatTransaksi.setAdapter(adapter);
                    rvRiwayatTransaksi.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<List<RiwayatTransaksiItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(DetailRiwayatTransaksiActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                SweetAlertDialog pDialog = new SweetAlertDialog(DetailRiwayatTransaksiActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();

                Call<Map> call = penjualanEndpoint.rangkumanPenjualanMobile(auth_token, id_store,DateFrom,DateTo);
                call.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(DetailRiwayatTransaksiActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                            DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                            df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                            tvTotalTerjual.setText(df.format(Double.valueOf(String.valueOf(inner.get("jmlPenjualan")))));
                            tvTotalPenjualan.setText(("Rp").concat(df.format(Double.valueOf(String.valueOf(inner.get("omset"))))));

                        }
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(DetailRiwayatTransaksiActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

                Call<List<RiwayatTransaksiItem>> callTransaksi = penjualanEndpoint.riwayatPertanggal(auth_token, id_store,DateFrom,DateTo);
                callTransaksi.enqueue(new Callback<List<RiwayatTransaksiItem>>() {
                    @Override
                    public void onResponse(Call<List<RiwayatTransaksiItem>> call, Response<List<RiwayatTransaksiItem>> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(DetailRiwayatTransaksiActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();

                            DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                            df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                            for (int i=0; i<response.body().size(); i++){
                                riwayatTransaksiItems.add(new RiwayatTransaksiItem(
                                        response.body().get(i).getTglTransaksi(),
                                        ("Rp").concat(df.format(Double.valueOf(response.body().get(i).getTotalTransaksi()))),
                                        response.body().get(i).getSubRiwayatTransaksiItems()

                                ));
                            }

                            // Removes blinks
                            ((SimpleItemAnimator) rvRiwayatTransaksi.getItemAnimator()).setSupportsChangeAnimations(false);

                            //Setup Adapter
                            adapter = new RiwayatTransaksiAdapter(riwayatTransaksiItems, DetailRiwayatTransaksiActivity.this);
                            rvRiwayatTransaksi.setLayoutManager(new LinearLayoutManager(DetailRiwayatTransaksiActivity.this));
                            rvRiwayatTransaksi.setAdapter(adapter);
                            rvRiwayatTransaksi.setHasFixedSize(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RiwayatTransaksiItem>> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(DetailRiwayatTransaksiActivity.this, SweetAlertDialog.ERROR_TYPE)
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
        mToolbar = findViewById(R.id.toolbar_detail_riwayat_transaksi_laporan);
        btnPilihPeriode = findViewById(R.id.btn_pilih_periode);
        mSelectedPeriod = findViewById(R.id.tv_selected_period);
        rvRiwayatTransaksi = findViewById(R.id.rv_riwayat_transaksi_laporan);
        tvTotalTerjual = findViewById(R.id.tv_total_transaksi_detail_laporan);
        tvTotalPenjualan = findViewById(R.id.tv_total_transaksi_penjual_detail_laporan);
    }

}