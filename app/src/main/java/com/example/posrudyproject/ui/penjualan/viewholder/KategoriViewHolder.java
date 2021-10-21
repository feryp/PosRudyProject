package com.example.posrudyproject.ui.penjualan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class KategoriViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView namaKetegori;

    public KategoriViewHolder(@NonNull View itemView) {
        super(itemView);

        namaKetegori = itemView.findViewById(R.id.tv_nama_kategori);
    }
}
