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
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterPenjualanProdukFragment;
import com.example.posrudyproject.ui.laporan.adapter.LaporanPelangganAdapter;
import com.example.posrudyproject.ui.laporan.adapter.PenjualanPerArtikelAdapter;
import com.example.posrudyproject.ui.laporan.model.LaporanPelangganItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerArtikelItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporanPelangganActivity extends AppCompatActivity implements OnItemClickListener {

    MaterialToolbar mToolbar;
    RecyclerView rvLaporanPelanggan;
    MaterialButton btnEkspor;
    ConstraintLayout layoutEmpty;
    LinearLayoutCompat layoutLaporan;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;
    public static final String INTENT_FILTER_PELANGGAN = "INTENT_FILTER_PELANGGAN";
    PenjualanEndpoint penjualanEndpoint;
    List<LaporanPelangganItem> items;
    LaporanPelangganAdapter adapter;
    int id_store;
    String auth_token,DateFrom,DateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_pelanggan);
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

        SweetAlertDialog pDialog = new SweetAlertDialog(LaporanPelangganActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        //INIT VIEW
        initComponent();

        initToolbar();

        Call<List<LaporanPelangganItem>> callPelanggan = penjualanEndpoint.rekapPelanggan(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        callPelanggan.enqueue(new Callback<List<LaporanPelangganItem>>() {
            @Override
            public void onResponse(Call<List<LaporanPelangganItem>> call, Response<List<LaporanPelangganItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(LaporanPelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    DecimalFormat decim = new DecimalFormat("#,###.##");
                    //DATA LAPORAN PENJUAL LIST
                    items = new ArrayList<>();

                    //DATA PENJUALAN PER PRODUK LIST
                    for (int i=0; i<response.body().size(); i++){

                        items.add(new LaporanPelangganItem(
                                response.body().get(i).getNamaPelanggan(),
                                response.body().get(i).getNoHpPelanggan(),
                                response.body().get(i).getTotKunjunganPelanggan(),
                                ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getTransaksiPelanggan())))));
                    }
                    // Removes blinks
                    ((SimpleItemAnimator) rvLaporanPelanggan.getItemAnimator()).setSupportsChangeAnimations(false);

                    //SET ADAPTER
                    adapter = new LaporanPelangganAdapter(items, LaporanPelangganActivity.this);
                    rvLaporanPelanggan.setLayoutManager(new LinearLayoutManager(LaporanPelangganActivity.this));
                    rvLaporanPelanggan.setAdapter(adapter);
                    rvLaporanPelanggan.setHasFixedSize(true);

                    //Jika ada list item ilustrasi hilang
                    if (adapter.getItemCount() > 0){
                        layoutEmpty.setVisibility(View.GONE);
                        layoutLaporan.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<LaporanPelangganItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(LaporanPelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
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

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long,Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long,Long> selection) {
                mSelectedPeriod.setText(materialDatePicker.getHeaderText()); //SET PICKER
                DateFrom = new SimpleDateFormat("yyyy-MM-dd").format(new Date(selection.first));
                DateTo = new SimpleDateFormat("yyyy-MM-dd").format(new Date(selection.second));

                SweetAlertDialog pDialog = new SweetAlertDialog(LaporanPelangganActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();
                Call<List<LaporanPelangganItem>> callPelanggan = penjualanEndpoint.rekapPelanggan(auth_token, id_store,DateFrom,DateTo);
                callPelanggan.enqueue(new Callback<List<LaporanPelangganItem>>() {
                    @Override
                    public void onResponse(Call<List<LaporanPelangganItem>> call, Response<List<LaporanPelangganItem>> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(LaporanPelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            DecimalFormat decim = new DecimalFormat("#,###.##");
                            //DATA LAPORAN PENJUAL LIST
                            items = new ArrayList<>();

                            //DATA PENJUALAN PER PRODUK LIST
                            for (int i=0; i<response.body().size(); i++){

                                items.add(new LaporanPelangganItem(
                                        response.body().get(i).getNamaPelanggan(),
                                        response.body().get(i).getNoHpPelanggan(),
                                        response.body().get(i).getTotKunjunganPelanggan(),
                                        ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getTransaksiPelanggan())))));
                            }
                            // Removes blinks
                            ((SimpleItemAnimator) rvLaporanPelanggan.getItemAnimator()).setSupportsChangeAnimations(false);

                            //SET ADAPTER
                            adapter = new LaporanPelangganAdapter(items, LaporanPelangganActivity.this);
                            rvLaporanPelanggan.setLayoutManager(new LinearLayoutManager(LaporanPelangganActivity.this));
                            rvLaporanPelanggan.setAdapter(adapter);
                            rvLaporanPelanggan.setHasFixedSize(true);

                            //Jika ada list item ilustrasi hilang
                            if (adapter.getItemCount() > 0){
                                layoutEmpty.setVisibility(View.GONE);
                                layoutLaporan.setVisibility(View.VISIBLE);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<LaporanPelangganItem>> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(LaporanPelangganActivity.this, SweetAlertDialog.ERROR_TYPE)
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

            SweetAlertDialog pDialog = new SweetAlertDialog(LaporanPelangganActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();

            if (intent.getIntExtra("OrderBY",0) == 1) {
                Collections.sort(items, new Comparator<LaporanPelangganItem>() {
                    @Override
                    public int compare(LaporanPelangganItem laporanPelangganItem, LaporanPelangganItem t1) {
                        return t1.getTotKunjunganPelanggan().compareToIgnoreCase(laporanPelangganItem.getTotKunjunganPelanggan());

                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvLaporanPelanggan.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new LaporanPelangganAdapter(items, LaporanPelangganActivity.this);
                rvLaporanPelanggan.setLayoutManager(new LinearLayoutManager(LaporanPelangganActivity.this));
                rvLaporanPelanggan.setAdapter(adapter);
                rvLaporanPelanggan.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutLaporan.setVisibility(View.VISIBLE);
                }
            } else if (intent.getIntExtra("OrderBY",0) == 2) {
                Collections.sort(items, new Comparator<LaporanPelangganItem>() {
                    @Override
                    public int compare(LaporanPelangganItem laporanPelangganItem, LaporanPelangganItem t1) {
                        return laporanPelangganItem.getTotKunjunganPelanggan().compareToIgnoreCase(t1.getTotKunjunganPelanggan());
                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvLaporanPelanggan.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new LaporanPelangganAdapter(items, LaporanPelangganActivity.this);
                rvLaporanPelanggan.setLayoutManager(new LinearLayoutManager(LaporanPelangganActivity.this));
                rvLaporanPelanggan.setAdapter(adapter);
                rvLaporanPelanggan.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutLaporan.setVisibility(View.VISIBLE);
                }
            } else if (intent.getIntExtra("OrderBY",0) == 3) {
                Collections.sort(items, new Comparator<LaporanPelangganItem>() {
                    @Override
                    public int compare(LaporanPelangganItem laporanPelangganItem, LaporanPelangganItem t1) {
                        return t1.getTransaksiPelanggan().compareToIgnoreCase(laporanPelangganItem.getTransaksiPelanggan());

                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvLaporanPelanggan.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new LaporanPelangganAdapter(items, LaporanPelangganActivity.this);
                rvLaporanPelanggan.setLayoutManager(new LinearLayoutManager(LaporanPelangganActivity.this));
                rvLaporanPelanggan.setAdapter(adapter);
                rvLaporanPelanggan.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutLaporan.setVisibility(View.VISIBLE);
                }
            } else if (intent.getIntExtra("OrderBY",0) == 4) {
                Collections.sort(items, new Comparator<LaporanPelangganItem>() {
                    @Override
                    public int compare(LaporanPelangganItem laporanPelangganItem, LaporanPelangganItem t1) {
                        return laporanPelangganItem.getTransaksiPelanggan().compareToIgnoreCase(t1.getTransaksiPelanggan());
                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvLaporanPelanggan.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new LaporanPelangganAdapter(items, LaporanPelangganActivity.this);
                rvLaporanPelanggan.setLayoutManager(new LinearLayoutManager(LaporanPelangganActivity.this));
                rvLaporanPelanggan.setAdapter(adapter);
                rvLaporanPelanggan.setHasFixedSize(true);

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
                new IntentFilter(INTENT_FILTER_PELANGGAN));
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
                BotSheetFilterPelangganFragment botSheetPelanggan = new BotSheetFilterPelangganFragment();
                botSheetPelanggan.setArguments(bundle);
                botSheetPelanggan.setCancelable(false);
                botSheetPelanggan.show(getSupportFragmentManager(), botSheetPelanggan.getTag());
                return true;
            }

            return false;
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_pelanggan_laporan);
        btnPilihPeriode = findViewById(R.id.btn_pilih_periode);
        mSelectedPeriod = findViewById(R.id.tv_selected_period);
        rvLaporanPelanggan = findViewById(R.id.rv_pelanggan_laporan);
        btnEkspor = findViewById(R.id.btn_ekspor_laporan_pelanggan);
        layoutLaporan = findViewById(R.id.layout_laporan_pelanggan);
        layoutEmpty = findViewById(R.id.layout_ilustrasi_empty_laporan_pelanggan);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent detail = new Intent(this, DetailLaporanPelangganActivity.class);
        detail.putExtra("noHpPelanggan", items.get(position).getNoHpPelanggan());
        detail.putExtra("namaPelanggan", items.get(position).getNamaPelanggan());
        startActivity(detail);
    }
}