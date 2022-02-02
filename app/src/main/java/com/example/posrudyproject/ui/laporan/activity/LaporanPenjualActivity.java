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
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterPenjualFragment;
import com.example.posrudyproject.ui.laporan.adapter.LaporanPelangganAdapter;
import com.example.posrudyproject.ui.laporan.adapter.LaporanPenjualAdapter;
import com.example.posrudyproject.ui.laporan.model.LaporanPelangganItem;
import com.example.posrudyproject.ui.laporan.model.LaporanPenjualItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.List;

public class LaporanPenjualActivity extends AppCompatActivity implements OnItemClickListener {

    MaterialToolbar mToolbar;
    RecyclerView rvLaporanPenjual;
    MaterialButton btnEkspor;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;

    List<LaporanPenjualItem> items;
    LaporanPenjualAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_penjual);

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
        ((SimpleItemAnimator) rvLaporanPenjual.getItemAnimator()).setSupportsChangeAnimations(false);

        //DATA LAPORAN PENJUAL LIST
        items = new ArrayList<>();
        for (int i=0; i<10; i++){
            items.add(new LaporanPenjualItem(
                    "John Doe",
                    "Rp 14.000.000",
                    "2 Transaksi"));
        }

        //SET ADAPTER
        adapter = new LaporanPenjualAdapter(items, this);
        rvLaporanPenjual.setLayoutManager(new LinearLayoutManager(this));
        rvLaporanPenjual.setAdapter(adapter);
        rvLaporanPenjual.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_filter){
                BotSheetFilterPenjualFragment botSheetPenjual = new BotSheetFilterPenjualFragment();
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
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent detailLaporan = new Intent(this, DetailLaporanPenjualActivity.class);
        startActivity(detailLaporan);
    }
}