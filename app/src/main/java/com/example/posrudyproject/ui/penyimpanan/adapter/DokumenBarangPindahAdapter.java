package com.example.posrudyproject.ui.penyimpanan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.model.DokumenBarangPindahItem;
import com.example.posrudyproject.ui.penyimpanan.viewholder.DokumenBarangPindahViewHolder;

import java.util.List;

public class DokumenBarangPindahAdapter extends RecyclerView.Adapter<DokumenBarangPindahViewHolder> {

    private final List<DokumenBarangPindahItem> dokumenBarangPindahItems;
    private final OnItemClickListener listener;

    public DokumenBarangPindahAdapter(List<DokumenBarangPindahItem> dokumenBarangPindahItems, OnItemClickListener listener) {
        this.dokumenBarangPindahItems = dokumenBarangPindahItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DokumenBarangPindahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dokumen_barang_pindah,parent,false);
        return new DokumenBarangPindahViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DokumenBarangPindahViewHolder holder, int position) {
        DokumenBarangPindahItem item = dokumenBarangPindahItems.get(position);
        holder.noDokumen.setText(item.getNoDocBarang());
        holder.waktu.setText(item.getWaktuBarangPindah());
        holder.jumlahBarang.setText(item.getJumlahBarangPindah());
        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view,position));
    }

    @Override
    public int getItemCount() {
        return dokumenBarangPindahItems.size();
    }
}
