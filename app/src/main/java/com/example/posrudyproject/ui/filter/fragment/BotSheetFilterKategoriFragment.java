package com.example.posrudyproject.ui.filter.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.filter.adapter.FilterKategoriAdapter;
import com.example.posrudyproject.ui.filter.adapter.FilterTipeAdapter;
import com.example.posrudyproject.ui.penjualan.model.KategoriItem;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;


public class BotSheetFilterKategoriFragment extends BottomSheetDialogFragment implements OnItemClickListener {

    RecyclerView rvKategoriBarang;
    AppCompatImageButton btnClose;
    FilterKategoriAdapter adapter;
    List<KategoriItem> kategoriItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_filter_kategori, container, false);

        //INIT VIEW
        rvKategoriBarang = v.findViewById(R.id.rv_kategori_botsheet);
        btnClose = v.findViewById(R.id.btn_close_botsheet);

        //Tipe List
        kategoriItems = new ArrayList<>();
        kategoriItems.add(new KategoriItem("BIKE"));
        kategoriItems.add(new KategoriItem("BIKE HELMETS"));
        kategoriItems.add(new KategoriItem("RX/OPTICAL"));
        kategoriItems.add(new KategoriItem("RUN"));
        kategoriItems.add(new KategoriItem("OUTDOOR"));
        kategoriItems.add(new KategoriItem("GOLF"));
        kategoriItems.add(new KategoriItem("WATER SPORT"));

        //Setup Adapter
        adapter = new FilterKategoriAdapter(kategoriItems, this);
        rvKategoriBarang.setLayoutManager(new LinearLayoutManager(getContext()));
        rvKategoriBarang.setAdapter(adapter);
        rvKategoriBarang.setHasFixedSize(true);

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

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(getContext(), "Pilih " + kategoriItems.get(position).getNamaKetegori(), Toast.LENGTH_SHORT).show();
        //SELECT ITEM FILTER
    }
}