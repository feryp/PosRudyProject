package com.example.posrudyproject.ui.laporan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualanEndpoint;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterPelangganFragment;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterPenjualFragment;
import com.example.posrudyproject.ui.laporan.adapter.LaporanPelangganAdapter;
import com.example.posrudyproject.ui.laporan.adapter.LaporanPenjualAdapter;
import com.example.posrudyproject.ui.laporan.model.LaporanPelangganItem;
import com.example.posrudyproject.ui.laporan.model.LaporanPenjualItem;
import com.example.posrudyproject.ui.notifikasi.activity.NotifikasiActivity;
import com.example.posrudyproject.ui.penyimpanan.model.BarangMasukItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.internal.LinkedTreeMap;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporanPenjualActivity extends AppCompatActivity implements OnItemClickListener {

    MaterialToolbar mToolbar;
    RecyclerView rvLaporanPenjual;
    MaterialButton btnEkspor;
    ConstraintLayout layoutEmpty;
    LinearLayoutCompat layoutLaporan;
    AppCompatTextView tv_total_omset_penjual,tv_jumlah_transaksi_penjual;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;
    public static final String INTENT_FILTER_PENJUAL = "INTENT_FILTER_PENJUAL";
    PenjualanEndpoint penjualanEndpoint;
    List<LaporanPenjualItem> items;
    LaporanPenjualAdapter adapter;
    int id_store;
    String auth_token,DateFrom,DateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_penjual);
        penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        auth_token = ("Bearer ").concat(token);
        id_store = preferences.getInt("id_store", 0);
        items = new ArrayList<>();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // Create first day of year
        Calendar firstDayOfCurrentYear = Calendar.getInstance();
        firstDayOfCurrentYear.set(Calendar.DATE, 1);
        firstDayOfCurrentYear.set(Calendar.MONTH, 0);

        // Create last day of year
        Calendar lastDayOfCurrentYear = Calendar.getInstance();
        lastDayOfCurrentYear.set(Calendar.DATE, 31);
        lastDayOfCurrentYear.set(Calendar.MONTH, 11);
        //INIT VIEW
        initComponent();

        initToolbar();

        SweetAlertDialog pDialog = new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<Map> call = penjualanEndpoint.rangkumanPenjualanMobile(auth_token, id_store, df.format(firstDayOfCurrentYear.getTime()), df.format(lastDayOfCurrentYear.getTime()));
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (!response.isSuccessful()) {
                    pDialog.dismiss();
                    new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                    DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                    df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                    tv_jumlah_transaksi_penjual.setText(df.format(Double.valueOf(String.valueOf(inner.get("jmlPenjualan")))));
                    tv_total_omset_penjual.setText(("Rp").concat(df.format(Double.valueOf(String.valueOf(inner.get("omset"))))));

                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        Bundle bundle = getIntent().getExtras();
        Call<List<LaporanPenjualItem>> callPenjual = penjualanEndpoint.rekapKaryawan(auth_token, id_store, df.format(firstDayOfCurrentYear.getTime()), df.format(lastDayOfCurrentYear.getTime()));
        if (bundle != null) {
            callPenjual.enqueue(new Callback<List<LaporanPenjualItem>>() {
                @Override
                public void onResponse(Call<List<LaporanPenjualItem>> call, Response<List<LaporanPenjualItem>> response) {
                    if (!response.isSuccessful()) {
                        pDialog.dismiss();
                        new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(String.valueOf(response.code()))
                                .setContentText(response.message())
                                .show();
                    } else {
                        pDialog.dismiss();
                        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                        df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                        //DATA LAPORAN PENJUAL LIST
                        items = new ArrayList<>();
                        for (int i = 0; i < response.body().size(); i++) {
                            if (response.body().get(i).getId_karyawan() == bundle.getInt("idPenjual")) {
                                items.add(new LaporanPenjualItem(
                                        response.body().get(i).getId_karyawan(),
                                        response.body().get(i).getNamaPenjual(),
                                        ("Rp").concat(df.format(Double.valueOf(response.body().get(i).getNominalTransaksiPenjual()))),
                                        response.body().get(i).getTotalTransaksiPenjual()));
                            }
                        }
                        // Removes blinks
                        ((SimpleItemAnimator) rvLaporanPenjual.getItemAnimator()).setSupportsChangeAnimations(false);

                        //SET ADAPTER
                        adapter = new LaporanPenjualAdapter(items, LaporanPenjualActivity.this);
                        rvLaporanPenjual.setLayoutManager(new LinearLayoutManager(LaporanPenjualActivity.this));
                        rvLaporanPenjual.setAdapter(adapter);
                        rvLaporanPenjual.setHasFixedSize(true);

                        //Jika ada list item ilustrasi hilang
                        if (adapter.getItemCount() > 0) {
                            layoutEmpty.setVisibility(View.GONE);
                            layoutLaporan.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<LaporanPenjualItem>> call, Throwable t) {
                    pDialog.dismiss();
                    new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(t.getMessage())
                            .show();
                }
            });
        } else {
            callPenjual.enqueue(new Callback<List<LaporanPenjualItem>>() {
                @Override
                public void onResponse(Call<List<LaporanPenjualItem>> call, Response<List<LaporanPenjualItem>> response) {
                    if (!response.isSuccessful()) {
                        pDialog.dismiss();
                        new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(String.valueOf(response.code()))
                                .setContentText(response.message())
                                .show();
                    } else {
                        pDialog.dismiss();
                        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                        df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                        //DATA LAPORAN PENJUAL LIST
                        items = new ArrayList<>();
                        for (int i = 0; i < response.body().size(); i++) {
                            items.add(new LaporanPenjualItem(
                                    response.body().get(i).getId_karyawan(),
                                    response.body().get(i).getNamaPenjual(),
                                    ("Rp").concat(df.format(Double.valueOf(response.body().get(i).getNominalTransaksiPenjual()))),
                                    response.body().get(i).getTotalTransaksiPenjual()));
                        }
                        // Removes blinks
                        ((SimpleItemAnimator) rvLaporanPenjual.getItemAnimator()).setSupportsChangeAnimations(false);

                        //SET ADAPTER
                        adapter = new LaporanPenjualAdapter(items, LaporanPenjualActivity.this);
                        rvLaporanPenjual.setLayoutManager(new LinearLayoutManager(LaporanPenjualActivity.this));
                        rvLaporanPenjual.setAdapter(adapter);
                        rvLaporanPenjual.setHasFixedSize(true);

                        //Jika ada list item ilustrasi hilang
                        if (adapter.getItemCount() > 0) {
                            layoutEmpty.setVisibility(View.GONE);
                            layoutLaporan.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<LaporanPenjualItem>> call, Throwable t) {
                    pDialog.dismiss();
                    new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(t.getMessage())
                            .show();
                }
            });
        }
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
            @Override public void onPositiveButtonClick(Pair<Long,Long> selection) {
                Long startDate = selection.first;
                Long endDate = selection.second;

                mSelectedPeriod.setText(materialDatePicker.getHeaderText()); //SET PICKER

                DateFrom = new SimpleDateFormat("yyyy-MM-dd").format(new Date(startDate));
                DateTo = new SimpleDateFormat("yyyy-MM-dd").format(new Date(endDate));

                SweetAlertDialog pDialog = new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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
                            new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                            DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                            df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                            tv_jumlah_transaksi_penjual.setText(df.format(Double.valueOf(String.valueOf(inner.get("jmlPenjualan")))));
                            tv_total_omset_penjual.setText(("Rp").concat(df.format(Double.valueOf(String.valueOf(inner.get("omset"))))));

                        }
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

                Call<List<LaporanPenjualItem>> callPenjual = penjualanEndpoint.rekapKaryawan(auth_token, id_store,DateFrom,DateTo);
                callPenjual.enqueue(new Callback<List<LaporanPenjualItem>>() {
                    @Override
                    public void onResponse(Call<List<LaporanPenjualItem>> call, Response<List<LaporanPenjualItem>> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                            df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                            //DATA LAPORAN PENJUAL LIST
                            items = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                items.add(new LaporanPenjualItem(
                                        response.body().get(i).getId_karyawan(),
                                        response.body().get(i).getNamaPenjual(),
                                        ("Rp").concat(df.format(Double.valueOf(response.body().get(i).getNominalTransaksiPenjual()))),
                                        response.body().get(i).getTotalTransaksiPenjual()));
                            }
                            // Removes blinks
                            ((SimpleItemAnimator) rvLaporanPenjual.getItemAnimator()).setSupportsChangeAnimations(false);

                            //SET ADAPTER
                            adapter = new LaporanPenjualAdapter(items, LaporanPenjualActivity.this);
                            rvLaporanPenjual.setLayoutManager(new LinearLayoutManager(LaporanPenjualActivity.this));
                            rvLaporanPenjual.setAdapter(adapter);
                            rvLaporanPenjual.setHasFixedSize(true);

                            //Jika ada list item ilustrasi hilang
                            if (adapter.getItemCount() > 0){
                                layoutEmpty.setVisibility(View.GONE);
                                layoutLaporan.setVisibility(View.VISIBLE);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<LaporanPenjualItem>> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });
            }
        });
        //Jika ada list item ilustrasi hilang

        if (items.size() > 0){
            layoutEmpty.setVisibility(View.GONE);
            layoutLaporan.setVisibility(View.VISIBLE);
        }
    }

    private BroadcastReceiver someBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO extract extras from intent

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            // Create first day of year
            Calendar firstDayOfCurrentYear = Calendar.getInstance();
            firstDayOfCurrentYear.set(Calendar.DATE, 1);
            firstDayOfCurrentYear.set(Calendar.MONTH, 0);

            // Create last day of year
            Calendar lastDayOfCurrentYear = Calendar.getInstance();
            lastDayOfCurrentYear.set(Calendar.DATE, 31);
            lastDayOfCurrentYear.set(Calendar.MONTH, 11);

            String StartDate = DateFrom == null ? df.format(firstDayOfCurrentYear.getTime()) : DateFrom;
            String EndDate = DateTo == null ? df.format(lastDayOfCurrentYear.getTime()) : DateTo;

            SweetAlertDialog pDialog = new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();

            Call<Map> call = penjualanEndpoint.rangkumanPenjualanMobile(auth_token, id_store,StartDate,EndDate);
            call.enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    if (!response.isSuccessful()){
                        pDialog.dismiss();
                        new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(String.valueOf(response.code()))
                                .setContentText(response.message())
                                .show();
                    } else {
                        pDialog.dismiss();
                        Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                        df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                        tv_jumlah_transaksi_penjual.setText(df.format(Double.valueOf(String.valueOf(inner.get("jmlPenjualan")))));
                        tv_total_omset_penjual.setText(("Rp").concat(df.format(Double.valueOf(String.valueOf(inner.get("omset"))))));

                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    pDialog.dismiss();
                    new SweetAlertDialog(LaporanPenjualActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(t.getMessage())
                            .show();
                }
            });

            if (intent.getIntExtra("OrderBY",0) == 1) {
                Collections.sort(items, new Comparator<LaporanPenjualItem>() {
                    @Override
                    public int compare(LaporanPenjualItem laporanPenjualItem, LaporanPenjualItem t1) {
                        return t1.getNominalTransaksiPenjual().compareToIgnoreCase(laporanPenjualItem.getNominalTransaksiPenjual());

                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvLaporanPenjual.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new LaporanPenjualAdapter(items, LaporanPenjualActivity.this);
                rvLaporanPenjual.setLayoutManager(new LinearLayoutManager(LaporanPenjualActivity.this));
                rvLaporanPenjual.setAdapter(adapter);
                rvLaporanPenjual.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutLaporan.setVisibility(View.VISIBLE);
                }
            } else if (intent.getIntExtra("OrderBY",0) == 2) {
                Collections.sort(items, new Comparator<LaporanPenjualItem>() {
                    @Override
                    public int compare(LaporanPenjualItem laporanPelangganItem, LaporanPenjualItem t1) {
                        return laporanPelangganItem.getNominalTransaksiPenjual().compareToIgnoreCase(t1.getNominalTransaksiPenjual());
                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvLaporanPenjual.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new LaporanPenjualAdapter(items, LaporanPenjualActivity.this);
                rvLaporanPenjual.setLayoutManager(new LinearLayoutManager(LaporanPenjualActivity.this));
                rvLaporanPenjual.setAdapter(adapter);
                rvLaporanPenjual.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutLaporan.setVisibility(View.VISIBLE);
                }
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(someBroadcastReceiver,
                new IntentFilter(INTENT_FILTER_PENJUAL));
    }
    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(someBroadcastReceiver);
        super.onPause();
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_filter){
                Bundle bundle = new Bundle();
                bundle.putString("StartDate", DateFrom);
                bundle.putString("EndDate", DateTo);
                BotSheetFilterPenjualFragment botSheetPenjual = new BotSheetFilterPenjualFragment();
                botSheetPenjual.setArguments(bundle);
                botSheetPenjual.setCancelable(false);
                botSheetPenjual.show(getSupportFragmentManager(), botSheetPenjual.getTag());
                return true;
            }

            return false;
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_penjual_laporan);
        btnPilihPeriode = findViewById(R.id.btn_pilih_periode);
        mSelectedPeriod = findViewById(R.id.tv_selected_period);
        rvLaporanPenjual = findViewById(R.id.rv_penjual_laporan);
        btnEkspor = findViewById(R.id.btn_ekspor_laporan_penjual);
        layoutLaporan = findViewById(R.id.layout_laporan_penjual);
        layoutEmpty = findViewById(R.id.layout_ilustrasi_empty_laporan_penjual);
        tv_total_omset_penjual = findViewById(R.id.tv_total_omset_penjual);
        tv_jumlah_transaksi_penjual = findViewById(R.id.tv_jumlah_transaksi_penjual);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // Create first day of year
        Calendar firstDayOfCurrentYear = Calendar.getInstance();
        firstDayOfCurrentYear.set(Calendar.DATE, 1);
        firstDayOfCurrentYear.set(Calendar.MONTH, 0);
        System.out.println(df.format(firstDayOfCurrentYear.getTime()));

        // Create last day of year
        Calendar lastDayOfCurrentYear = Calendar.getInstance();
        lastDayOfCurrentYear.set(Calendar.DATE, 31);
        lastDayOfCurrentYear.set(Calendar.MONTH, 11);
        System.out.println(df.format(lastDayOfCurrentYear.getTime()));

        Intent detailLaporan = new Intent(this, DetailLaporanPenjualActivity.class);
        detailLaporan.putExtra("id_store",id_store);
        detailLaporan.putExtra("auth_token",auth_token);
        detailLaporan.putExtra("id_karyawan",items.get(position).getId_karyawan());
        detailLaporan.putExtra("nama_karyawan",items.get(position).getNamaPenjual());
        detailLaporan.putExtra("StartDate",DateFrom == null ? df.format(firstDayOfCurrentYear.getTime()) : DateFrom);
        detailLaporan.putExtra("EndDate",DateTo == null ? df.format(lastDayOfCurrentYear.getTime()) : DateFrom);
        detailLaporan.putExtra("tota_transaksi",items.get(position).getNominalTransaksiPenjual());
        startActivity(detailLaporan);
    }
}