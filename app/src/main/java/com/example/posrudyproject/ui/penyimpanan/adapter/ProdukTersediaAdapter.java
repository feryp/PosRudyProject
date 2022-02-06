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
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.example.posrudyproject.ui.penyimpanan.viewholder.ProdukTersediaViewHolder;

import java.util.List;

public class ProdukTersediaAdapter extends RecyclerView.Adapter<ProdukTersediaViewHolder> {

    private final List<ProdukTersediaItem> produkTersediaItems;
    private final Context mContext;

    public ProdukTersediaAdapter(List<ProdukTersediaItem> produkTersediaItems, Context mContext) {
        this.produkTersediaItems = produkTersediaItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ProdukTersediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk_tersedia_penyimpanan,parent,false);
        return new ProdukTersediaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukTersediaViewHolder holder, int position) {
        ProdukTersediaItem item = produkTersediaItems.get(position);

        byte[] bytes = Base64.decode(item.getFoto_barang().getBytes(), Base64.DEFAULT);
        Bitmap btm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.imBarang.setImageBitmap(btm);
        holder.tipeBarang.setText(item.getTipeBarang());
        holder.artikelBarang.setText(item.getArtikelBarang());
        holder.namaBarang.setText(item.getNamaBarang());
        holder.hargaBarang.setText(item.getHargaBarang());
        holder.jumlahStok.setText(item.getJumlahStok());
    }

    @Override
    public int getItemCount() {
        return produkTersediaItems.size();
    }
}
