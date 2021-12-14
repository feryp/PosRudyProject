package com.example.posrudyproject.ui.penyimpanan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.google.android.material.card.MaterialCardView;

public class KategoriPenyimpananViewHolder extends RecyclerView.ViewHolder {

    public MaterialCardView container;
    public AppCompatTextView jumlahBarang;
    public AppCompatTextView kategoriBarang;

    public KategoriPenyimpananViewHolder(@NonNull View itemView) {
        super(itemView);

        container = itemView.findViewById(R.id.card_kategori_penyimpanan);
        jumlahBarang = itemView.findViewById(R.id.tv_jumlah_barang_kategori_penyimpanan);
        kategoriBarang = itemView.findViewById(R.id.tv_kategori_barang_penyimpanan);
    }
}
