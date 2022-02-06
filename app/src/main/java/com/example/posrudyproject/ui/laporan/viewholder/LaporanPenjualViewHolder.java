package com.example.posrudyproject.ui.laporan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class LaporanPenjualViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView namaPenjual, nominalTransaksiPenjual, totTransaksiPenjual;

    public LaporanPenjualViewHolder(@NonNull View itemView) {
        super(itemView);

        namaPenjual = itemView.findViewById(R.id.tv_nama_penjual_laporan);
        nominalTransaksiPenjual = itemView.findViewById(R.id.tv_nominal_transaksi_penjual_laporan);
        totTransaksiPenjual = itemView.findViewById(R.id.tv_total_transaksi_penjual_laporan);
    }
}
