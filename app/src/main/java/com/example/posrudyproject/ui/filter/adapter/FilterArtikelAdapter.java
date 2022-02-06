package com.example.posrudyproject.ui.filter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.filter.viewholder.FilterArtikelViewHolder;
import com.example.posrudyproject.ui.penjualan.model.ArtikelItem;

import java.util.List;

public class FilterArtikelAdapter extends RecyclerView.Adapter<FilterArtikelViewHolder> {

    private final List<ArtikelItem> artikelItems;
    private final OnItemClickListener listener;

    public FilterArtikelAdapter(List<ArtikelItem> artikelItems, OnItemClickListener listener) {
        this.artikelItems = artikelItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FilterArtikelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_botsheet, parent, false);
        return new FilterArtikelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterArtikelViewHolder holder, int position) {
        ArtikelItem item = artikelItems.get(position);
        holder.artikelBarang.setText(item.getNamaArtikel());

        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view, position));
    }

    @Override
    public int getItemCount() {
        return artikelItems.size();
    }
}
