package com.example.posrudyproject.ui.laporan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiPelangganItem;

public class RiwayatTransaksiPelangganViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView tglTransaksi, totalTransaksi;
    public AppCompatImageView imArrow;
    public RecyclerView rvSubItemRiwayatTransaksi;

    public RiwayatTransaksiPelangganViewHolder(@NonNull View itemView) {
        super(itemView);

        tglTransaksi = itemView.findViewById(R.id.tv_tgl_riwayat_pelanggan_laporan);
        totalTransaksi = itemView.findViewById(R.id.tv_total_transaksi_pelanggan_laporan);
        imArrow = itemView.findViewById(R.id.btn_expand_arrow);
        rvSubItemRiwayatTransaksi = itemView.findViewById(R.id.rv_sub_item_transaksi_laporan_pelanggan);
    }

    public void bind(RiwayatTransaksiPelangganItem item) {
        // Get the state
        boolean expanded = item.isExpanded();

        // Set the visibility based on state
        rvSubItemRiwayatTransaksi.setVisibility(expanded ? View.VISIBLE : View.GONE);
        imArrow.setRotation(expanded ? 180 : 0);

        tglTransaksi.setText(item.getTglTransaksi());
        totalTransaksi.setText(item.getTotalTransaksi());
    }
}
