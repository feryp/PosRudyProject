package com.example.posrudyproject.ui.penyimpanan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class BarangPindahViewHolder extends RecyclerView.ViewHolder{

    public RoundedImageView imBarang;
    public AppCompatTextView tipeBarang;
    public AppCompatTextView artikelBarang;
    public AppCompatTextView namaBarang;
    public AppCompatTextView kuantitasBarang;

    public BarangPindahViewHolder(@NonNull View itemView) {
        super(itemView);

        imBarang = itemView.findViewById(R.id.im_detail_barang_pindah);
        tipeBarang = itemView.findViewById(R.id.tv_detail_tipe_barang_pindah);
        artikelBarang = itemView.findViewById(R.id.tv_detail_artikel_barang_pindah);
        namaBarang = itemView.findViewById(R.id.tv_detail_nama_barang_pindah);
        kuantitasBarang = itemView.findViewById(R.id.tv_detail_kuantitas_barang_pindah);
    }
}
