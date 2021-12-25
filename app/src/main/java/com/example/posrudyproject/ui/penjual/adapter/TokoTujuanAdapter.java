package com.example.posrudyproject.ui.penjual.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penjual.model.TokoItem;
import com.example.posrudyproject.ui.penjual.viewholder.TokoTujuanViewHolder;

import java.util.List;

public class TokoTujuanAdapter extends RecyclerView.Adapter<TokoTujuanViewHolder> {

    private final List<TokoItem> tokoItems;
    private final OnItemClickListener listener;

    public TokoTujuanAdapter(List<TokoItem> tokoItems, OnItemClickListener listener) {
        this.tokoItems = tokoItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TokoTujuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_botsheet, parent, false);
        return new TokoTujuanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TokoTujuanViewHolder holder, int position) {
        TokoItem item = tokoItems.get(position);
        holder.namaToko.setText(item.getNamaToko());

        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view, position));
    }

    @Override
    public int getItemCount() {
        return tokoItems.size();
    }
}
