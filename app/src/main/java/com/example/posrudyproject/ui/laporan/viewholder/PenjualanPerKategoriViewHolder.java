package com.example.posrudyproject.ui.laporan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerKategoriItem;

public class PenjualanPerKategoriViewHolder extends RecyclerView.ViewHolder{

    public AppCompatTextView namaKategori, penjualanKotorKategori, jmlTerjualKategori, totalPenjualanKategori, pajakKategori;
    public AppCompatImageView imArrow;
    public LinearLayoutCompat subItem;

    public PenjualanPerKategoriViewHolder(@NonNull View itemView) {
        super(itemView);

        namaKategori = itemView.findViewById(R.id.tv_nama_kategori_penjualan_laporan);
        penjualanKotorKategori = itemView.findViewById(R.id.tv_penjualan_kotor_kategori_laporan);
        jmlTerjualKategori = itemView.findViewById(R.id.tv_terjual_penjualan_kategori_laporan);
        totalPenjualanKategori = itemView.findViewById(R.id.tv_total_terjual_kategori_laporan);

        imArrow = itemView.findViewById(R.id.btn_expand_arrow);
        subItem = itemView.findViewById(R.id.sub_item);
    }

    public void bind(PenjualanPerKategoriItem item) {
        // Get the state
        boolean expanded = item.isExpanded();

        // Set the visibility based on state
        subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
        imArrow.setRotation(expanded ? 180 : 0);

        namaKategori.setText(item.getNamaKategori());
        penjualanKotorKategori.setText(item.getPenjualanKotorKategori());
        jmlTerjualKategori.setText(item.getJmlTerjualKategori());
        totalPenjualanKategori.setText(item.getTotPenjualanKategori());

    }
}
