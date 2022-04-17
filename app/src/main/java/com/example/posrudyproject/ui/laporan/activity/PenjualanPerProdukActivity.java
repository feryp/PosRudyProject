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

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualanEndpoint;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterPenjualanProdukFragment;
import com.example.posrudyproject.ui.laporan.adapter.PenjualanPerKategoriAdapter;
import com.example.posrudyproject.ui.laporan.adapter.PenjualanPerProdukAdapter;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerKategoriItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerProdukItem;
import com.example.posrudyproject.ui.pesananTunggu.activity.PesananTungguActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualanPerProdukActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvPenjualanPerProduk;
    MaterialButton btnEkspor;
    AppCompatTextView tvTotalTerjual, tvTotalPenjualan;
    ConstraintLayout layoutEmpty;
    LinearLayoutCompat layoutLaporan;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;
    public static final String INTENT_FILTER_PRODUK = "INTENT_FILTER_PRODUK";
    PenjualanEndpoint penjualanEndpoint;
    List<PenjualanPerProdukItem> items;
    PenjualanPerProdukAdapter adapter;
    int id_store;
    String auth_token,DateFrom,DateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_per_produk);
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

        SweetAlertDialog pDialog = new SweetAlertDialog(PenjualanPerProdukActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();


        Call<Map> callTotalByDefaultDate = penjualanEndpoint.rangkumanPenjualanMobile(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        callTotalByDefaultDate.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(PenjualanPerProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                    DecimalFormat decim = new DecimalFormat("#,###.##");
                    tvTotalTerjual.setText(decim.format(Float.valueOf(String.valueOf(inner.get("jmlPenjualan")))));
                    tvTotalPenjualan.setText(("Rp").concat(decim.format(Float.valueOf(String.valueOf(inner.get("omset"))))));

                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(PenjualanPerProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        Call<List<PenjualanPerProdukItem>> callPenjualanByDefaultDate = penjualanEndpoint.rekapProduk(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        callPenjualanByDefaultDate.enqueue(new Callback<List<PenjualanPerProdukItem>>() {
            @Override
            public void onResponse(Call<List<PenjualanPerProdukItem>> call, Response<List<PenjualanPerProdukItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(PenjualanPerProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                        items.add(new PenjualanPerProdukItem(
                                response.body().get(i).getNamaProduk(),
                                ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getTotalPenjualanProduk()))),
                                response.body().get(i).getQtyProduk(),
                                response.body().get(i).getArtikelProduk(),
                                response.body().get(i).getKategoriProduk(),
                                ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getTotalPenjualanProduk())))));
                    }
                    // Removes blinks
                    ((SimpleItemAnimator) rvPenjualanPerProduk.getItemAnimator()).setSupportsChangeAnimations(false);

                    //SET ADAPTER
                    adapter = new PenjualanPerProdukAdapter(items, PenjualanPerProdukActivity.this);
                    rvPenjualanPerProduk.setLayoutManager(new LinearLayoutManager(PenjualanPerProdukActivity.this));
                    rvPenjualanPerProduk.setAdapter(adapter);
                    rvPenjualanPerProduk.setHasFixedSize(true);

                    //Jika ada list item ilustrasi hilang
                    if (adapter.getItemCount() > 0){
                        layoutEmpty.setVisibility(View.GONE);
                        layoutLaporan.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<PenjualanPerProdukItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(PenjualanPerProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

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

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long,Long> selection) {
                mSelectedPeriod.setText(materialDatePicker.getHeaderText()); //SET PICKER
                DateFrom = new SimpleDateFormat("yyyy-MM-dd").format(new Date(selection.first));
                DateTo = new SimpleDateFormat("yyyy-MM-dd").format(new Date(selection.second));


                Call<Map> call = penjualanEndpoint.rangkumanPenjualanMobile(auth_token, id_store,DateFrom,DateTo);
                call.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(PenjualanPerProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                            DecimalFormat decim = new DecimalFormat("#,###.##");
                            tvTotalTerjual.setText(decim.format(Float.valueOf(String.valueOf(inner.get("jmlPenjualan")))));
                            tvTotalPenjualan.setText(("Rp").concat(decim.format(Float.valueOf(String.valueOf(inner.get("omset"))))));

                        }
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(PenjualanPerProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

                Call<List<PenjualanPerProdukItem>> callPenjual = penjualanEndpoint.rekapProduk(auth_token, id_store,DateFrom,DateTo);
                callPenjual.enqueue(new Callback<List<PenjualanPerProdukItem>>() {
                    @Override
                    public void onResponse(Call<List<PenjualanPerProdukItem>> call, Response<List<PenjualanPerProdukItem>> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(PenjualanPerProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                                items.add(new PenjualanPerProdukItem(
                                        response.body().get(i).getNamaProduk(),
                                        ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getTotalPenjualanProduk()))),
                                        response.body().get(i).getQtyProduk(),
                                        response.body().get(i).getArtikelProduk(),
                                        response.body().get(i).getKategoriProduk(),
                                        ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getTotalPenjualanProduk())))));
                            }
                            // Removes blinks
                            ((SimpleItemAnimator) rvPenjualanPerProduk.getItemAnimator()).setSupportsChangeAnimations(false);

                            //SET ADAPTER
                            adapter = new PenjualanPerProdukAdapter(items, PenjualanPerProdukActivity.this);
                            rvPenjualanPerProduk.setLayoutManager(new LinearLayoutManager(PenjualanPerProdukActivity.this));
                            rvPenjualanPerProduk.setAdapter(adapter);
                            rvPenjualanPerProduk.setHasFixedSize(true);

                            //Jika ada list item ilustrasi hilang
                            if (adapter.getItemCount() > 0){
                                layoutEmpty.setVisibility(View.GONE);
                                layoutLaporan.setVisibility(View.VISIBLE);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<PenjualanPerProdukItem>> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(PenjualanPerProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
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

            SweetAlertDialog pDialog = new SweetAlertDialog(PenjualanPerProdukActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();

            System.out.println(intent.getIntExtra("OrderBY",0));

            Call<Map> callTotal = penjualanEndpoint.rangkumanPenjualanMobile(auth_token, id_store,StartDate,EndDate);
            callTotal.enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    if (!response.isSuccessful()){
                        pDialog.dismiss();
                        new SweetAlertDialog(PenjualanPerProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(String.valueOf(response.code()))
                                .setContentText(response.message())
                                .show();
                    } else {
                        pDialog.dismiss();
                        Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                        DecimalFormat decim = new DecimalFormat("#,###.##");
                        tvTotalTerjual.setText(decim.format(Float.valueOf(String.valueOf(inner.get("jmlPenjualan")))));
                        tvTotalPenjualan.setText(("Rp").concat(decim.format(Float.valueOf(String.valueOf(inner.get("omset"))))));

                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    pDialog.dismiss();
                    new SweetAlertDialog(PenjualanPerProdukActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(t.getMessage())
                            .show();
                }
            });

            if (intent.getIntExtra("OrderBY",0) == 1) {
                Collections.sort(items, new Comparator<PenjualanPerProdukItem>() {
                    @Override
                    public int compare(PenjualanPerProdukItem penjualanPerProdukItem, PenjualanPerProdukItem t1) {
                        return penjualanPerProdukItem.getNamaProduk().compareToIgnoreCase(t1.getNamaProduk());

                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvPenjualanPerProduk.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new PenjualanPerProdukAdapter(items, PenjualanPerProdukActivity.this);
                rvPenjualanPerProduk.setLayoutManager(new LinearLayoutManager(PenjualanPerProdukActivity.this));
                rvPenjualanPerProduk.setAdapter(adapter);
                rvPenjualanPerProduk.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutLaporan.setVisibility(View.VISIBLE);
                }
            } else if (intent.getIntExtra("OrderBY",0) == 2) {
                Collections.sort(items, new Comparator<PenjualanPerProdukItem>() {
                    @Override
                    public int compare(PenjualanPerProdukItem penjualanPerProdukItem, PenjualanPerProdukItem t1) {
                        return t1.getNamaProduk().compareToIgnoreCase(penjualanPerProdukItem.getNamaProduk());
                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvPenjualanPerProduk.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new PenjualanPerProdukAdapter(items, PenjualanPerProdukActivity.this);
                rvPenjualanPerProduk.setLayoutManager(new LinearLayoutManager(PenjualanPerProdukActivity.this));
                rvPenjualanPerProduk.setAdapter(adapter);
                rvPenjualanPerProduk.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutLaporan.setVisibility(View.VISIBLE);
                }
            } else if (intent.getIntExtra("OrderBY",0) == 3) {
                Collections.sort(items, new Comparator<PenjualanPerProdukItem>() {
                    @Override
                    public int compare(PenjualanPerProdukItem penjualanPerProdukItem, PenjualanPerProdukItem t1) {
                        return penjualanPerProdukItem.getKategoriProduk().compareToIgnoreCase(t1.getKategoriProduk());

                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvPenjualanPerProduk.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new PenjualanPerProdukAdapter(items, PenjualanPerProdukActivity.this);
                rvPenjualanPerProduk.setLayoutManager(new LinearLayoutManager(PenjualanPerProdukActivity.this));
                rvPenjualanPerProduk.setAdapter(adapter);
                rvPenjualanPerProduk.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutLaporan.setVisibility(View.VISIBLE);
                }
            } else if (intent.getIntExtra("OrderBY",0) == 4) {
                Collections.sort(items, new Comparator<PenjualanPerProdukItem>() {
                    @Override
                    public int compare(PenjualanPerProdukItem penjualanPerProdukItem, PenjualanPerProdukItem t1) {
                        return t1.getKategoriProduk().compareToIgnoreCase(penjualanPerProdukItem.getKategoriProduk());
                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvPenjualanPerProduk.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new PenjualanPerProdukAdapter(items, PenjualanPerProdukActivity.this);
                rvPenjualanPerProduk.setLayoutManager(new LinearLayoutManager(PenjualanPerProdukActivity.this));
                rvPenjualanPerProduk.setAdapter(adapter);
                rvPenjualanPerProduk.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutLaporan.setVisibility(View.VISIBLE);
                }
            } else if (intent.getIntExtra("OrderBY",0) == 5) {
                Collections.sort(items, new Comparator<PenjualanPerProdukItem>() {
                    @Override
                    public int compare(PenjualanPerProdukItem t1, PenjualanPerProdukItem penjualanPerProdukItem) {
                        return t1.getPenjualanKotorProduk().compareToIgnoreCase(penjualanPerProdukItem.getPenjualanKotorProduk());
                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvPenjualanPerProduk.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new PenjualanPerProdukAdapter(items, PenjualanPerProdukActivity.this);
                rvPenjualanPerProduk.setLayoutManager(new LinearLayoutManager(PenjualanPerProdukActivity.this));
                rvPenjualanPerProduk.setAdapter(adapter);
                rvPenjualanPerProduk.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutLaporan.setVisibility(View.VISIBLE);
                }
            } else if (intent.getIntExtra("OrderBY",0) == 6) {
                Collections.sort(items, new Comparator<PenjualanPerProdukItem>() {
                    @Override
                    public int compare(PenjualanPerProdukItem t1, PenjualanPerProdukItem penjualanPerProdukItem) {
                        return penjualanPerProdukItem.getPenjualanKotorProduk().compareToIgnoreCase(t1.getPenjualanKotorProduk());

                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvPenjualanPerProduk.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new PenjualanPerProdukAdapter(items, PenjualanPerProdukActivity.this);
                rvPenjualanPerProduk.setLayoutManager(new LinearLayoutManager(PenjualanPerProdukActivity.this));
                rvPenjualanPerProduk.setAdapter(adapter);
                rvPenjualanPerProduk.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutLaporan.setVisibility(View.VISIBLE);
                }
            } else if (intent.getIntExtra("OrderBY",0) == 7) {
                Collections.sort(items, new Comparator<PenjualanPerProdukItem>() {
                    @Override
                    public int compare(PenjualanPerProdukItem t1, PenjualanPerProdukItem penjualanPerProdukItem) {
                        return t1.getTotalPenjualanProduk().compareToIgnoreCase(penjualanPerProdukItem.getTotalPenjualanProduk());
                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvPenjualanPerProduk.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new PenjualanPerProdukAdapter(items, PenjualanPerProdukActivity.this);
                rvPenjualanPerProduk.setLayoutManager(new LinearLayoutManager(PenjualanPerProdukActivity.this));
                rvPenjualanPerProduk.setAdapter(adapter);
                rvPenjualanPerProduk.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutLaporan.setVisibility(View.VISIBLE);
                }
            } else if (intent.getIntExtra("OrderBY",0) == 8) {
                Collections.sort(items, new Comparator<PenjualanPerProdukItem>() {
                    @Override
                    public int compare(PenjualanPerProdukItem t1, PenjualanPerProdukItem penjualanPerProdukItem) {
                        return penjualanPerProdukItem.getTotalPenjualanProduk().compareToIgnoreCase(t1.getTotalPenjualanProduk());

                    }
                });
                // Removes blinks
                ((SimpleItemAnimator) rvPenjualanPerProduk.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new PenjualanPerProdukAdapter(items, PenjualanPerProdukActivity.this);
                rvPenjualanPerProduk.setLayoutManager(new LinearLayoutManager(PenjualanPerProdukActivity.this));
                rvPenjualanPerProduk.setAdapter(adapter);
                rvPenjualanPerProduk.setHasFixedSize(true);

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
                new IntentFilter(INTENT_FILTER_PRODUK));
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
                BotSheetFilterPenjualanProdukFragment botSheetProduk = new BotSheetFilterPenjualanProdukFragment();
                botSheetProduk.setArguments(bundle);
                botSheetProduk.setCancelable(false);
                botSheetProduk.show(getSupportFragmentManager(), botSheetProduk.getTag());
                return true;
            }

            return false;
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_penjualan_produk_laporan);
        btnPilihPeriode = findViewById(R.id.btn_pilih_periode);
        mSelectedPeriod = findViewById(R.id.tv_selected_period);
        rvPenjualanPerProduk = findViewById(R.id.rv_penjualan_produk_laporan);
        tvTotalTerjual = findViewById(R.id.tv_total_terjual_per_produk);

        tvTotalPenjualan = findViewById(R.id.tv_total_penjualan_per_produk);
        btnEkspor = findViewById(R.id.btn_ekspor_laporan_produk);
        layoutLaporan = findViewById(R.id.layout_laporan_penjualan_per_produk);
        layoutEmpty = findViewById(R.id.layout_ilustrasi_empty_laporan_produk);
    }
}