package com.example.posrudyproject.ui.filter.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

public class BotSheetFilterPenjualanProdukFragment extends BottomSheetDialogFragment {

    AppCompatImageButton btnClose;
    Chip chipNamaProdukAZ, chipNamaProdukZA, chipKategoriAZ, chipKategoriZA, chipPenjualanTerbesar,
         chipPenjualanTerkecil, chipPenKotorTerbesar, chipPenKotorTerkecil;
    MaterialButton btnTerapkan;

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
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }
}