package com.example.posrudyproject.ui.laporan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class TransaksiTerakhirViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView nominalTransaksi, invoiceTransaksi, statusTransaksi, waktuTransaksi;

    public TransaksiTerakhirViewHolder(@NonNull View itemView) {
        super(itemView);

        nominalTransaksi = itemView.findViewById(R.id.tv_nominal_transaksi_terakhir);
        invoiceTransaksi = itemView.findViewById(R.id.tv_no_invoice_transaksi_terakhir);
        statusTransaksi = itemView.findViewById(R.id.tv_status_transaksi_terakhir);
        waktuTransaksi = itemView.findViewById(R.id.tv_waktu_transaksi_terakhir);

    }
}
