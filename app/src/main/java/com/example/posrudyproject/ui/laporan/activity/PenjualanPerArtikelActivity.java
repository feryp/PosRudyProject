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

import java.util.ArrayList;
import java.util.List;

public class PenjualanPerArtikelActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvPenjualanPerArtikel;
    MaterialButton btnEkspor;
    AppCompatTextView tvTotalTerjual, tvTotalPajak, tvTotPenjualanKotor, tvTotalPenjualan;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;

    List<PenjualanPerArtikelItem> items;
    PenjualanPerArtikelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_per_artikel);

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
        ((SimpleItemAnimator) rvPenjualanPerArtikel.getItemAnimator()).setSupportsChangeAnimations(false);

        //DATA PENJUALAN PER PRODUK LIST
        items = new ArrayList<>();
        for (int i=0; i<10; i++){
            items.add(new PenjualanPerArtikelItem(
                    "SP633846-0011",
                    "Rp 4.000.000",
                    "2",
                    "Rp 4.000.000",
                    "Rp 0"));
        }

        //SET ADAPTER
        adapter = new PenjualanPerArtikelAdapter(items, this);
        rvPenjualanPerArtikel.setLayoutManager(new LinearLayoutManager(this));
        rvPenjualanPerArtikel.setAdapter(adapter);
        rvPenjualanPerArtikel.setHasFixedSize(true);
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
        tvTotalPajak = findViewById(R.id.tv_total_pajak_per_artikel);
        tvTotPenjualanKotor = findViewById(R.id.tv_total_penjualan_kotor_per_artikel);
        tvTotalPenjualan = findViewById(R.id.tv_total_penjualan_per_artikel);
        btnEkspor = findViewById(R.id.btn_ekspor_laporan_artikel);
    }
}