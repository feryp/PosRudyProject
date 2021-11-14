package com.example.posrudyproject.ui.penjual.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penjual.model.PenjualItem;
import com.example.posrudyproject.ui.penjual.viewholder.PenjualViewHolder;

import java.util.List;

public class PenjualAdapter extends RecyclerView.Adapter<PenjualViewHolder> {

    private final List<PenjualItem> penjualItems;
    private final OnItemClickListener listener;

    PenjualItem selectedItemPositon = null;

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
    public void onBindViewHolder(@NonNull PenjualViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PenjualItem item = penjualItems.get(position);
        holder.fotoPenjual.setImageResource(item.getImPenjual());
        holder.namaPenjual.setText(item.getNamaPenjual());
        holder.jabatanPenjual.setText(item.getJabatanPenjual());

        if (selectedItemPositon != null){
            if (selectedItemPositon.getNamaPenjual().equals(penjualItems.get(position).getNamaPenjual())){
                holder.itemView.setBackgroundResource(R.drawable.bg_rounded_neon_blue_shadow_outlined);
            } else {
                holder.itemView.setBackgroundResource(R.drawable.bg_container_shadow);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if (listener != null) listener.onItemClickListener(view, position);
                selectItem(penjualItems.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return penjualItems.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void selectItem(PenjualItem selected){
        selectedItemPositon = selected;
        notifyDataSetChanged();
    }

}
