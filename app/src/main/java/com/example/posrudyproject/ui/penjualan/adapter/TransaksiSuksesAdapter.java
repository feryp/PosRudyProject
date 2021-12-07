package com.example.posrudyproject.ui.penjualan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.penjualan.viewholder.TransaksiSuksesViewHolder;

import java.util.List;

public class TransaksiSuksesAdapter extends RecyclerView.Adapter<TransaksiSuksesViewHolder> {

    private final List<KeranjangItem> keranjangItems;
    private final Context mContext;

    public TransaksiSuksesAdapter(List<KeranjangItem> keranjangItems, Context mContext) {
        this.keranjangItems = keranjangItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TransaksiSuksesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_transaksi_selesai,parent,false);
        return new TransaksiSuksesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiSuksesViewHolder holder, int position) {
        KeranjangItem item = keranjangItems.get(position);
        holder.tipeBarang.setText(item.getTipeBarang());
        holder.artikelBarang.setText(item.getArtikelBarang());
        holder.namaBarang.setText(item.getNamaBarang());
        holder.hargaBarang.setText(item.getHargaBarang());
        holder.jumlahBarang.setText(item.getJumlahBarang());
        holder.totalHargaBarang.setText(item.getTotalHargaBarang());
    }

    @Override
    public int getItemCount() {
        return keranjangItems.size();
    }
}
