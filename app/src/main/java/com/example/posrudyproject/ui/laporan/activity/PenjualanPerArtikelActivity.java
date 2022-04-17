package com.example.posrudyproject.ui.laporan.activity;

import androidx.annotation.RequiresApi;
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
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualanEndpoint;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterArtikelFragment;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterTipeFragment;
import com.example.posrudyproject.ui.laporan.adapter.PenjualanPerArtikelAdapter;
import com.example.posrudyproject.ui.laporan.adapter.PenjualanPerTipeAdapter;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerArtikelItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerTipeItem;
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
import java.util.Objects;
import java.util.stream.Collectors;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualanPerArtikelActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvPenjualanPerArtikel;
    MaterialButton btnEkspor;
    AppCompatTextView tvTotalTerjual, tvTotalPenjualan;
    ConstraintLayout layoutEmpty;
    LinearLayoutCompat layoutLaporan;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;
    public static final String INTENT_FILTER_ARTIKEL = "INTENT_FILTER_ARTIKEL";
    PenjualanEndpoint penjualanEndpoint;
    List<PenjualanPerArtikelItem> items;
    PenjualanPerArtikelAdapter adapter;
    int id_store;
    String auth_token,DateFrom,DateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_per_artikel);
        penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        auth_token = ("Bearer ").concat(token);
        id_store = preferences.getInt("id_store", 0);
        items = new ArrayList<>();
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

        SweetAlertDialog pDialog = new SweetAlertDialog(PenjualanPerArtikelActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<Map> call = penjualanEndpoint.rangkumanPenjualanMobilePerArtikel(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(PenjualanPerArtikelActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                    DecimalFormat decim = new DecimalFormat("#,###.##");
                    tvTotalTerjual.setText(decim.format(Float.valueOf(String.valueOf(inner.get("totalTerjual")))));
                    tvTotalPenjualan.setText(("Rp").concat(decim.format(Float.valueOf(String.valueOf(inner.get("omset"))))));

                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(PenjualanPerArtikelActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        Call<List<PenjualanPerArtikelItem>> callPenjual = penjualanEndpoint.rekapArtikel(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        callPenjual.enqueue(new Callback<List<PenjualanPerArtikelItem>>() {
            @Override
            public void onResponse(Call<List<PenjualanPerArtikelItem>> call, Response<List<PenjualanPerArtikelItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(PenjualanPerArtikelActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                        items.add(new PenjualanPerArtikelItem(
                                response.body().get(i).getNamaArtikel(),
                                ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getTotPenjualanArtikel()))),
                                response.body().get(i).getJmlTerjualArtikel(),
                                ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getTotPenjualanArtikel())))));
                    }
                    // Removes blinks
                    ((SimpleItemAnimator) rvPenjualanPerArtikel.getItemAnimator()).setSupportsChangeAnimations(false);

                    //SET ADAPTER
                    adapter = new PenjualanPerArtikelAdapter(items, PenjualanPerArtikelActivity.this);
                    rvPenjualanPerArtikel.setLayoutManager(new LinearLayoutManager(PenjualanPerArtikelActivity.this));
                    rvPenjualanPerArtikel.setAdapter(adapter);
                    rvPenjualanPerArtikel.setHasFixedSize(true);

                    //Jika ada list item ilustrasi hilang
                    if (adapter.getItemCount() > 0){
                        layoutEmpty.setVisibility(View.GONE);
                        layoutLaporan.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<PenjualanPerArtikelItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(PenjualanPerArtikelActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                SweetAlertDialog pDialog = new SweetAlertDialog(PenjualanPerArtikelActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();

                Call<Map> call = penjualanEndpoint.rangkumanPenjualanMobilePerArtikel(auth_token, id_store,DateFrom,DateTo);
                call.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(PenjualanPerArtikelActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                            DecimalFormat decim = new DecimalFormat("#,###.##");
                            tvTotalTerjual.setText(decim.format(Float.valueOf(String.valueOf(inner.get("totalTerjual")))));
                            tvTotalPenjualan.setText(("Rp").concat(decim.format(Float.valueOf(String.valueOf(inner.get("omset"))))));

                        }
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(PenjualanPerArtikelActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

                Call<List<PenjualanPerArtikelItem>> callPenjual = penjualanEndpoint.rekapArtikel(auth_token, id_store,DateFrom,DateTo);
                callPenjual.enqueue(new Callback<List<PenjualanPerArtikelItem>>() {
                    @Override
                    public void onResponse(Call<List<PenjualanPerArtikelItem>> call, Response<List<PenjualanPerArtikelItem>> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(PenjualanPerArtikelActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                                items.add(new PenjualanPerArtikelItem(
                                        response.body().get(i).getNamaArtikel(),
                                        ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getTotPenjualanArtikel()))),
                                        response.body().get(i).getJmlTerjualArtikel(),
                                        ("Rp").concat(decim.format(Float.valueOf(response.body().get(i).getTotPenjualanArtikel())))));
                            }
                            // Removes blinks
                            ((SimpleItemAnimator) rvPenjualanPerArtikel.getItemAnimator()).setSupportsChangeAnimations(false);

                            //SET ADAPTER
                            adapter = new PenjualanPerArtikelAdapter(items, PenjualanPerArtikelActivity.this);
                            rvPenjualanPerArtikel.setLayoutManager(new LinearLayoutManager(PenjualanPerArtikelActivity.this));
                            rvPenjualanPerArtikel.setAdapter(adapter);
                            rvPenjualanPerArtikel.setHasFixedSize(true);

                            //Jika ada list item ilustrasi hilang
                            if (adapter.getItemCount() > 0){
                                layoutEmpty.setVisibility(View.GONE);
                                layoutLaporan.setVisibility(View.VISIBLE);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<PenjualanPerArtikelItem>> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(PenjualanPerArtikelActivity.this, SweetAlertDialog.ERROR_TYPE)
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
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO extract extras from intent

            SweetAlertDialog pDialog = new SweetAlertDialog(PenjualanPerArtikelActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();

            if (intent.getStringExtra("artikel") != "") {
                pDialog.dismiss();
                List<PenjualanPerArtikelItem> res = items.stream()
                        .filter(a -> Objects.equals(a.getNamaArtikel(),intent.getStringExtra("artikel")))
                        .collect(Collectors.toList());
                // Removes blinks
                ((SimpleItemAnimator) rvPenjualanPerArtikel.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new PenjualanPerArtikelAdapter(res, PenjualanPerArtikelActivity.this);
                rvPenjualanPerArtikel.setLayoutManager(new LinearLayoutManager(PenjualanPerArtikelActivity.this));
                rvPenjualanPerArtikel.setAdapter(adapter);
                rvPenjualanPerArtikel.setHasFixedSize(true);

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
                new IntentFilter(INTENT_FILTER_ARTIKEL));
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
                BotSheetFilterArtikelFragment botSheetArtikel = new BotSheetFilterArtikelFragment();
                botSheetArtikel.setCancelable(false);
                botSheetArtikel.show(getSupportFragmentManager(), botSheetArtikel.getTag());
                return true;
            }
            return false;
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_penjualan_artikel_laporan);
        btnPilihPeriode = findViewById(R.id.btn_pilih_periode);
        mSelectedPeriod = findViewById(R.id.tv_selected_period);
        rvPenjualanPerArtikel = findViewById(R.id.rv_penjualan_artikel_laporan);
        tvTotalTerjual = findViewById(R.id.tv_total_terjual_per_artikel);
        tvTotalPenjualan = findViewById(R.id.tv_total_penjualan_per_artikel);
        btnEkspor = findViewById(R.id.btn_ekspor_laporan_artikel);
        layoutLaporan = findViewById(R.id.layout_laporan_penjualan_per_artikel);
        layoutEmpty = findViewById(R.id.layout_ilustrasi_empty_laporan_artikel);
    }
}