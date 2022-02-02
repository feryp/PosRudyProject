package com.example.posrudyproject.ui.laporan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class RiwayatTransaksiPenjualViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView waktuTransaksi, nominalTransaksi, namaToko, produkTerjual;

    public RiwayatTransaksiPenjualViewHolder(@NonNull View itemView) {
        super(itemView);

        waktuTransaksi = itemView.findViewById(R.id.tv_waktu_riwayat_penjual_laporan);
        nominalTransaksi = itemView.findViewById(R.id.tv_nominal_riwayat_penjual_laporan);
        namaToko = itemView.findViewById(R.id.tv_nama_toko_riwayat_penjual_laporan);
        produkTerjual = itemView.findViewById(R.id.tv_produk_terjual_riwayat_penjual_laporan);
    }
}
