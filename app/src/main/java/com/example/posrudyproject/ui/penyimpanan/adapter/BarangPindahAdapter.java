package com.example.posrudyproject.ui.penyimpanan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.model.BarangPindahItem;
import com.example.posrudyproject.ui.penyimpanan.viewholder.BarangPindahViewHolder;

import java.util.List;

public class BarangPindahAdapter extends RecyclerView.Adapter<BarangPindahViewHolder>{

    private final List<BarangPindahItem> barangPindahItems;
    private final Context mContext;

    public BarangPindahAdapter(List<BarangPindahItem> barangPindahItems, Context mContext) {
        this.barangPindahItems = barangPindahItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BarangPindahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_pindah,parent,false);
        return new BarangPindahViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangPindahViewHolder holder, int position) {
        BarangPindahItem item = barangPindahItems.get(position);
        holder.imBarang.setImageResource(item.getImBarang());
        holder.tipeBarang.setText(item.getTipeBarangPindah());
        holder.artikelBarang.setText(item.getArtikelBarangPindah());
        holder.namaBarang.setText(item.getNamaBarangPindah());
        holder.kuantitasBarang.setText(item.getKuantitasBarangPindah());
    }

    @Override
    public int getItemCount() {
        return barangPindahItems.size();
    }
}
