package com.example.posrudyproject.ui.produk.fragment;

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
import com.example.posrudyproject.ui.produk.adapter.ProdukCustomAdapter;
import com.example.posrudyproject.ui.produk.model.ProdukItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BotSheetProdukFragment extends BottomSheetDialogFragment implements OnItemClickListener {

    AppCompatImageButton btnClose;
    RecyclerView rvListBarang;

    List<ProdukItem> produkItems;
    ProdukCustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_produk, container, false);

        //INIT VIEW
        btnClose = v.findViewById(R.id.btn_close_botsheet);
        rvListBarang = v.findViewById(R.id.rv_list_barang_custom);

        //Produk Tersedia
        produkItems = new ArrayList<>();
        for (int i=0; i<50; i++){
            produkItems.add(new ProdukItem(
                    R.drawable.im_example,
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "30"
            ));
        }

        //Setup Adapter Produk Tersedia
        adapter = new ProdukCustomAdapter(produkItems, this);
        rvListBarang.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvListBarang.setAdapter(adapter);
        rvListBarang.setHasFixedSize(true);

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
        Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();
    }
}