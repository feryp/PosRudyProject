package com.example.posrudyproject.ui.laporan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class ProdukTerlarisViewHolder extends RecyclerView.ViewHolder{

    public AppCompatTextView namaProduk;

    public ProdukTerlarisViewHolder(@NonNull View itemView) {
        super(itemView);

        namaProduk = itemView.findViewById(R.id.tv_rangkuman_terlaris);
    }
}
