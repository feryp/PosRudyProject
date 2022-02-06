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

public class BotSheetFilterPelangganFragment extends BottomSheetDialogFragment {

    AppCompatImageButton btnClose;
    Chip chipKedatanganTerbesar, chipKedatanganTerkecil, chipTotTransaksiTerbesar, chipTotTransaksiTerkecil;
    MaterialButton btnTerapkan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_filter_pelanggan, container, false);

        //INIT VIEW
        btnClose = v.findViewById(R.id.btn_close_botsheet);
        chipKedatanganTerbesar = v.findViewById(R.id.chip_kedatangan_terbesar);
        chipKedatanganTerkecil = v.findViewById(R.id.chip_kedatangan_terkecil);
        chipTotTransaksiTerbesar = v.findViewById(R.id.chip_total_transaksi_terbesar);
        chipTotTransaksiTerkecil = v.findViewById(R.id.chip_total_transaksi_terkecil);
        btnTerapkan = v.findViewById(R.id.btn_terapkan_filter_pelanggan);

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