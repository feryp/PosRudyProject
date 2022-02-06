package com.example.posrudyproject.ui.penyimpanan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.model.BarangMasukItem;
import com.example.posrudyproject.ui.penyimpanan.viewholder.BarangMasukViewHolder;
import com.google.gson.internal.LinkedTreeMap;

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
        Object item = barangMasukItems.get(position);
        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) item;
        holder.tipeBarang.setText(t.get("type_name").toString());
        holder.artikelBarang.setText(t.get("artikel").toString());
        holder.namaBarang.setText(t.get("nama_barang").toString());
        holder.waktu.setText(t.get("tanggal_masuk").toString());
        holder.stokBarang.setText(t.get("kuantitas").toString());
    }

    @Override
    public int getItemCount() {
        return barangMasukItems.size();
    }
}
