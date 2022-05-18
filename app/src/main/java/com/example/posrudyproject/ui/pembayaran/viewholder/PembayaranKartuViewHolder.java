package com.example.posrudyproject.ui.pembayaran.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class PembayaranKartuViewHolder extends RecyclerView.ViewHolder {

    public AppCompatImageView logoBank;
    public AppCompatTextView namaBank;
    public AppCompatTextView noRek;

    public PembayaranKartuViewHolder(@NonNull View itemView) {
        super(itemView);

        logoBank = itemView.findViewById(R.id.im_logo_bank_card);
        namaBank = itemView.findViewById(R.id.tv_nama_bank);
        noRek = itemView.findViewById(R.id.tv_no_rek_bank);
    }
}
