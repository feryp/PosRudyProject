package com.example.posrudyproject.ui.laporan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerArtikelItem;

public class PenjualanPerArtikelViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView namaArtikel, penjualanKotorArtikel, jmlTerjualArtikel, totalPenjualanArtikel, pajakArtikel;
    public AppCompatImageView imArrow;
    public LinearLayoutCompat subItem;

    public PenjualanPerArtikelViewHolder(@NonNull View itemView) {
        super(itemView);

        namaArtikel = itemView.findViewById(R.id.tv_artikel_penjualan_laporan);
        penjualanKotorArtikel = itemView.findViewById(R.id.tv_penjualan_kotor_artikel_laporan);
        jmlTerjualArtikel = itemView.findViewById(R.id.tv_terjual_penjualan_artikel_laporan);
        totalPenjualanArtikel = itemView.findViewById(R.id.tv_total_terjual_artikel_laporan);
        pajakArtikel = itemView.findViewById(R.id.tv_pajak_penjualan_artikel_laporan);
        imArrow = itemView.findViewById(R.id.btn_expand_arrow);
        subItem = itemView.findViewById(R.id.sub_item);

    }

    public void bind(PenjualanPerArtikelItem item) {

        // Get the state
        boolean expanded = item.isExpanded();

        // Set the visibility based on state
        subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
        imArrow.setRotation(expanded ? 180 : 0);

        namaArtikel.setText(item.getNamaArtikel());
        penjualanKotorArtikel.setText(item.getPenjualanKotorArtikel());
        jmlTerjualArtikel.setText(item.getJmlTerjualArtikel());
        totalPenjualanArtikel.setText(item.getTotPenjualanArtikel());
        pajakArtikel.setText(item.getPajakArtikel());
    }
}
