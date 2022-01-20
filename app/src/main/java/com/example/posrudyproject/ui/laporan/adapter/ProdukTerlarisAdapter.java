package com.example.posrudyproject.ui.laporan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.ProdukTerlarisItem;
import com.example.posrudyproject.ui.laporan.viewholder.ProdukTerlarisViewHolder;

import java.util.List;

public class ProdukTerlarisAdapter extends RecyclerView.Adapter<ProdukTerlarisViewHolder>{

    private final List<ProdukTerlarisItem> produkTerlarisItems;
    private final Context mContext;

    public ProdukTerlarisAdapter(List<ProdukTerlarisItem> produkTerlarisItems, Context mContext) {
        this.produkTerlarisItems = produkTerlarisItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ProdukTerlarisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rangkuman_laporan,parent,false);
        return new ProdukTerlarisViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukTerlarisViewHolder holder, int position) {
        ProdukTerlarisItem item = produkTerlarisItems.get(position);
        holder.namaProduk.setText(item.getNamaProduk());
    }

    @Override
    public int getItemCount() {
        return produkTerlarisItems.size();
    }
}
