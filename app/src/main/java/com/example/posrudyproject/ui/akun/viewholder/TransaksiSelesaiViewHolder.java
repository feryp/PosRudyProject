package com.example.posrudyproject.ui.akun.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class TransaksiSelesaiViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView noInvoice, nominal, metodePembayaran, waktuTransaksi;

    public TransaksiSelesaiViewHolder(@NonNull View itemView) {
        super(itemView);
        noInvoice = itemView.findViewById(R.id.tv_no_invoice_transaksi_selesai);
        nominal = itemView.findViewById(R.id.tv_nominal_transaksi_selesai);
        metodePembayaran = itemView.findViewById(R.id.tv_metode_pembayaran_transaksi_selesai);
        waktuTransaksi = itemView.findViewById(R.id.tv_waktu_transaksi_selesai);
    }
}
