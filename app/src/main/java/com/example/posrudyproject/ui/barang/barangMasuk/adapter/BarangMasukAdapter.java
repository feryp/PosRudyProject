package com.example.posrudyproject.ui.barang.barangMasuk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.barang.barangMasuk.model.BarangMasukItem;
import com.example.posrudyproject.ui.barang.barangMasuk.viewholder.BarangMasukViewHolder;

import java.util.List;

public class BarangMasukAdapter extends RecyclerView.Adapter<BarangMasukViewHolder> {

    private final List<BarangMasukItem> barangMasukItems;
    private final Context mContext;

    public BarangMasukAdapter(List<BarangMasukItem> barangMasukItems, Context mContext) {
        this.barangMasukItems = barangMasukItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BarangMasukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_masuk,parent,false);
        return new BarangMasukViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangMasukViewHolder holder, int position) {
        BarangMasukItem item = barangMasukItems.get(position);
        holder.tipeBarang.setText(item.getTipeBarangMasuk());
        holder.artikelBarang.setText(item.getArtikelBarangMasuk());
        holder.namaBarang.setText(item.getNamaBarangMasuk());
        holder.waktu.setText(item.getWaktuBarangMasuk());
        holder.stokBarang.setText(item.getStokBarangMasuk());
    }

    @Override
    public int getItemCount() {
        return barangMasukItems.size();
    }
}
