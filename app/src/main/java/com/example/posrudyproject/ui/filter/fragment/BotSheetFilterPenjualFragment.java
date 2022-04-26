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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;


public class BotSheetFilterPenjualFragment extends BottomSheetDialogFragment {

    AppCompatImageButton btnClose;
    ChipGroup chipGroup;
    Chip chipUangMasukTerbesar, chipUangMasukTerkecil;
    MaterialButton btnTerapkan;
    public static final String INTENT_FILTER_PENJUAL = "INTENT_FILTER_PENJUAL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_filter_penjual, container, false);

        //INIT VIEW
        btnClose = v.findViewById(R.id.btn_close_botsheet);
        chipUangMasukTerbesar = v.findViewById(R.id.chip_jml_uang_masuk_terbesar);
        chipUangMasukTerkecil = v.findViewById(R.id.chip_jml_uang_masuk_terkecil);
        btnTerapkan = v.findViewById(R.id.btn_terapkan_filter_penjual);
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
                Intent someIntent = new Intent(INTENT_FILTER_PENJUAL);
                int id = 0;
                if (chipUangMasukTerbesar.isChecked()) {
                    id = 1;
                } else if (chipUangMasukTerkecil.isChecked()) {
                    id = 2;
                }

                someIntent.putExtra("OrderBY",id);
                LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).sendBroadcast(someIntent);

                dismiss();
            }
        });
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }
}