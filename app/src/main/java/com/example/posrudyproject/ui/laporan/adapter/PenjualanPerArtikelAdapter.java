package com.example.posrudyproject.ui.laporan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerArtikelItem;
import com.example.posrudyproject.ui.laporan.viewholder.PenjualanPerArtikelViewHolder;

import java.util.List;

public class PenjualanPerArtikelAdapter extends RecyclerView.Adapter<PenjualanPerArtikelViewHolder> {

    private final List<PenjualanPerArtikelItem> perArtikelItemList;
    private final Context mContext;

    public PenjualanPerArtikelAdapter(List<PenjualanPerArtikelItem> perArtikelItemList, Context mContext) {
        this.perArtikelItemList = perArtikelItemList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PenjualanPerArtikelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan_per_artikel,parent,false);
        return new PenjualanPerArtikelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualanPerArtikelViewHolder holder, int position) {
        PenjualanPerArtikelItem item = perArtikelItemList.get(position);

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
        return perArtikelItemList.size();
    }
}
