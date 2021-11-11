package com.example.posrudyproject.ui.penjual.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penjual.model.PenjualItem;
import com.example.posrudyproject.ui.penjual.viewholder.PenjualViewHolder;
import com.example.posrudyproject.ui.penjualan.model.PenjualanItem;

import java.util.List;

public class PenjualAdapter extends RecyclerView.Adapter<PenjualViewHolder> {

    private final List<PenjualItem> penjualItems;
    private final OnItemClickListener listener;

    public PenjualAdapter(List<PenjualItem> penjualItems, OnItemClickListener listener) {
        this.penjualItems = penjualItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PenjualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penjual, parent, false);
        return new PenjualViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualViewHolder holder, int position) {
        PenjualItem item = penjualItems.get(position);
        holder.fotoPenjual.setImageResource(item.getImPenjual());
        holder.namaPenjual.setText(item.getNamaPenjual());
        holder.jabatanPenjual.setText(item.getJabatanPenjual());

        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view, position));
    }

    @Override
    public int getItemCount() {
        return penjualItems.size();
    }
}
