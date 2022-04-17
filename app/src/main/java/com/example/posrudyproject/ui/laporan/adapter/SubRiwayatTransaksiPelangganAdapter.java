package com.example.posrudyproject.ui.laporan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.SubRiwayatTransaksiPelangganItem;
import com.example.posrudyproject.ui.laporan.viewholder.SubRiwayatTransaksiPelangganViewHolder;

import java.util.List;

public class SubRiwayatTransaksiPelangganAdapter extends RecyclerView.Adapter<SubRiwayatTransaksiPelangganViewHolder> {

    private final List<SubRiwayatTransaksiPelangganItem> subRiwayatTransaksiPelangganItems;
    private final OnItemClickListener listener;

    public SubRiwayatTransaksiPelangganAdapter(List<SubRiwayatTransaksiPelangganItem> subRiwayatTransaksiPelangganItems, OnItemClickListener listener) {
        this.subRiwayatTransaksiPelangganItems = subRiwayatTransaksiPelangganItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubRiwayatTransaksiPelangganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subitem_riwayat_transaksi, parent, false);
        return new SubRiwayatTransaksiPelangganViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubRiwayatTransaksiPelangganViewHolder holder, int position) {
        SubRiwayatTransaksiPelangganItem item = subRiwayatTransaksiPelangganItems.get(position);
        holder.nominalTransaksi.setText(item.getNominalTransaksi());
        holder.invoiceTransaksi.setText(item.getNoInvTransaksi());
        holder.jamTransaksi.setText(item.getJamTransaksi());

        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view, position));
    }

    @Override
    public int getItemCount() {
        return subRiwayatTransaksiPelangganItems.size();
    }
}
