package com.example.posrudyproject.ui.penyimpanan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.model.PemindahanBarangItem;
import com.example.posrudyproject.ui.penyimpanan.viewholder.PreviewPemindahanViewHolder;

import java.util.List;

public class PreviewPemindahanAdapter extends RecyclerView.Adapter<PreviewPemindahanViewHolder>{

    private final List<PemindahanBarangItem> pemindahanBarangItems;
    private final Context mContext;

    public PreviewPemindahanAdapter(List<PemindahanBarangItem> pemindahanBarangItems, Context mContext) {
        this.pemindahanBarangItems = pemindahanBarangItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PreviewPemindahanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_preview_pemindahan,parent,false);
        return new PreviewPemindahanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewPemindahanViewHolder holder, int position) {
        PemindahanBarangItem item = pemindahanBarangItems.get(position);
        String fotoBarang = item.getFoto_barang() == null? "" : item.getFoto_barang();
        byte[] bytes = Base64.decode(fotoBarang.getBytes(), Base64.DEFAULT);
        Bitmap btm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.imBarang.setImageBitmap(btm);
        holder.tipeBarang.setText(item.getTipeProduk());
        holder.artikelBarang.setText(item.getArtikelProduk());
        holder.namaBarang.setText(item.getNamaProduk());
        holder.jumlahBarang.setText(item.getJumlahProduk());
    }

    @Override
    public int getItemCount() {
        return pemindahanBarangItems.size();
    }
}
