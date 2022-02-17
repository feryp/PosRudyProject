package com.example.posrudyproject.ui.laporan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.SubRiwayatTransaksiItem;
import com.example.posrudyproject.ui.laporan.viewholder.SubRiwayatTransaksiViewHolder;

import java.util.List;

public class SubRiwayatTransaksiAdapter extends RecyclerView.Adapter<SubRiwayatTransaksiViewHolder>{

    private final List<SubRiwayatTransaksiItem> subRiwayatTransaksiItems;
    private final OnItemClickListener listener;

    public SubRiwayatTransaksiAdapter(List<SubRiwayatTransaksiItem> subRiwayatTransaksiItems, OnItemClickListener listener) {
        this.subRiwayatTransaksiItems = subRiwayatTransaksiItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubRiwayatTransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subitem_riwayat_transaksi, parent, false);
        return new SubRiwayatTransaksiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubRiwayatTransaksiViewHolder holder, int position) {
        SubRiwayatTransaksiItem item = subRiwayatTransaksiItems.get(position);
        holder.nominalTransaksi.setText(item.getNominalTransaksi());
        holder.invoiceTransaksi.setText(item.getNoInvTransaksi());
        holder.metodePembayaranTransaksi.setText(item.getMetodePembayaranTransaksi());
        holder.jamTransaksi.setText(item.getJamTransaksi());

        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view, position));
    }

    @Override
    public int getItemCount() {
        return subRiwayatTransaksiItems.size();
    }
}
