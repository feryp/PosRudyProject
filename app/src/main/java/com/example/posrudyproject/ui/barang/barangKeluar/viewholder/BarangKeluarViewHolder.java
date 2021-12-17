package com.example.posrudyproject.ui.barang.barangKeluar.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class BarangKeluarViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView tipeBarang;
    public AppCompatTextView artikelBarang;
    public AppCompatTextView namaBarang;
    public AppCompatTextView waktu;
    public AppCompatTextView stokBarang;

    public BarangKeluarViewHolder(@NonNull View itemView) {
        super(itemView);

        tipeBarang = itemView.findViewById(R.id.tv_tipe_barang_keluar);
        artikelBarang = itemView.findViewById(R.id.tv_artikel_barang_keluar);
        namaBarang = itemView.findViewById(R.id.tv_nama_barang_keluar);
        waktu = itemView.findViewById(R.id.tv_waktu_barang_keluar);
        stokBarang = itemView.findViewById(R.id.tv_stok_barang_keluar);
    }
}
