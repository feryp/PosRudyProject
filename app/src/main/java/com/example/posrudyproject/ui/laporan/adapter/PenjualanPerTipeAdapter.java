package com.example.posrudyproject.ui.laporan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerKategoriItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerTipeItem;
import com.example.posrudyproject.ui.laporan.viewholder.PenjualanPerTipeViewHolder;

import java.util.List;

public class PenjualanPerTipeAdapter extends RecyclerView.Adapter<PenjualanPerTipeViewHolder>{

    private final List<PenjualanPerTipeItem> penjualanPerTipeItems;
    private final Context mContext;

    public PenjualanPerTipeAdapter(List<PenjualanPerTipeItem> penjualanPerTipeItems, Context mContext) {
        this.penjualanPerTipeItems = penjualanPerTipeItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PenjualanPerTipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan_per_tipe,parent,false);
        return new PenjualanPerTipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualanPerTipeViewHolder holder, int position) {
        PenjualanPerTipeItem item = penjualanPerTipeItems.get(position);

        holder.bind(item);
        holder.itemView.setOnClickListener(v -> {
            // Get the current state of the item
            boolean expanded = item.isExpanded();
            // Change the state
            item.setExpanded(!expanded);
            // Notify the adapter that item has changed
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return penjualanPerTipeItems.size();
    }
}
