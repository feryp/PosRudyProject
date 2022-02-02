package com.example.posrudyproject.ui.laporan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.LaporanPenjualItem;
import com.example.posrudyproject.ui.laporan.viewholder.LaporanPenjualViewHolder;

import java.util.List;

public class LaporanPenjualAdapter extends RecyclerView.Adapter<LaporanPenjualViewHolder> {

    private final List<LaporanPenjualItem> penjualItems;
    private final OnItemClickListener listener;

    public LaporanPenjualAdapter(List<LaporanPenjualItem> penjualItems, OnItemClickListener listener) {
        this.penjualItems = penjualItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LaporanPenjualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan_penjual,parent,false);
        return new LaporanPenjualViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanPenjualViewHolder holder, int position) {
        LaporanPenjualItem item = penjualItems.get(position);
        holder.namaPenjual.setText(item.getNamaPenjual());
        holder.nominalTransaksiPenjual.setText(item.getNominalTransaksiPenjual());
        holder.totTransaksiPenjual.setText(item.getTotalTransaksiPenjual());

        holder.itemView.setOnClickListener(v -> listener.onItemClickListener(v, position));
    }

    @Override
    public int getItemCount() {
        return penjualItems.size();
    }
}
