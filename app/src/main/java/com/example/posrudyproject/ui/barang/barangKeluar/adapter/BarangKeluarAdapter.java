package com.example.posrudyproject.ui.barang.barangKeluar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.barang.barangKeluar.model.BarangKeluarItem;
import com.example.posrudyproject.ui.barang.barangKeluar.viewholder.BarangKeluarViewHolder;
import com.example.posrudyproject.ui.barang.barangMasuk.model.BarangMasukItem;

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
        BarangKeluarItem item = barangKeluarItems.get(position);
        holder.tipeBarang.setText(item.getTipeBarangKeluar());
        holder.artikelBarang.setText(item.getArtikelBarangKeluar());
        holder.namaBarang.setText(item.getNamaBarangKeluar());
        holder.waktu.setText(item.getWaktuBarangKeluark());
        holder.stokBarang.setText(item.getStokBarangKeluar());
    }

    @Override
    public int getItemCount() {
        return barangKeluarItems.size();
    }
}
