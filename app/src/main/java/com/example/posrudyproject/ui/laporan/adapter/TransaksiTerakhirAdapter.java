package com.example.posrudyproject.ui.laporan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.TransaksiTerakhirItem;
import com.example.posrudyproject.ui.laporan.viewholder.TransaksiTerakhirViewHolder;

import java.util.List;

public class TransaksiTerakhirAdapter extends RecyclerView.Adapter<TransaksiTerakhirViewHolder> {

    private final List<TransaksiTerakhirItem> transaksiTerakhirItems;
    private final Context mContext;

    public TransaksiTerakhirAdapter(List<TransaksiTerakhirItem> transaksiTerakhirItems, Context mContext) {
        this.transaksiTerakhirItems = transaksiTerakhirItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TransaksiTerakhirViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi_terakhir,parent,false);
        return new TransaksiTerakhirViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiTerakhirViewHolder holder, int position) {
        TransaksiTerakhirItem item = transaksiTerakhirItems.get(position);
        holder.nominalTransaksi.setText(item.getNominalTransaksi());
        holder.invoiceTransaksi.setText(item.getInvoiceTransaksi());
        holder.statusTransaksi.setText(item.getStatusTransaksi());
        holder.waktuTransaksi.setText(item.getWaktuTransaksi());
    }

    @Override
    public int getItemCount() {
        return transaksiTerakhirItems.size();
    }
}
