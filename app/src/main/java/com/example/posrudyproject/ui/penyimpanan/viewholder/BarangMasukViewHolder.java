package com.example.posrudyproject.ui.penyimpanan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class BarangMasukViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView tipeBarang;
    public AppCompatTextView artikelBarang;
    public AppCompatTextView namaBarang;
    public AppCompatTextView waktu;
    public AppCompatTextView stokBarang;

    public BarangMasukViewHolder(@NonNull View itemView) {
        super(itemView);

        tipeBarang = itemView.findViewById(R.id.tv_tipe_barang_masuk);
        artikelBarang = itemView.findViewById(R.id.tv_artikel_barang_masuk);
        namaBarang = itemView.findViewById(R.id.tv_nama_barang_masuk);
        waktu = itemView.findViewById(R.id.tv_waktu_barang_masuk);
        stokBarang = itemView.findViewById(R.id.tv_stok_barang_masuk);
    }
}
