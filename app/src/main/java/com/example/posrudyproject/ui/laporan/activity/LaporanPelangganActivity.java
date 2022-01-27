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

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
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

import java.util.ArrayList;
import java.util.List;

public class LaporanPelangganActivity extends AppCompatActivity implements OnItemClickListener {

    MaterialToolbar mToolbar;
    RecyclerView rvLaporanPelanggan;
    MaterialButton btnEkspor;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;

    List<LaporanPelangganItem> items;
    LaporanPelangganAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_pelanggan);

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
        ((SimpleItemAnimator) rvLaporanPelanggan.getItemAnimator()).setSupportsChangeAnimations(false);

        //DATA PENJUALAN PER PRODUK LIST
        items = new ArrayList<>();
        for (int i=0; i<10; i++){
            items.add(new LaporanPelangganItem(
                    "Ahmad",
                    "0812364589",
                    "2",
                    "Rp 5.000.000"));
        }

        //SET ADAPTER
        adapter = new LaporanPelangganAdapter(items, this);
        rvLaporanPelanggan.setLayoutManager(new LinearLayoutManager(this));
        rvLaporanPelanggan.setAdapter(adapter);
        rvLaporanPelanggan.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_filter){
                BotSheetFilterPelangganFragment botSheetPelanggan = new BotSheetFilterPelangganFragment();
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
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent detail = new Intent(this, DetailLaporanPelangganActivity.class);
        startActivity(detail);
    }
}