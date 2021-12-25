package com.example.posrudyproject.ui.pelanggan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class RiwayatPelangganViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView riwayatNamaPelanggan;
    public AppCompatTextView riwayatNoHpPelanggan;
    public AppCompatTextView riwayatEmailPelanggan;
    public AppCompatTextView riwayatAlamatPelanggan;
    public AppCompatTextView riwayatTotalKunjungan;
    public AppCompatTextView riwayatKunjunganTerakhir;
    public AppCompatTextView riwayatTotalPoin;

    public RiwayatPelangganViewHolder(@NonNull View itemView) {
        super(itemView);

        riwayatNamaPelanggan = itemView.findViewById(R.id.tv_riwayat_nama_pelanggan);
        riwayatNoHpPelanggan = itemView.findViewById(R.id.tv_riwayat_no_hp_pelanggan);
        riwayatEmailPelanggan = itemView.findViewById(R.id.tv_riwayat_email_pelanggan);
        riwayatAlamatPelanggan = itemView.findViewById(R.id.tv_riwayat_alamat_pelanggan);
        riwayatTotalKunjungan = itemView.findViewById(R.id.tv_riwayat_total_kunjungan);
        riwayatKunjunganTerakhir = itemView.findViewById(R.id.tv_riwayat_kunjungan_terakhir);
        riwayatTotalPoin = itemView.findViewById(R.id.tv_riwayat_total_poin);
    }
}
