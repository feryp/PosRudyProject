package com.example.posrudyproject.ui.produk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.produk.model.ProdukItem;
import com.example.posrudyproject.ui.produk.viewholder.PilihProdukViewHolder;

import java.util.List;

public class PilihProdukAdapter extends RecyclerView.Adapter<PilihProdukViewHolder>{

    private final List<ProdukItem> produkItems;
    private final OnItemClickListener listener;

    public PilihProdukAdapter(List<ProdukItem> produkItems, OnItemClickListener listener) {
        this.produkItems = produkItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PilihProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk_custom,parent,false);
        return new PilihProdukViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PilihProdukViewHolder holder, int position) {
        ProdukItem item = produkItems.get(position);
        holder.imProduk.setImageResource(item.getImProduk());
        holder.tipeProduk.setText(item.getTipeProduk());
        holder.artikelProduk.setText(item.getArtikelProduk());
        holder.namaProduk.setText(item.getNamaProduk());
        holder.stokProduk.setText(item.getStokProduk());
        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view, position));
    }

    @Override
    public int getItemCount() {
        return produkItems.size();
    }
}
