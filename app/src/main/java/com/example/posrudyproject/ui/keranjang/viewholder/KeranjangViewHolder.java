package com.example.posrudyproject.ui.keranjang.viewholder;

import android.app.ProgressDialog;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.posrudyproject.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class KeranjangViewHolder extends RecyclerView.ViewHolder {

    public RoundedImageView imBarang;
    public AppCompatTextView tipeBarang;
    public AppCompatTextView artikelBarang;
    public AppCompatTextView namaBarang;
    public AppCompatTextView hargaBarang;
    public AppCompatTextView jumlahBarang;
    public AppCompatTextView totalHargaBarang;
    public AppCompatEditText kuantitasBarang;
    public SwipeRevealLayout swipeRevealLayout;
    public LinearLayoutCompat layoutDelete;

    private ProgressDialog progressDialog;

    public KeranjangViewHolder(@NonNull View itemView) {
        super(itemView);

        imBarang = itemView.findViewById(R.id.im_barang_keranjang);
        tipeBarang = itemView.findViewById(R.id.tv_tipe_barang_keranjang);
        artikelBarang = itemView.findViewById(R.id.tv_artikel_barang_keranjang);
        namaBarang = itemView.findViewById(R.id.tv_nama_barang_keranjang);
        hargaBarang = itemView.findViewById(R.id.tv_harga_barang_keranjang);
        jumlahBarang = itemView.findViewById(R.id.tv_jumlah_barang_keranjang);
        totalHargaBarang = itemView.findViewById(R.id.tv_total_harga_barang_keranjang);
        kuantitasBarang = itemView.findViewById(R.id.et_qty_item_keranjang);
        swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout);
        layoutDelete = itemView.findViewById(R.id.layout_delete);
    }

    public void hapusItem() {
        //function delete barang
    }
}
