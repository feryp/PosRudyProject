package com.example.posrudyproject.ui.laporan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiPelangganItem;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiPenjualItem;
import com.example.posrudyproject.ui.laporan.viewholder.RiwayatTransaksiPenjualViewHolder;

import java.util.List;

public class RiwayatTransaksiPenjualAdapter extends RecyclerView.Adapter<RiwayatTransaksiPenjualViewHolder> {

    private final List<RiwayatTransaksiPenjualItem> riwayatTransaksiPenjualItems;
    private final Context mContext;

    public RiwayatTransaksiPenjualAdapter(List<RiwayatTransaksiPenjualItem> riwayatTransaksiPenjualItems, Context mContext) {
        this.riwayatTransaksiPenjualItems = riwayatTransaksiPenjualItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RiwayatTransaksiPenjualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat_transaksi_penjual, parent, false);
        return new RiwayatTransaksiPenjualViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatTransaksiPenjualViewHolder holder, int position) {
        RiwayatTransaksiPenjualItem item = riwayatTransaksiPenjualItems.get(position);
        holder.waktuTransaksi.setText(item.getWaktuTransaksi());
        holder.nominalTransaksi.setText(item.getNominalTransaksi());
        holder.namaToko.setText(item.getNamaToko());
        holder.produkTerjual.setText(item.getProdukTerjual());
    }

    @Override
    public int getItemCount() {
        return riwayatTransaksiPenjualItems.size();
    }
}
