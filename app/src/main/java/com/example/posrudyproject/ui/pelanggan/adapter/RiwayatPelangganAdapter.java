package com.example.posrudyproject.ui.pelanggan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pelanggan.model.PelangganItem;
import com.example.posrudyproject.ui.pelanggan.viewholder.RiwayatPelangganViewHolder;

import java.util.List;

public class RiwayatPelangganAdapter extends RecyclerView.Adapter<RiwayatPelangganViewHolder> {

    private final List<PelangganItem> pelangganItems;
    private final Context mContext;

    public RiwayatPelangganAdapter(List<PelangganItem> pelangganItems, Context mContext) {
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
        PelangganItem item = pelangganItems.get(position);
        holder.riwayatNamaPelanggan.setText(item.getNamaPelanggan());
        holder.riwayatNoHpPelanggan.setText(item.getNohpPelanggan());
        holder.riwayatEmailPelanggan.setText(item.getEmailPelanggan());
        holder.riwayatAlamatPelanggan.setText(item.getAlamatPelanggan());
        holder.riwayatTotalKunjungan.setText(String.valueOf(item.getTotalKunjungan()));
        holder.riwayatKunjunganTerakhir.setText(item.getKunjunganTerakhir());
        holder.riwayatTotalPoin.setText(item.getTotalPoin());
    }

    @Override
    public int getItemCount() {
        return pelangganItems.size();
    }
}
