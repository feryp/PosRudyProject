package com.example.posrudyproject.ui.filter.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.filter.viewholder.FilterTipeViewHolder;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;

import java.util.List;

public class FilterTipeAdapter extends RecyclerView.Adapter<FilterTipeViewHolder> {

    private final List<TipeItem> tipeItems;
    private final OnItemClickListener listener;

    public FilterTipeAdapter(List<TipeItem> tipeItems, OnItemClickListener listener) {
        this.tipeItems = tipeItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FilterTipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tipe_botsheet, parent, false);
        return new FilterTipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterTipeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tipeBarang.setText(tipeItems.get(position).getNamaTipe());

        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view, position));
    }

    @Override
    public int getItemCount() {
        return tipeItems.size();
    }
}
