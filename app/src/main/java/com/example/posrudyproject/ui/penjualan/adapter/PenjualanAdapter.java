package com.example.posrudyproject.ui.penjualan.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
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
        PenjualanItem item = penjualanItems.get(position);
        byte[] bytes = Base64.decode(item.getFoto_barang().getBytes(), Base64.DEFAULT);
        Bitmap btm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.imBarang.setImageBitmap(btm);
        holder.tipeBarang.setText(item.getTipeBarang());
        holder.skuCode.setText(item.getSkuCode());
        holder.artikelBarang.setText(item.getArtikelBarang());
        holder.namaBarang.setText(item.getNamaBarang());
        holder.hargaBarang.setText(item.getHargaBarang());
        holder.kuantitasBarang.setText(item.getKuantitasBarang());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            //FUNCTION PENJUALAN AND INTENT TO NEXT STEP SALE
            @Override
            public void onClick(View v){

            }
        });
    }

    @Override
    public int getItemCount() {
        return penjualanItems.size();
    }
}
