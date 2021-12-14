package com.example.posrudyproject.ui.penyimpanan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class KategoriPenyimpananViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView jumlahBarang;
    public AppCompatTextView kategoriBarang;

    public KategoriPenyimpananViewHolder(@NonNull View itemView) {
        super(itemView);

        jumlahBarang = itemView.findViewById(R.id.tv_jumlah_barang_kategori_penyimpanan);
        kategoriBarang = itemView.findViewById(R.id.tv_kategori_barang_penyimpanan);
    }
}
