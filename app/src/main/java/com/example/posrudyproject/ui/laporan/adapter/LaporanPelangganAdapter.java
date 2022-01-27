package com.example.posrudyproject.ui.laporan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.activity.DetailLaporanPelangganActivity;
import com.example.posrudyproject.ui.laporan.model.LaporanPelangganItem;
import com.example.posrudyproject.ui.laporan.viewholder.LaporanPelangganViewHolder;

import java.util.List;

public class LaporanPelangganAdapter extends RecyclerView.Adapter<LaporanPelangganViewHolder> {

    private final List<LaporanPelangganItem> pelangganItems;
    private final OnItemClickListener listener;

    public LaporanPelangganAdapter(List<LaporanPelangganItem> pelangganItems, OnItemClickListener listener) {
        this.pelangganItems = pelangganItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LaporanPelangganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan_pelanggan,parent,false);
        return new LaporanPelangganViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanPelangganViewHolder holder, int position) {
        LaporanPelangganItem item = pelangganItems.get(position);

        holder.bind(item);
        holder.imArrow.setOnClickListener(v -> {
            // Get the current state of the item
            boolean expanded = item.isExpanded();
            // Change the state
            item.setExpanded(!expanded);
            // Notify the adapter that item has changed
            notifyItemChanged(position);
        });

        holder.btnDetail.setOnClickListener(v -> listener.onItemClickListener(v, position));
    }

    @Override
    public int getItemCount() {
        return pelangganItems.size();
    }
}
