package com.example.posrudyproject.ui.laporan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.adapter.RiwayatTransaksiPelangganAdapter;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiPelangganItem;
import com.example.posrudyproject.ui.laporan.model.SubRiwayatTransaksiPelangganItem;
import com.example.posrudyproject.ui.pesananTunggu.adapter.PesananTungguAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.List;

public class DetailLaporanPelangganActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvRiwayatTransaksi;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;

    RiwayatTransaksiPelangganAdapter adapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan_pelanggan);

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
        ((SimpleItemAnimator) rvRiwayatTransaksi.getItemAnimator()).setSupportsChangeAnimations(false);

        //Setup Adapter
        adapter = new RiwayatTransaksiPelangganAdapter(buildItemList(), this);
        rvRiwayatTransaksi.setLayoutManager(new LinearLayoutManager(this));
        rvRiwayatTransaksi.setAdapter(adapter);
        rvRiwayatTransaksi.setHasFixedSize(true);
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

    private List<RiwayatTransaksiPelangganItem> buildItemList(){
        List<RiwayatTransaksiPelangganItem> riwayatTransaksiPelangganItems = new ArrayList<>();
        for (int i=0; i<15; i++){
            RiwayatTransaksiPelangganItem item = new RiwayatTransaksiPelangganItem(
                    "Sabtu, 07 Aug 2021",
                    "Rp 9.000.000", buildSubItemList());
            riwayatTransaksiPelangganItems.add(item);
        }
        return riwayatTransaksiPelangganItems;
    }

    private List<SubRiwayatTransaksiPelangganItem> buildSubItemList() {
        List<SubRiwayatTransaksiPelangganItem> subRiwayatTransaksiPelangganItems = new ArrayList<>();
        for (int i=0; i<3; i++){
            SubRiwayatTransaksiPelangganItem subItem = new SubRiwayatTransaksiPelangganItem("Rp 3.000.000", "#INV001", "Tunai", "10:09");
            subRiwayatTransaksiPelangganItems.add(subItem);
        }
        return subRiwayatTransaksiPelangganItems;
    }
}