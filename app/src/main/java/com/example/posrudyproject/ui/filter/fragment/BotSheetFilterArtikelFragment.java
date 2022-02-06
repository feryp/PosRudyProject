package com.example.posrudyproject.ui.filter.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.filter.adapter.FilterArtikelAdapter;
import com.example.posrudyproject.ui.filter.adapter.FilterTipeAdapter;
import com.example.posrudyproject.ui.penjualan.model.ArtikelItem;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BotSheetFilterArtikelFragment extends BottomSheetDialogFragment implements OnItemClickListener {

    RecyclerView rvArtikelBarang;
    AppCompatImageButton btnClose;
    SearchView searchView;
    FilterArtikelAdapter adapter;
    List<ArtikelItem> artikelItem;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_filter_artikel, container, false);

        //INIT VIEW
        rvArtikelBarang = v.findViewById(R.id.rv_artikel_botsheet);
        btnClose = v.findViewById(R.id.btn_close_botsheet);
        searchView = v.findViewById(R.id.search_filter_artikel);

        //Tipe List
        artikelItem = new ArrayList<>();
        for (int i=0; i<50; i++){
            artikelItem.add(new ArtikelItem("SP633846-0011"));
        }

        //Setup Adapter
        adapter = new FilterArtikelAdapter(artikelItem, this);
        rvArtikelBarang.setLayoutManager(new LinearLayoutManager(getContext()));
        rvArtikelBarang.setAdapter(adapter);
        rvArtikelBarang.setHasFixedSize(true);

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
        Toast.makeText(getContext(), "Pilih " + artikelItem.get(position).getNamaArtikel(), Toast.LENGTH_SHORT).show();
        //SELECT ITEM FILTER
    }
}