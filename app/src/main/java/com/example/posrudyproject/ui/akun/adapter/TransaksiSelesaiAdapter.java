package com.example.posrudyproject.ui.akun.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.akun.model.TransaksiSelesaiItem;
import com.example.posrudyproject.ui.akun.viewholder.TransaksiSelesaiViewHolder;

import java.util.List;

public class TransaksiSelesaiAdapter extends RecyclerView.Adapter<TransaksiSelesaiViewHolder> {

    private final List<TransaksiSelesaiItem> transaksiSelesaiItems;
    private final OnItemClickListener listener;

    public TransaksiSelesaiAdapter(List<TransaksiSelesaiItem> transaksiSelesaiItems, OnItemClickListener listener) {
        this.transaksiSelesaiItems = transaksiSelesaiItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransaksiSelesaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selesai_transaksi, parent, false);
        return new TransaksiSelesaiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiSelesaiViewHolder holder, int position) {
        TransaksiSelesaiItem item = transaksiSelesaiItems.get(position);
        holder.noInvoice.setText(item.getNoInvoice());
        holder.nominal.setText(item.getNominal());
        holder.metodePembayaran.setText(item.getMetodePembayaran());
        holder.waktuTransaksi.setText(item.getWaktuTransaksil());
        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view,position));
    }

    @Override
    public int getItemCount() {
        return transaksiSelesaiItems.size();
    }
}
