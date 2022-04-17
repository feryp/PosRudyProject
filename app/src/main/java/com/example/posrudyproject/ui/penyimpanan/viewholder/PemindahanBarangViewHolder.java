package com.example.posrudyproject.ui.penyimpanan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.posrudyproject.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.makeramen.roundedimageview.RoundedImageView;

public class PemindahanBarangViewHolder extends RecyclerView.ViewHolder {

    public RoundedImageView imBarang;
    public AppCompatTextView tipeBarang;
    public AppCompatTextView artikelBarang;
    public AppCompatTextView namaBarang;
    public AppCompatEditText jumlahBarang;
    public SwipeRevealLayout swipeRevealLayout;
    public LinearLayoutCompat layoutDelete;
    public MaterialButton btnPlus,btnMinus;

    public PemindahanBarangViewHolder(@NonNull View itemView) {
        super(itemView);

        imBarang = itemView.findViewById(R.id.im_barang_pemindahan);
        tipeBarang = itemView.findViewById(R.id.tv_tipe_barang_pemindahan);
        artikelBarang = itemView.findViewById(R.id.tv_artikel_barang_pemindahan);
        namaBarang = itemView.findViewById(R.id.tv_nama_barang_pemindahan);
        jumlahBarang = itemView.findViewById(R.id.et_jumlah_barang_pemindahan);
        swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout_pemindahan);
        layoutDelete = itemView.findViewById(R.id.layout_delete_pemindahan);
        btnPlus = itemView.findViewById(R.id.btn_plus_pemindahan);
        btnMinus = itemView.findViewById(R.id.btn_minus_pemindahan);
    }

    public void hapusItem() {
    }
}
