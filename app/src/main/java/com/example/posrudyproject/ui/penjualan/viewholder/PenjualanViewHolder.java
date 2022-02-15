package com.example.posrudyproject.ui.penjualan.viewholder;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

public class PenjualanViewHolder extends RecyclerView.ViewHolder {

    public RoundedImageView imBarang;
    public AppCompatTextView tipeBarang;
    public AppCompatTextView artikelBarang;
    public AppCompatTextView namaBarang;
    public AppCompatTextView hargaBarang;
    public AppCompatTextView kuantitasBarang;
    public MaterialButton btnPlus,btnMinus;

    public PenjualanViewHolder(@NonNull View itemView) {
        super(itemView);

        imBarang = itemView.findViewById(R.id.im_barang_penjualan);
        tipeBarang = itemView.findViewById(R.id.tv_tipe_barang_penjualan);
        artikelBarang = itemView.findViewById(R.id.tv_artikel_barang_penjualan);
        namaBarang = itemView.findViewById(R.id.tv_nama_barang_penjualan);
        hargaBarang = itemView.findViewById(R.id.tv_harga_barang_penjualan);
        kuantitasBarang = itemView.findViewById(R.id.tv_qty_item_penjualan);
        btnPlus = itemView.findViewById(R.id.btn_plus);
        btnMinus = itemView.findViewById(R.id.btn_minus);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                kuantitasBarang.setText(String.valueOf(Integer.parseInt(String.valueOf(kuantitasBarang.getText())) + 1));
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                if (Integer.parseInt(String.valueOf(kuantitasBarang.getText())) != 0) {
                    kuantitasBarang.setText(String.valueOf(Integer.parseInt(String.valueOf(kuantitasBarang.getText())) - 1));
                }

            }
        });
    }
}
