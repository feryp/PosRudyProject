package com.example.posrudyproject.ui.produk.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class PilihProdukViewHolder extends RecyclerView.ViewHolder{

    public RoundedImageView imProduk;
    public AppCompatTextView tipeProduk;
    public AppCompatTextView artikelProduk;
    public AppCompatTextView namaProduk;
    public AppCompatTextView stokProduk;

    public PilihProdukViewHolder(@NonNull View itemView) {
        super(itemView);

        imProduk = itemView.findViewById(R.id.im_custom_produk);
        tipeProduk = itemView.findViewById(R.id.tv_tipe_custom_produk);
        artikelProduk = itemView.findViewById(R.id.tv_artikel_custom_produk);
        namaProduk = itemView.findViewById(R.id.tv_nama_custom_produk);
        stokProduk = itemView.findViewById(R.id.tv_stok_custom_produk);
    }
}
