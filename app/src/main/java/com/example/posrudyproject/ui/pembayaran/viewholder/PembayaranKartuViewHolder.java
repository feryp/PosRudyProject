package com.example.posrudyproject.ui.pembayaran.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class PembayaranKartuViewHolder extends RecyclerView.ViewHolder {

    public AppCompatImageView logoBank;

    public PembayaranKartuViewHolder(@NonNull View itemView) {
        super(itemView);

        logoBank = itemView.findViewById(R.id.im_logo_bank_card);
    }
}
