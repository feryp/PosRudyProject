package com.example.posrudyproject.ui.pesananTunggu.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class BarangPesananViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView kuantitasBarang;
    public AppCompatTextView namaBarang;

    public BarangPesananViewHolder(@NonNull View itemView) {
        super(itemView);

        kuantitasBarang = itemView.findViewById(R.id.tv_qty_item_pesanan_tunggu);
        namaBarang = itemView.findViewById(R.id.tv_nama_barang_pesanan_tunggu);
    }
}
