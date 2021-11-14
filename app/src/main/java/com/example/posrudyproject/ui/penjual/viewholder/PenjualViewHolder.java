package com.example.posrudyproject.ui.penjual.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.google.android.material.card.MaterialCardView;
import com.makeramen.roundedimageview.RoundedImageView;

public class PenjualViewHolder extends RecyclerView.ViewHolder {

    public RoundedImageView fotoPenjual;
    public AppCompatTextView namaPenjual;
    public AppCompatTextView jabatanPenjual;
    public ConstraintLayout container;

    public PenjualViewHolder(@NonNull View itemView) {
        super(itemView);

        fotoPenjual = itemView.findViewById(R.id.im_foto_penjual);
        namaPenjual = itemView.findViewById(R.id.tv_nama_penjual);
        jabatanPenjual = itemView.findViewById(R.id.tv_jabatan_penjual);
        container = itemView.findViewById(R.id.container_card_penjual);
    }
}
