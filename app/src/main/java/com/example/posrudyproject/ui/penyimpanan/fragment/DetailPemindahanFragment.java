package com.example.posrudyproject.ui.penyimpanan.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.adapter.PemindahanBarangAdapter;
import com.example.posrudyproject.ui.penyimpanan.adapter.PreviewPemindahanAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.PemindahanBarangItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetailPemindahanFragment extends Fragment {

    AppCompatTextView tvTokoTujuan, tvNamaPenjual, tvCatatan;
    RecyclerView rvPreviewBarang;

    List<PemindahanBarangItem> pemindahanBarangItems;
    PreviewPemindahanAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_pemindahan, container, false);

        //INIT VIEW
        tvTokoTujuan = v.findViewById(R.id.tv_toko_preview_pemindahan);
        tvNamaPenjual = v.findViewById(R.id.tv_penjual_preview_pemindahan);
        tvCatatan = v.findViewById(R.id.tv_catatan_preview_pemindahan);
        rvPreviewBarang = v.findViewById(R.id.rv_preview_pemindahan_barang);

        SharedPreferences pemindahanPreferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
        pemindahanBarangItems = new ArrayList<>();
        String json = pemindahanPreferences.getString("pemindahanBarangItems","");
        int id_toko_tujuan = pemindahanPreferences.getInt("tokoTujuanId",0);
        tvTokoTujuan.setText(pemindahanPreferences.getString("tokoTujuanName",""));
        int id_penjual = pemindahanPreferences.getInt("penjualId",0);
        tvNamaPenjual.setText(pemindahanPreferences.getString("penjualName",""));
        String catatan = pemindahanPreferences.getString("catatan","");
        tvCatatan.setText(pemindahanPreferences.getString("catatan",""));
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<PemindahanBarangItem>>(){}.getType();
            pemindahanBarangItems = gson.fromJson(json, type);
        }

        //Barang List;
        /*for (int i=0; i<10; i++){
            pemindahanBarangItems.add(new PemindahanBarangItem(
                    "",
                    "CUTLINE",
                    "634343234",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "50"

            ));
        }*/

        //Setup Adapter
        adapter = new PreviewPemindahanAdapter(pemindahanBarangItems, getActivity());
        rvPreviewBarang.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPreviewBarang.setAdapter(adapter);
        rvPreviewBarang.setHasFixedSize(true);

        return v;
    }
}