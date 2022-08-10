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
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterTipeFragment;
import com.example.posrudyproject.ui.laporan.adapter.PenjualanPerTipeAdapter;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerTipeItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
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
import java.util.Objects;
import java.util.stream.Collectors;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualanPerTipeActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvPenjualanPerTipe;
    MaterialButton btnEkspor;
    AppCompatTextView tvTotalTerjual, tvTotalPenjualan;
    ConstraintLayout layoutEmpty;
    LinearLayoutCompat layoutLaporan;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;
    public static final String INTENT_FILTER_TIPE = "INTENT_FILTER_TIPE";
    PenjualanEndpoint penjualanEndpoint;
    List<PenjualanPerTipeItem> items;
    PenjualanPerTipeAdapter adapter;
    int id_store;
    String auth_token,DateFrom,DateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_per_tipe);
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

        SweetAlertDialog pDialog = new SweetAlertDialog(PenjualanPerTipeActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<Map> call = penjualanEndpoint.rangkumanPenjualanMobilePerTipe(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(PenjualanPerTipeActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                    DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                    df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                    tvTotalTerjual.setText(df.format(Double.valueOf(String.valueOf(inner.get("totalTerjual")))));
                    tvTotalPenjualan.setText(("Rp").concat(df.format(Double.valueOf(String.valueOf(inner.get("omset"))))));

                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(PenjualanPerTipeActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        Call<List<PenjualanPerTipeItem>> callPenjual = penjualanEndpoint.rekapTipe(auth_token, id_store,df.format(firstDayOfCurrentYear.getTime()),df.format(lastDayOfCurrentYear.getTime()));
        callPenjual.enqueue(new Callback<List<PenjualanPerTipeItem>>() {
            @Override
            public void onResponse(Call<List<PenjualanPerTipeItem>> call, Response<List<PenjualanPerTipeItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(PenjualanPerTipeActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                    df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                    //DATA LAPORAN PENJUAL LIST
                    items = new ArrayList<>();

                    //DATA PENJUALAN PER PRODUK LIST
                    for (int i=0; i<response.body().size(); i++){
                        items.add(new PenjualanPerTipeItem(
                                response.body().get(i).getNamaTipe(),
                                ("Rp").concat(df.format(Double.valueOf(response.body().get(i).getTotPenjualanTipe()))),
                                response.body().get(i).getJmlTerjualTipe(),
                                ("Rp").concat(df.format(Double.valueOf(response.body().get(i).getTotPenjualanTipe())))));
                    }
                    // Removes blinks
                    ((SimpleItemAnimator) rvPenjualanPerTipe.getItemAnimator()).setSupportsChangeAnimations(false);

                    //SET ADAPTER
                    adapter = new PenjualanPerTipeAdapter(items, PenjualanPerTipeActivity.this);
                    rvPenjualanPerTipe.setLayoutManager(new LinearLayoutManager(PenjualanPerTipeActivity.this));
                    rvPenjualanPerTipe.setAdapter(adapter);
                    rvPenjualanPerTipe.setHasFixedSize(true);

                    //Jika ada list item ilustrasi hilang
                    if (adapter.getItemCount() > 0){
                        layoutEmpty.setVisibility(View.GONE);
                        layoutLaporan.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<PenjualanPerTipeItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(PenjualanPerTipeActivity.this, SweetAlertDialog.ERROR_TYPE)
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

                SweetAlertDialog pDialog = new SweetAlertDialog(PenjualanPerTipeActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();

                Call<Map> call = penjualanEndpoint.rangkumanPenjualanMobilePerTipe(auth_token, id_store,DateFrom,DateTo);
                call.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(PenjualanPerTipeActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");

                            DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                            df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                            tvTotalTerjual.setText(df.format(Double.valueOf(String.valueOf(inner.get("totalTerjual")))));
                            tvTotalPenjualan.setText(("Rp").concat(df.format(Double.valueOf(String.valueOf(inner.get("omset"))))));

                        }
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(PenjualanPerTipeActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

                Call<List<PenjualanPerTipeItem>> callPenjual = penjualanEndpoint.rekapTipe(auth_token, id_store,DateFrom,DateTo);
                callPenjual.enqueue(new Callback<List<PenjualanPerTipeItem>>() {
                    @Override
                    public void onResponse(Call<List<PenjualanPerTipeItem>> call, Response<List<PenjualanPerTipeItem>> response) {
                        if (!response.isSuccessful()){
                            pDialog.dismiss();
                            new SweetAlertDialog(PenjualanPerTipeActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(String.valueOf(response.code()))
                                    .setContentText(response.message())
                                    .show();
                        } else {
                            pDialog.dismiss();
                            DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                            df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
                            //DATA LAPORAN PENJUAL LIST
                            items = new ArrayList<>();

                            //DATA PENJUALAN PER PRODUK LIST
                            for (int i=0; i<response.body().size(); i++){
                                items.add(new PenjualanPerTipeItem(
                                        response.body().get(i).getNamaTipe(),
                                        ("Rp").concat(df.format(Double.valueOf(response.body().get(i).getTotPenjualanTipe()))),
                                        response.body().get(i).getJmlTerjualTipe(),
                                        ("Rp").concat(df.format(Double.valueOf(response.body().get(i).getTotPenjualanTipe())))));
                            }
                            // Removes blinks
                            ((SimpleItemAnimator) rvPenjualanPerTipe.getItemAnimator()).setSupportsChangeAnimations(false);

                            //SET ADAPTER
                            adapter = new PenjualanPerTipeAdapter(items, PenjualanPerTipeActivity.this);
                            rvPenjualanPerTipe.setLayoutManager(new LinearLayoutManager(PenjualanPerTipeActivity.this));
                            rvPenjualanPerTipe.setAdapter(adapter);
                            rvPenjualanPerTipe.setHasFixedSize(true);

                            //Jika ada list item ilustrasi hilang
                            if (adapter.getItemCount() > 0){
                                layoutEmpty.setVisibility(View.GONE);
                                layoutLaporan.setVisibility(View.VISIBLE);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<PenjualanPerTipeItem>> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(PenjualanPerTipeActivity.this, SweetAlertDialog.ERROR_TYPE)
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

            SweetAlertDialog pDialog = new SweetAlertDialog(PenjualanPerTipeActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();

            if (intent.getStringExtra("nama_tipe") != "") {
                pDialog.dismiss();
                List<PenjualanPerTipeItem> res = items.stream()
                        .filter(a -> Objects.equals(a.getNamaTipe(),intent.getStringExtra("nama_tipe")))
                        .collect(Collectors.toList());
                // Removes blinks
                ((SimpleItemAnimator) rvPenjualanPerTipe.getItemAnimator()).setSupportsChangeAnimations(false);

                //SET ADAPTER
                adapter = new PenjualanPerTipeAdapter(res, PenjualanPerTipeActivity.this);
                rvPenjualanPerTipe.setLayoutManager(new LinearLayoutManager(PenjualanPerTipeActivity.this));
                rvPenjualanPerTipe.setAdapter(adapter);
                rvPenjualanPerTipe.setHasFixedSize(true);

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
                new IntentFilter(INTENT_FILTER_TIPE));
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
                BotSheetFilterTipeFragment botSheetTipe = new BotSheetFilterTipeFragment();
                botSheetTipe.setCancelable(false);
                botSheetTipe.show(getSupportFragmentManager(), botSheetTipe.getTag());
                return true;
            }
            return false;
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_penjualan_tipe_laporan);
        btnPilihPeriode = findViewById(R.id.btn_pilih_periode);
        mSelectedPeriod = findViewById(R.id.tv_selected_period);
        rvPenjualanPerTipe = findViewById(R.id.rv_penjualan_tipe_laporan);
        tvTotalTerjual = findViewById(R.id.tv_total_terjual_per_tipe);
        tvTotalPenjualan = findViewById(R.id.tv_total_penjualan_per_tipe);
        btnEkspor = findViewById(R.id.btn_ekspor_laporan_tipe);
        layoutLaporan = findViewById(R.id.layout_laporan_penjualan_per_tipe);
        layoutEmpty = findViewById(R.id.layout_ilustrasi_empty_laporan_tipe);
    }
}