package com.example.posrudyproject.ui.penjualan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
import com.example.posrudyproject.ui.penjualan.model.KategoriItem;
import com.example.posrudyproject.ui.penjualan.viewholder.KategoriViewHolder;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriViewHolder> {

    private final List<KategoriItem> kategoriItems;
    private final OnItemClickListener listener;

    public KategoriAdapter(List<KategoriItem> kategoriItems, OnItemClickListener listener) {
        this.kategoriItems = kategoriItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public KategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori, parent, false);
        return new KategoriViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.namaKetegori.setText(kategoriItems.get(position).getNamaKetegori());

        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view,position));
    }

    @Override
    public int getItemCount() {
        return kategoriItems.size();
    }
}
