package com.example.posrudyproject.ui.penyimpanan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class PreviewPemindahanViewHolder extends RecyclerView.ViewHolder{

    public RoundedImageView imBarang;
    public AppCompatTextView tipeBarang;
    public AppCompatTextView artikelBarang;
    public AppCompatTextView namaBarang;
    public AppCompatTextView jumlahBarang;

    public PreviewPemindahanViewHolder(@NonNull View itemView) {
        super(itemView);

        imBarang = itemView.findViewById(R.id.im_barang_preview_pemindahan);
        tipeBarang = itemView.findViewById(R.id.tv_tipe_preview_pemindahan);
        artikelBarang = itemView.findViewById(R.id.tv_artikel_preview_pemindahan);
        namaBarang = itemView.findViewById(R.id.tv_nama_barang_preview_pemindahan);
        jumlahBarang = itemView.findViewById(R.id.tv_jumlah_barang_preview_pemindahan);
    }
}
