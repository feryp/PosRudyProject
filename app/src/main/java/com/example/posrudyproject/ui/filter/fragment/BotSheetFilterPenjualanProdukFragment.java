package com.example.posrudyproject.ui.filter.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.activity.PenjualanPerProdukActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class BotSheetFilterPenjualanProdukFragment extends BottomSheetDialogFragment {

    AppCompatImageButton btnClose;
    ChipGroup chipGroup;
    Chip chipNamaProdukAZ, chipNamaProdukZA, chipKategoriAZ, chipKategoriZA, chipPenjualanTerbesar,
         chipPenjualanTerkecil, chipPenKotorTerbesar, chipPenKotorTerkecil;
    MaterialButton btnTerapkan;
    public static final String INTENT_FILTER_PRODUK = "INTENT_FILTER_PRODUK";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_filter_penjualan_produk, container, false);

        //INIT VIEW
        btnClose = v.findViewById(R.id.btn_close_botsheet);
        chipNamaProdukAZ = v.findViewById(R.id.chip_nama_produk_a_z);
        chipNamaProdukZA = v.findViewById(R.id.chip_nama_produk_z_a);
        chipKategoriZA = v.findViewById(R.id.chip_kategori_z_a);
        chipKategoriAZ = v.findViewById(R.id.chip_kategori_a_z);
        chipPenjualanTerbesar = v.findViewById(R.id.chip_penjualan_terbesar);
        chipPenjualanTerkecil = v.findViewById(R.id.chip_penjualan_terkecil);
        chipPenKotorTerbesar = v.findViewById(R.id.chip_penjualan_kotor_terbesar);
        chipPenKotorTerkecil = v.findViewById(R.id.chip_penjualan_kotor_terkecil);
        btnTerapkan = v.findViewById(R.id.btn_terapkan_filter_produk);
        chipGroup = v.findViewById(R.id.chip_group);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnTerapkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent someIntent = new Intent(INTENT_FILTER_PRODUK);
                //TODO put extras to your intent
                int id = 0;
                if (chipNamaProdukAZ.isChecked()) {
                    id = 1;
                } else if (chipNamaProdukZA.isChecked()) {
                    id = 2;
                } else if (chipKategoriAZ.isChecked()) {
                    id = 3;
                } else if (chipKategoriZA.isChecked()) {
                    id = 4;
                } else if (chipPenjualanTerbesar.isChecked()) {
                    id = 5;
                } else if (chipPenjualanTerkecil.isChecked()) {
                    id = 6;
                } else if (chipPenKotorTerbesar.isChecked()) {
                    id = 7;
                } else if (chipPenKotorTerkecil.isChecked()) {
                    id = 8;
                }

                someIntent.putExtra("OrderBY",id);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(someIntent);

                dismiss();
            }
        });
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }
}