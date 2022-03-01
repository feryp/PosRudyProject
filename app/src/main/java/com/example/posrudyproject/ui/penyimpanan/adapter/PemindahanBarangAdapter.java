package com.example.posrudyproject.ui.penyimpanan.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.model.PemindahanBarangItem;
import com.example.posrudyproject.ui.penyimpanan.viewholder.PemindahanBarangViewHolder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class PemindahanBarangAdapter extends RecyclerView.Adapter<PemindahanBarangViewHolder> {

    private final List<PemindahanBarangItem> pemindahanBarangItems;
    private final Context mContext;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public PemindahanBarangAdapter(List<PemindahanBarangItem> pemindahanBarangItems, Context mContext) {
        this.pemindahanBarangItems = pemindahanBarangItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PemindahanBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_pemindahan,parent,false);
        return new PemindahanBarangViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PemindahanBarangViewHolder holder, int position) {
        PemindahanBarangItem item = pemindahanBarangItems.get(position);
        String fotoBarang = item.getFoto_barang() == null? "" : item.getFoto_barang();
        byte[] bytes = Base64.decode(fotoBarang.getBytes(), Base64.DEFAULT);
        Bitmap btm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.imBarang.setImageBitmap(btm);
        holder.tipeBarang.setText(item.getTipeProduk());
        holder.artikelBarang.setText(item.getArtikelProduk());
        holder.namaBarang.setText(item.getNamaProduk());
        holder.jumlahBarang.setText(item.getJumlahProduk());

        //bind data by tipe
        viewBinderHelper.bind(holder.swipeRevealLayout, pemindahanBarangItems.get(position).getTipeProduk());
        //onclick layout delete item
        holder.layoutDelete.setOnClickListener(view -> new MaterialAlertDialogBuilder(mContext)
                .setTitle("Konfirmasi Hapus")
                .setMessage("Barang akan dihapus, Lanjutkan ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        holder.hapusItem();
                        pemindahanBarangItems.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show());
    }

    @Override
    public int getItemCount() {
        return pemindahanBarangItems.size();
    }
}
