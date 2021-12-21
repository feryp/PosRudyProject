package com.example.posrudyproject.ui.penyimpanan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class DokumenBarangPindahViewHolder extends RecyclerView.ViewHolder{

    public AppCompatTextView noDokumen;
    public AppCompatTextView waktu;
    public AppCompatTextView jumlahBarang;

    public DokumenBarangPindahViewHolder(@NonNull View itemView) {
        super(itemView);

        noDokumen = itemView.findViewById(R.id.tv_no_dokumen_barang_pindah);
        waktu = itemView.findViewById(R.id.tv_waktu_barang_pindah);
        jumlahBarang = itemView.findViewById(R.id.tv_jumlah_barang_pindah);
    }
}
