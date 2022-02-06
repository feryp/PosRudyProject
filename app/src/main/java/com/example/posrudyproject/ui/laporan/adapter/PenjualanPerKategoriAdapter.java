package com.example.posrudyproject.ui.laporan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerKategoriItem;
import com.example.posrudyproject.ui.laporan.viewholder.PenjualanPerKategoriViewHolder;

import java.util.List;

public class PenjualanPerKategoriAdapter extends RecyclerView.Adapter<PenjualanPerKategoriViewHolder> {

    private final List<PenjualanPerKategoriItem> perKategoriItemList;
    private final Context mContext;

    public PenjualanPerKategoriAdapter(List<PenjualanPerKategoriItem> perKategoriItemList, Context mContext) {
        this.perKategoriItemList = perKategoriItemList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PenjualanPerKategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan_per_kategori,parent,false);
        return new PenjualanPerKategoriViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualanPerKategoriViewHolder holder, int position) {
        PenjualanPerKategoriItem item = perKategoriItemList.get(position);

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
        return perKategoriItemList.size();
    }
}
