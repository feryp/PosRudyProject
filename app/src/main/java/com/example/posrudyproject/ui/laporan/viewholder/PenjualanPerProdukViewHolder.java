package com.example.posrudyproject.ui.laporan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerProdukItem;

public class PenjualanPerProdukViewHolder extends RecyclerView.ViewHolder{

    public AppCompatTextView namaProduk, penjualanKotorProduk, qtyProduk, artikelProduk, kategoriProduk, pajakProduk, totalPenjualanProduk;
    public AppCompatImageView imArrow;
    public LinearLayoutCompat subItem;

    public PenjualanPerProdukViewHolder(@NonNull View itemView) {
        super(itemView);

        namaProduk = itemView.findViewById(R.id.tv_nama_produk_penjualan_laporan);
        penjualanKotorProduk = itemView.findViewById(R.id.tv_penjualan_kotor_produk_laporan);
        qtyProduk = itemView.findViewById(R.id.tv_qty_penjualan_laporan);
        artikelProduk = itemView.findViewById(R.id.tv_artikel_penjualan_produk_laporan);
        kategoriProduk = itemView.findViewById(R.id.tv_kategori_penjualan_produk_laporan);
        totalPenjualanProduk = itemView.findViewById(R.id.tv_total_harga_penjualan_produk_laporan);
        imArrow = itemView.findViewById(R.id.btn_expand_arrow);
        subItem = itemView.findViewById(R.id.sub_item);
    }

    public void bind(PenjualanPerProdukItem item) {
        // Get the state
        boolean expanded = item.isExpanded();

        // Set the visibility based on state
        subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
        imArrow.setRotation(expanded ? 180 : 0);

        namaProduk.setText(item.getNamaProduk());
        penjualanKotorProduk.setText(item.getPenjualanKotorProduk());
        qtyProduk.setText(item.getQtyProduk());
        artikelProduk.setText(item.getArtikelProduk());
        kategoriProduk.setText(item.getKategoriProduk());
        totalPenjualanProduk.setText(item.getTotalPenjualanProduk());
    }
}
