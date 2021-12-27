package com.example.posrudyproject.ui.penyimpanan.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.adapter.BarangMasukAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.BarangMasukItem;

import java.util.ArrayList;
import java.util.List;

public class BarangMasukFragment extends Fragment {

    ConstraintLayout layoutEmpty;
    SearchView cariBarang;
    RecyclerView rvBarangMasuk;
    BarangMasukAdapter adapter;
    List<BarangMasukItem> barangMasukItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_barang_masuk, container, false);

        //INIT VIEW
        layoutEmpty = v.findViewById(R.id.layout_ilustrasi_empty_barang_masuk);
        cariBarang = v.findViewById(R.id.search_barang_masuk);
        rvBarangMasuk = v.findViewById(R.id.rv_barang_masuk);

        //Barang Masuk List
        barangMasukItems = new ArrayList<>();
        for (int i=0; i<50; i++){
            barangMasukItems.add(new BarangMasukItem(
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "10-08-2021, 15:55",
                    "200 Pcs"
            ));
        }

        //Setup adapter
        adapter = new BarangMasukAdapter(barangMasukItems, getActivity());
        rvBarangMasuk.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBarangMasuk.setAdapter(adapter);
        rvBarangMasuk.setHasFixedSize(true);

        //Jikaada list item ilustrasi hilang
        if (adapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
        }

        return v;
    }
}