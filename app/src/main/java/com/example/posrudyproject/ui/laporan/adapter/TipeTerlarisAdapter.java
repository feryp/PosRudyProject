package com.example.posrudyproject.ui.laporan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.TipeTerlarisItem;
import com.example.posrudyproject.ui.laporan.viewholder.TipeTerlarisViewHolder;

import java.util.List;

public class TipeTerlarisAdapter extends RecyclerView.Adapter<TipeTerlarisViewHolder>{

    private final List<TipeTerlarisItem> tipeTerlarisItems;
    private final Context mContext;

    public TipeTerlarisAdapter(List<TipeTerlarisItem> tipeTerlarisItems, Context mContext) {
        this.tipeTerlarisItems = tipeTerlarisItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TipeTerlarisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rangkuman_laporan,parent,false);
        return new TipeTerlarisViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TipeTerlarisViewHolder holder, int position) {
        TipeTerlarisItem item = tipeTerlarisItems.get(position);
        holder.namaTipe.setText(item.getNamaTipe());
    }

    @Override
    public int getItemCount() {
        return tipeTerlarisItems.size();
    }
}
