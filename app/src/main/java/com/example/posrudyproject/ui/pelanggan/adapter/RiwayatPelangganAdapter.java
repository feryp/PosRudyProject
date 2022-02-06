package com.example.posrudyproject.ui.pelanggan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pelanggan.model.Pelanggan;
import com.example.posrudyproject.ui.pelanggan.viewholder.RiwayatPelangganViewHolder;

import java.util.List;

public class RiwayatPelangganAdapter extends RecyclerView.Adapter<RiwayatPelangganViewHolder> {

    private final List<Pelanggan> pelangganItems;
    private final Context mContext;

    public RiwayatPelangganAdapter(List<Pelanggan> pelangganItems, Context mContext) {
        this.pelangganItems = pelangganItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RiwayatPelangganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat_pelanggan, parent, false);
        return new RiwayatPelangganViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatPelangganViewHolder holder, int position) {
        Pelanggan item = pelangganItems.get(position);
        holder.riwayatNamaPelanggan.setText(item.getNama_pelanggan());
        holder.riwayatNoHpPelanggan.setText(item.getNo_hp());
        holder.riwayatEmailPelanggan.setText(item.getEmail());
        holder.riwayatAlamatPelanggan.setText(item.getAlamat());
        holder.riwayatTotalKunjungan.setText(String.valueOf(item.getTotal_kunjungan()));
        holder.riwayatTotalPoin.setText(String.valueOf(item.getPoin()));
    }

    @Override
    public int getItemCount() {
        return pelangganItems.size();
    }
}
