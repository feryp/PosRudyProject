package com.example.posrudyproject.ui.ubahHarga.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

public class UbahHargaViewHolder extends RecyclerView.ViewHolder {

    public RoundedImageView imBarang;
    public AppCompatTextView tipeBarang;
    public AppCompatTextView artikelBarang;
    public AppCompatTextView namaBarang;
    public AppCompatTextView hargaBarang;
    public MaterialButton btnEdit;

    public UbahHargaViewHolder(@NonNull View itemView) {
        super(itemView);

        imBarang = itemView.findViewById(R.id.im_barang_potongan_harga);
        tipeBarang = itemView.findViewById(R.id.tv_tipe_barang_potongan_harga);
        artikelBarang = itemView.findViewById(R.id.tv_artikel_barang_potongan_harga);
        namaBarang = itemView.findViewById(R.id.tv_nama_barang_potongan_harga);
        hargaBarang = itemView.findViewById(R.id.tv_harga_barang_potongan_harga);
        btnEdit = itemView.findViewById(R.id.btn_edit_harga_barang);
    }
}
