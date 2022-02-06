package com.example.posrudyproject.ui.filter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.filter.adapter.FilterTipeAdapter;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BotSheetFilterTipeFragment extends BottomSheetDialogFragment implements OnItemClickListener {

    RecyclerView rvTipeBarang;
    AppCompatImageButton btnClose;
    SearchView searchView;
    FilterTipeAdapter adapter;
    List<TipeItem> tipeItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_filter_tipe, container, false);

        //INIT VIEW
        rvTipeBarang = v.findViewById(R.id.rv_tipe_botsheet);
        btnClose = v.findViewById(R.id.btn_close_botsheet);
        searchView = v.findViewById(R.id.search_filter_tipe);

        //Tipe List
        tipeItems = new ArrayList<>();
        for (int i=0; i<50; i++){
            tipeItems.add(new TipeItem("CUTLINE"));
        }

        //Setup Adapter
        adapter = new FilterTipeAdapter(tipeItems, this);
        rvTipeBarang.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTipeBarang.setAdapter(adapter);
        rvTipeBarang.setHasFixedSize(true);

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
        Toast.makeText(getContext(), "Pilih " + tipeItems.get(position).getNamaTipe(), Toast.LENGTH_SHORT).show();
        //SELECT ITEM FILTER
    }
}