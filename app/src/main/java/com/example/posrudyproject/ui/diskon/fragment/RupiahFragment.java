package com.example.posrudyproject.ui.diskon.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


public class RupiahFragment extends Fragment implements View.OnClickListener{

    TextInputEditText etJumlahDiskon, etDeskripsi;
    MaterialButton btnBatal, btnTambah;
    public static final String INTENT_DISKON = "INTENT_DISKON";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rupiah, container, false);

        //INIT VIEW
        etJumlahDiskon = v.findViewById(R.id.et_jumlah_rupiah_diskon);
        etDeskripsi = v.findViewById(R.id.et_desk_rupiah_diskon);
        btnBatal = v.findViewById(R.id.btn_batal_rupiah_diskon);
        btnTambah = v.findViewById(R.id.btn_tambah_rupiah_diskon);

        //SET LISTENER
        btnBatal.setOnClickListener(RupiahFragment.this);
        btnTambah.setOnClickListener(RupiahFragment.this);

        return v;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_batal_rupiah_diskon:
                //FUNCTION
                BotSheetDiskonFragment.getInstance().DismissParent();
                break;
            case R.id.btn_tambah_rupiah_diskon:
                //FUNCTION
                Intent someIntent = new Intent(INTENT_DISKON);
                someIntent.putExtra("diskon_rupiah",etJumlahDiskon.getText().toString());
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(someIntent);

                BotSheetDiskonFragment.getInstance().DismissParent();

                break;
        }
    }
}