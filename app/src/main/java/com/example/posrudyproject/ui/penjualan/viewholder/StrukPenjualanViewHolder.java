package com.example.posrudyproject.ui.penjualan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class StrukPenjualanViewHolder extends RecyclerView.ViewHolder{

    public AppCompatTextView tipeBarang;
    public AppCompatTextView artikelBarang;
    public AppCompatTextView namaBarang;
    public AppCompatTextView hargaBarang;
    public AppCompatTextView jumlahBarang;
    public AppCompatTextView totalHargaBarang;

    public StrukPenjualanViewHolder(@NonNull View itemView) {
        super(itemView);

        tipeBarang = itemView.findViewById(R.id.tv_tipe_barang_struk);
        artikelBarang = itemView.findViewById(R.id.tv_artikel_barang_struk);
        namaBarang = itemView.findViewById(R.id.tv_nama_barang_struk);
        hargaBarang = itemView.findViewById(R.id.tv_harga_barang_struk);
        jumlahBarang = itemView.findViewById(R.id.tv_jumlah_barang_struk);
        totalHargaBarang = itemView.findViewById(R.id.tv_total_harga_barang_struk);
    }
}
