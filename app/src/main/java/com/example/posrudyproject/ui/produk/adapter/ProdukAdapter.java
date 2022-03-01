package com.example.posrudyproject.ui.produk.adapter;

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
import com.example.posrudyproject.ui.produk.model.ProdukItem;
import com.example.posrudyproject.ui.produk.viewholder.ProdukViewHolder;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukViewHolder> {

    private final List<ProdukItem> produkItems;
    private final Context mContext;

    public ProdukAdapter(List<ProdukItem> produkItems, Context mContext) {
        this.produkItems = produkItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk,parent,false);
        return new ProdukViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukViewHolder holder, int position) {
        ProdukItem item = produkItems.get(position);
        byte[] bytes = Base64.decode(item.getFoto_barang().getBytes(), Base64.DEFAULT);
        Bitmap btm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.imProduk.setImageBitmap(btm);
        holder.tipeProduk.setText(item.getTipeProduk());
        holder.artikelProduk.setText(item.getArtikelProduk());
        holder.namaProduk.setText(item.getNamaProduk());
        holder.stokProduk.setText(item.getStokProduk());
    }

    @Override
    public int getItemCount() {
        return produkItems.size();
    }
}
