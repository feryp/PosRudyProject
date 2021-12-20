package com.example.posrudyproject.ui.penyimpanan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.model.BarangPindahItem;
import com.example.posrudyproject.ui.penyimpanan.viewholder.BarangPindahViewHolder;

import java.util.List;

public class BarangPindahAdapter extends RecyclerView.Adapter<BarangPindahViewHolder> {

    private final List<BarangPindahItem> barangPindahItems;
    private final OnItemClickListener listener;

    public BarangPindahAdapter(List<BarangPindahItem> barangPindahItems, OnItemClickListener listener) {
        this.barangPindahItems = barangPindahItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BarangPindahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_pindah,parent,false);
        return new BarangPindahViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangPindahViewHolder holder, int position) {
        BarangPindahItem item = barangPindahItems.get(position);
        holder.noDokumen.setText(item.getNoDocBarang());
        holder.waktu.setText(item.getWaktuBarangPindah());
        holder.jumlahBarang.setText(item.getJumlahBarangPindah());
        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view,position));
    }

    @Override
    public int getItemCount() {
        return barangPindahItems.size();
    }
}
