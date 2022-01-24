package com.example.posrudyproject.ui.filter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.filter.viewholder.FilterKategoriViewHolder;
import com.example.posrudyproject.ui.penjualan.model.KategoriItem;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;

import java.util.List;

public class FilterKategoriAdapter extends RecyclerView.Adapter<FilterKategoriViewHolder> {

    private final List<KategoriItem> kategoriItems;
    private final OnItemClickListener listener;

    public FilterKategoriAdapter(List<KategoriItem> kategoriItems, OnItemClickListener listener) {
        this.kategoriItems = kategoriItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FilterKategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_botsheet, parent, false);
        return new FilterKategoriViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterKategoriViewHolder holder, int position) {
        KategoriItem item = kategoriItems.get(position);
        holder.kategoriBarang.setText(item.getNamaKetegori());

        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view, position));
    }

    @Override
    public int getItemCount() {
        return kategoriItems.size();
    }
}
