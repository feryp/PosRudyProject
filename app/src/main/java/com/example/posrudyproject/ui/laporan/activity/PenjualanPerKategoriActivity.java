package com.example.posrudyproject.ui.laporan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterKategoriFragment;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterTipeFragment;
import com.example.posrudyproject.ui.laporan.adapter.PenjualanPerKategoriAdapter;
import com.example.posrudyproject.ui.laporan.adapter.PenjualanPerProdukAdapter;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerKategoriItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerProdukItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.List;

public class PenjualanPerKategoriActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvPenjualanPerKategori;
    MaterialButton btnEkspor;
    AppCompatTextView tvTotalTerjual, tvTotalPajak, tvTotPenjualanKotor, tvTotalPenjualan;
    ConstraintLayout layoutEmpty;
    LinearLayoutCompat layoutLaporan;
    private ConstraintLayout btnPilihPeriode;
    private AppCompatTextView mSelectedPeriod;

    List<PenjualanPerKategoriItem> items;
    PenjualanPerKategoriAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_per_kategori);

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
        ((SimpleItemAnimator) rvPenjualanPerKategori.getItemAnimator()).setSupportsChangeAnimations(false);

        //DATA PENJUALAN PER PRODUK LIST
        items = new ArrayList<>();
        for (int i=0; i<10; i++){
            items.add(new PenjualanPerKategoriItem(
                    "EYEWEAR",
                    "Rp 20.000.000",
                    "5",
                    "Rp 20.000.000",
                    "Rp 0"));
        }

        //SET ADAPTER
        adapter = new PenjualanPerKategoriAdapter(items, this);
        rvPenjualanPerKategori.setLayoutManager(new LinearLayoutManager(this));
        rvPenjualanPerKategori.setAdapter(adapter);
        rvPenjualanPerKategori.setHasFixedSize(true);

        //Jika ada list item ilustrasi hilang
        if (adapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
            layoutLaporan.setVisibility(View.VISIBLE);
        }
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_penjualan_kategori_laporan);
        btnPilihPeriode = findViewById(R.id.btn_pilih_periode);
        mSelectedPeriod = findViewById(R.id.tv_selected_period);
        rvPenjualanPerKategori = findViewById(R.id.rv_penjualan_kategori_laporan);
        tvTotalTerjual = findViewById(R.id.tv_total_terjual_per_kategori);
        tvTotalPajak = findViewById(R.id.tv_total_pajak_per_kategori);
        tvTotPenjualanKotor = findViewById(R.id.tv_total_penjualan_kotor_per_kategori);
        tvTotalPenjualan = findViewById(R.id.tv_total_penjualan_per_kategori);
        btnEkspor = findViewById(R.id.btn_ekspor_laporan_kategori);
        layoutLaporan = findViewById(R.id.layout_laporan_penjualan_per_kategori);
        layoutEmpty = findViewById(R.id.layout_ilustrasi_empty_laporan_kategori);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_filter){
                BotSheetFilterKategoriFragment botSheetKategori = new BotSheetFilterKategoriFragment();
                botSheetKategori.setCancelable(false);
                botSheetKategori.show(getSupportFragmentManager(), botSheetKategori.getTag());
                return true;
            }
            return false;
        });
    }
}