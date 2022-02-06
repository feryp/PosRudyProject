package com.example.posrudyproject.ui.laporan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.KategoriTerlarisItem;
import com.example.posrudyproject.ui.laporan.viewholder.KategoriTerlarisViewHolder;

import java.util.List;

public class KategoriTerlarisAdapter extends RecyclerView.Adapter<KategoriTerlarisViewHolder>{

    private final List<KategoriTerlarisItem> kategoriTerlarisItems;
    private final Context mContext;

    public KategoriTerlarisAdapter(List<KategoriTerlarisItem> kategoriTerlarisItems, Context mContext) {
        this.kategoriTerlarisItems = kategoriTerlarisItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public KategoriTerlarisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rangkuman_laporan,parent,false);
        return new KategoriTerlarisViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriTerlarisViewHolder holder, int position) {
        KategoriTerlarisItem item = kategoriTerlarisItems.get(position);
        holder.namaKategori.setText(item.getNamaKategori());
    }

    @Override
    public int getItemCount() {
        return kategoriTerlarisItems.size();
    }
}
