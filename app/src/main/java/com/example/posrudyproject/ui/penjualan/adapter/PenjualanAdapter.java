package com.example.posrudyproject.ui.penjualan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penjualan.model.PenjualanItem;
import com.example.posrudyproject.ui.penjualan.viewholder.PenjualanViewHolder;

import java.util.List;

public class PenjualanAdapter extends RecyclerView.Adapter<PenjualanViewHolder> {

    private final List<PenjualanItem> penjualanItems;
    private final Context mContext;

    public PenjualanAdapter(List<PenjualanItem> penjualanItems, Context mContext) {
        this.penjualanItems = penjualanItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new PenjualanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualanViewHolder holder, int position) {
        holder.imBarang.setImageResource(penjualanItems.get(position).getImBarang());
        holder.tipeBarang.setText(penjualanItems.get(position).getTipeBarang());
        holder.artikelBarang.setText(penjualanItems.get(position).getArtikelBarang());
        holder.namaBarang.setText(penjualanItems.get(position).getNamaBarang());
        holder.hargaBarang.setText(penjualanItems.get(position).getHargaBarang());
        holder.kuantitasBarang.setText(penjualanItems.get(position).getKuantitasBarang());

        holder.itemView.setOnClickListener(view -> {
            //FUNCTION PENJUALAN AND INTENT TO NEXT STEP SALE
        });
    }

    @Override
    public int getItemCount() {
        return penjualanItems.size();
    }
}
