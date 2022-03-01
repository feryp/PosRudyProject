package com.example.posrudyproject.ui.penyimpanan.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.filter.adapter.NothingSelectedSpinnerAdapter;
import com.example.posrudyproject.ui.penjual.model.PenjualItem;
import com.example.posrudyproject.ui.penjual.model.TokoItem;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataTokoFragment extends Fragment {

    AppCompatSpinner spinTokoTujuan, spinPenjual;
    TextInputEditText etCatatan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_data_toko, container, false);

        //INIT VIEW
        spinTokoTujuan = v.findViewById(R.id.spinner_toko_tujuan_pemindahan);
        spinPenjual = v.findViewById(R.id.spinner_penjual_pemindahan);
        etCatatan = v.findViewById(R.id.et_catatan_pemindahan);

        //SPINNER TOKO TUJUAN
        // Spinner Drop down elements
        List<TokoItem> tokoItems = new ArrayList<>();
        for (int i=0; i<50; i++){
            tokoItems.add(new TokoItem(1,"Toko Pusat"));
        }

        //Update object to string
        List<String> tokoItem = new ArrayList<String>();
        for (TokoItem item : tokoItems){
            tokoItem.add(item.getNamaToko());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> tokoAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, tokoItem);
        tokoAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        spinTokoTujuan.setAdapter(new NothingSelectedSpinnerAdapter(tokoAdapter, getActivity(), R.layout.spinner_text_nothing_selected, getString(R.string.hint_spinner_pilih_toko)));

        //SPINNER PENJUAL
        // Spinner Drop down elements
        List<PenjualItem> penjualItems = new ArrayList<>();
        penjualItems.add(new PenjualItem(1,new Date(),"baginda isfa",1,"Kantor Pusat",1,"Store BSD","Penjual","","","",0,1,""));
        penjualItems.add(new PenjualItem(1,new Date(),"baginda isfa",1,"Kantor Pusat",1,"Store BSD","Penjual","","","",0,1,""));
        penjualItems.add(new PenjualItem(1,new Date(),"baginda isfa",1,"Kantor Pusat",1,"Store BSD","Penjual","","","",0,1,""));
        penjualItems.add(new PenjualItem(1,new Date(),"baginda isfa",1,"Kantor Pusat",1,"Store BSD","Penjual","","","",0,1,""));
        penjualItems.add(new PenjualItem(1,new Date(),"baginda isfa",1,"Kantor Pusat",1,"Store BSD","Penjual","","","",0,1,""));
        penjualItems.add(new PenjualItem(1,new Date(),"baginda isfa",1,"Kantor Pusat",1,"Store BSD","Penjual","","","",0,1,""));
        penjualItems.add(new PenjualItem(1,new Date(),"baginda isfa",1,"Kantor Pusat",1,"Store BSD","Penjual","","","",0,1,""));

        //Update object to string
        List<String> penjualItem = new ArrayList<String>();
        for (PenjualItem item : penjualItems){
            penjualItem.add(item.getNama_karyawan());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> penjualAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, penjualItem);
        penjualAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        spinPenjual.setAdapter(new NothingSelectedSpinnerAdapter(penjualAdapter, getActivity(), R.layout.spinner_text_nothing_selected, getString(R.string.hint_spinner_pilih_penjual)));


        return v;
    }
}