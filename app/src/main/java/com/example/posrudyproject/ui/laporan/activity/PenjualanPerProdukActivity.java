package com.example.posrudyproject.ui.laporan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterPenjualanProdukFragment;
import com.example.posrudyproject.ui.laporan.adapter.PenjualanPerProdukAdapter;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerProdukItem;
import com.example.posrudyproject.ui.pesananTunggu.activity.PesananTungguActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.List;

public class PenjualanPerProdukActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvPenjualanPerProduk;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;

    List<PenjualanPerProdukItem> items;
    PenjualanPerProdukAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_per_produk);

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

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                mSelectedPeriod.setText(materialDatePicker.getHeaderText()); //SET PICKER
            }
        });

        // Removes blinks
        ((SimpleItemAnimator) rvPenjualanPerProduk.getItemAnimator()).setSupportsChangeAnimations(false);

        //DATA PENJUALAN PER PRODUK LIST
        items = new ArrayList<>();
        for (int i=0; i<10; i++){
            items.add(new PenjualanPerProdukItem(
                    "Fire Red Matte - RP Optics Smoke Black",
                    "Rp 3.000.000",
                    "1 Pcs",
                    "SP631054-0000",
                    "EYEWEAR",
                    "Rp 0",
                    "Rp 3.000.000"));
        }

        //SET ADAPTER
        adapter = new PenjualanPerProdukAdapter(items, this);
        rvPenjualanPerProduk.setLayoutManager(new LinearLayoutManager(this));
        rvPenjualanPerProduk.setAdapter(adapter);
        rvPenjualanPerProduk.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_filter){
                BotSheetFilterPenjualanProdukFragment botSheetProduk = new BotSheetFilterPenjualanProdukFragment();
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
    }
}