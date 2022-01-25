package com.example.posrudyproject.ui.laporan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerProdukItem;
import com.example.posrudyproject.ui.laporan.viewholder.PenjualanPerProdukViewHolder;

import java.util.List;

public class PenjualanPerProdukAdapter extends RecyclerView.Adapter<PenjualanPerProdukViewHolder> {

    private final List<PenjualanPerProdukItem> perProdukItemList;
    private final Context mContext;

    public PenjualanPerProdukAdapter(List<PenjualanPerProdukItem> perProdukItemList, Context mContext) {
        this.perProdukItemList = perProdukItemList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PenjualanPerProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan_per_produk,parent,false);
        return new PenjualanPerProdukViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualanPerProdukViewHolder holder, int position) {
        PenjualanPerProdukItem item = perProdukItemList.get(position);

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
        return perProdukItemList.size();
    }
}
