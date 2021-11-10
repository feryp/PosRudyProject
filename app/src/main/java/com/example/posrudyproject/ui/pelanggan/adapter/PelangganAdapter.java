package com.example.posrudyproject.ui.pelanggan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pelanggan.model.PelangganItem;
import com.example.posrudyproject.ui.pelanggan.viewholder.PelangganViewHolder;

import java.util.List;

public class PelangganAdapter extends RecyclerView.Adapter<PelangganViewHolder> {

    private final List<PelangganItem> pelangganItems;
    private final OnItemClickListener listener;

    public PelangganAdapter(List<PelangganItem> pelangganItems, OnItemClickListener listener) {
        this.pelangganItems = pelangganItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PelangganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelanggan, parent, false);
        return new PelangganViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PelangganViewHolder holder, int position) {
        holder.namaPelanggan.setText(pelangganItems.get(position).getNamaPelanggan());
        holder.noHpPelanggan.setText(pelangganItems.get(position).getNohpPelanggan());

        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view, position));
    }

    @Override
    public int getItemCount() {
        return pelangganItems.size();
    }
}
