package com.example.posrudyproject.ui.notifikasi.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class NotifikasiViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView judulNotif;
    public AppCompatTextView pesanNotif;
    public AppCompatTextView waktuNotif;

    public NotifikasiViewHolder(@NonNull View itemView) {
        super(itemView);

        judulNotif = itemView.findViewById(R.id.tv_judul_notifikasi);
        pesanNotif = itemView.findViewById(R.id.tv_pesan_notifikasi);
        waktuNotif = itemView.findViewById(R.id.tv_waktu_notifikasi);
    }
}
