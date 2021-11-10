package com.example.posrudyproject.ui.pelanggan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.google.android.material.button.MaterialButton;

public class PelangganViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView namaPelanggan;
    public AppCompatTextView noHpPelanggan;
    public MaterialButton btnEdit;

    public PelangganViewHolder(@NonNull View itemView) {
        super(itemView);

        namaPelanggan = itemView.findViewById(R.id.tv_nama_pelanggan);
        noHpPelanggan = itemView.findViewById(R.id.tv_no_hp_pelanggan);
        btnEdit = itemView.findViewById(R.id.btn_edit_pelanggan);
    }
}
