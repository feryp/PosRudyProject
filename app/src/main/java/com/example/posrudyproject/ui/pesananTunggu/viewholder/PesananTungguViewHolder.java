package com.example.posrudyproject.ui.pesananTunggu.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class PesananTungguViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView noPesanan;
    public AppCompatTextView tglPesanan;
    public AppCompatTextView totalHargaPesanan;
    public AppCompatTextView ketPesanan;
    public AppCompatTextView pelangganPesanan;
    public RecyclerView rvItemPesanan;

    public PesananTungguViewHolder(@NonNull View itemView) {
        super(itemView);

        noPesanan = itemView.findViewById(R.id.tv_nomor_pesanan_tunggu);
        tglPesanan = itemView.findViewById(R.id.tv_tgl_nomor_pesanan_tunggu);
        totalHargaPesanan = itemView.findViewById(R.id.tv_total_harga_pesanan_tunggu);
        ketPesanan = itemView.findViewById(R.id.tv_ket_pesanan_tunggu);
        pelangganPesanan = itemView.findViewById(R.id.tv_pelanggan_pesanan_tunggu);
        rvItemPesanan = itemView.findViewById(R.id.rv_item_pesanan_tunggu);
    }
}
