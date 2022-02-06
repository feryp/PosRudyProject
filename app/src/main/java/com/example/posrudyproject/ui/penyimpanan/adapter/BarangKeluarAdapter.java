package com.example.posrudyproject.ui.penyimpanan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.model.BarangKeluarItem;
import com.example.posrudyproject.ui.penyimpanan.viewholder.BarangKeluarViewHolder;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

public class BarangKeluarAdapter extends RecyclerView.Adapter<BarangKeluarViewHolder> {

    private final List<BarangKeluarItem> barangKeluarItems;
    private final Context mContext;

    public BarangKeluarAdapter(List<BarangKeluarItem> barangKeluarItems, Context mContext) {
        this.barangKeluarItems = barangKeluarItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BarangKeluarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_keluar,parent,false);
        return new BarangKeluarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangKeluarViewHolder holder, int position) {
        Object item = barangKeluarItems.get(position);
        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) item;
        holder.tipeBarang.setText(t.get("type_name").toString());
        holder.artikelBarang.setText(t.get("artikel").toString());
        holder.namaBarang.setText(t.get("nama_barang").toString());
        holder.waktu.setText(t.get("tanggal_keluar").toString());
        holder.stokBarang.setText(String.valueOf(t.get("kuantitas")));
    }

    @Override
    public int getItemCount() {
        return barangKeluarItems.size();
    }
}
