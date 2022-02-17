package com.example.posrudyproject.ui.laporan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class SubRiwayatTransaksiViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView nominalTransaksi, invoiceTransaksi, metodePembayaranTransaksi, jamTransaksi;

    public SubRiwayatTransaksiViewHolder(@NonNull View itemView) {
        super(itemView);

        nominalTransaksi = itemView.findViewById(R.id.tv_nominal_riwayat_transaksi_laporan);
        invoiceTransaksi = itemView.findViewById(R.id.tv_no_invoice_riwayat_transaksi_laporan);
        metodePembayaranTransaksi = itemView.findViewById(R.id.tv_metode_pembayaran_riwayat_transaksi_laporan);
        jamTransaksi = itemView.findViewById(R.id.tv_jam_riwayat_transaksi_laporan);
    }
}
