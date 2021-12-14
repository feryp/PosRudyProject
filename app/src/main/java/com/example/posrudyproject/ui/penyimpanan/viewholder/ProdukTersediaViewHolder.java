package com.example.posrudyproject.ui.penyimpanan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class ProdukTersediaViewHolder extends RecyclerView.ViewHolder {

    public RoundedImageView imBarang;
    public AppCompatTextView tipeBarang;
    public AppCompatTextView artikelBarang;
    public AppCompatTextView namaBarang;
    public AppCompatTextView hargaBarang;
    public AppCompatTextView jumlahStok;

    public ProdukTersediaViewHolder(@NonNull View itemView) {
        super(itemView);

        imBarang = itemView.findViewById(R.id.im_barang_persediaan);
        tipeBarang = itemView.findViewById(R.id.tv_tipe_barang_persediaan);
        artikelBarang = itemView.findViewById(R.id.tv_artikel_barang_persediaan);
        namaBarang = itemView.findViewById(R.id.tv_nama_barang_persediaan);
        hargaBarang = itemView.findViewById(R.id.tv_harga_barang_persediaan);
        jumlahStok = itemView.findViewById(R.id.tv_jumlah_stok_persediaan);
    }
}
