package com.example.posrudyproject.ui.laporan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.LaporanPelangganItem;
import com.google.android.material.button.MaterialButton;

public class LaporanPelangganViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView namaPelanggan, noHpPelanggan, totKunjunganPelanggan, transaksiPelanggan;
    public AppCompatImageView imArrow;
    public MaterialButton btnDetail;
    public LinearLayoutCompat subItem;

    public LaporanPelangganViewHolder(@NonNull View itemView) {
        super(itemView);

        namaPelanggan = itemView.findViewById(R.id.tv_nama_pelanggan_laporan);
        noHpPelanggan = itemView.findViewById(R.id.tv_no_hp_pelanggan_laporan);
        totKunjunganPelanggan = itemView.findViewById(R.id.tv_total_kunjungan_pelanggan_laporan);
        transaksiPelanggan = itemView.findViewById(R.id.tv_transaksi_pelanggan_laporan);
        btnDetail = itemView.findViewById(R.id.btn_detail_laporan_pelanggan);
        imArrow = itemView.findViewById(R.id.btn_expand_arrow);
        subItem = itemView.findViewById(R.id.sub_item);

    }

    public void bind(LaporanPelangganItem item) {
        // Get the state
        boolean expanded = item.isExpanded();

        // Set the visibility based on state
        subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
        imArrow.setRotation(expanded ? 180 : 0);

        namaPelanggan.setText(item.getNamaPelanggan());
        noHpPelanggan.setText(item.getNoHpPelanggan());
        totKunjunganPelanggan.setText(item.getTotKunjunganPelanggan());
        transaksiPelanggan.setText(item.getTransaksiPelanggan());
    }
}
