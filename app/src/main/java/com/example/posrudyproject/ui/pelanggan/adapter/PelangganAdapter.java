package com.example.posrudyproject.ui.pelanggan.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pelanggan.model.Pelanggan;
import com.example.posrudyproject.ui.pelanggan.viewholder.PelangganViewHolder;

import java.util.List;

public class PelangganAdapter extends RecyclerView.Adapter<PelangganViewHolder> {

    private final List<Pelanggan> pelangganItems;
    private final OnItemClickListener listener;

    public PelangganAdapter(List<Pelanggan> pelangganItems, OnItemClickListener listener) {
        this.pelangganItems = pelangganItems;
        this.listener = listener;
    }

    @Override
    public PelangganViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelanggan, parent, false);
        return new PelangganViewHolder(v);
    }

    @Override
    public void onBindViewHolder( PelangganViewHolder holder, int position) {
        Pelanggan item = pelangganItems.get(position);
        holder.namaPelanggan.setText(item.getNama_pelanggan());
        holder.noHpPelanggan.setText(item.getNo_hp());

        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view, position));
        holder.btnEdit.setOnClickListener(view -> listener.onItemClickListener(view, position));
    }

    @Override
    public int getItemCount() {
        return pelangganItems.size();
    }
}
