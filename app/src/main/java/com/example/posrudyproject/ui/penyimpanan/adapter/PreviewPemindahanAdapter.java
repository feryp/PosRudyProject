package com.example.posrudyproject.ui.penyimpanan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.model.PemindahanBarangItem;
import com.example.posrudyproject.ui.penyimpanan.viewholder.PreviewPemindahanViewHolder;

import java.util.List;

public class PreviewPemindahanAdapter extends RecyclerView.Adapter<PreviewPemindahanViewHolder>{

    private final List<PemindahanBarangItem> pemindahanBarangItems;
    private final Context mContext;

    public PreviewPemindahanAdapter(List<PemindahanBarangItem> pemindahanBarangItems, Context mContext) {
        this.pemindahanBarangItems = pemindahanBarangItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PreviewPemindahanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_preview_pemindahan,parent,false);
        return new PreviewPemindahanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewPemindahanViewHolder holder, int position) {
        PemindahanBarangItem item = pemindahanBarangItems.get(position);
        holder.imBarang.setImageResource(item.getImProduk());
        holder.tipeBarang.setText(item.getTipeProduk());
        holder.artikelBarang.setText(item.getArtikelProduk());
        holder.namaBarang.setText(item.getNamaProduk());
        holder.jumlahBarang.setText(item.getJumlahProduk());
    }

    @Override
    public int getItemCount() {
        return pemindahanBarangItems.size();
    }
}
