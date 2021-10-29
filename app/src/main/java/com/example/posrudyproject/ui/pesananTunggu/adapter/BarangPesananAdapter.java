package com.example.posrudyproject.ui.pesananTunggu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pesananTunggu.model.BarangPesananTungguItem;
import com.example.posrudyproject.ui.pesananTunggu.viewholder.BarangPesananViewHolder;

import java.util.List;

public class BarangPesananAdapter extends RecyclerView.Adapter<BarangPesananViewHolder> {

    private final List<BarangPesananTungguItem> barangPesananTungguItems;

    public BarangPesananAdapter(List<BarangPesananTungguItem> barangPesananTungguItems) {
        this.barangPesananTungguItems = barangPesananTungguItems;
    }


    @NonNull
    @Override
    public BarangPesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_pesanan_tunggu, parent, false);
        return new  BarangPesananViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangPesananViewHolder holder, int position) {
        BarangPesananTungguItem item = barangPesananTungguItems.get(position);
        holder.kuantitasBarang.setText(String.valueOf(item.getKuantitasBarang()));
        holder.namaBarang.setText(item.getNamaBarang());
    }

    @Override
    public int getItemCount() {
        return barangPesananTungguItems.size();
    }
}
