package com.example.posrudyproject.ui.penjualan.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerTipeItem;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
import com.example.posrudyproject.ui.penjualan.model.PenjualanItem;
import com.example.posrudyproject.ui.penjualan.viewholder.PenjualanViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PenjualanAdapter extends RecyclerView.Adapter<PenjualanViewHolder> {

    private final List<PenjualanItem> penjualanItems;
    private final Context mContext;
    List<KeranjangItem> keranjangItems= new ArrayList<>();
    public static final String INTENT_KERANJANG = "INTENT_KERANJANG";

    public PenjualanAdapter(List<PenjualanItem> penjualanItems, Context mContext) {
        this.penjualanItems = penjualanItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new PenjualanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualanViewHolder holder, int position) {
        PenjualanItem item = penjualanItems.get(position);
        SharedPreferences preferences = mContext.getSharedPreferences("keranjang", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Integer kuantitas = Integer.valueOf(preferences.getString(item.getArtikelBarang(), "0"));

        keranjangItems.add(new KeranjangItem(
                item.getFoto_barang(),
                item.getTipeBarang(),
                item.getSkuCode(),
                item.getArtikelBarang(),
                item.getNamaBarang(),
                item.getHargaBarang(),
                item.getHargaBarang(),
                "",
                String.valueOf(kuantitas),
                String.valueOf(Double.valueOf(item.getHargaBarang()) * Double.valueOf(kuantitas)),
                String.valueOf(kuantitas)
        ));

        String fotoBarang = item.getFoto_barang() == null? "" : item.getFoto_barang();
        byte[] bytes = Base64.decode(fotoBarang.getBytes(), Base64.DEFAULT);
        Bitmap btm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        holder.imBarang.setImageBitmap(btm);
        holder.tipeBarang.setText(item.getTipeBarang());
        holder.skuCode.setText(item.getSkuCode());
        holder.artikelBarang.setText(item.getArtikelBarang());
        holder.namaBarang.setText(item.getNamaBarang());
        holder.hargaBarang.setText(item.getHargaBarang());
        holder.kuantitasBarang.setText(String.valueOf(kuantitas));

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.kuantitasBarang.setText(String.valueOf(Integer.valueOf(holder.kuantitasBarang.getText().toString()) + 1));
                int i = findIndex(item.getArtikelBarang() , keranjangItems);
                keranjangItems.get(i).setKuantitasBarang(holder.kuantitasBarang.getText().toString());
                keranjangItems.get(i).setTotalHargaBarang(String.valueOf(Double.valueOf(item.getHargaBarang()) * Double.valueOf(holder.kuantitasBarang.getText().toString())));
                keranjangItems.get(i).setJumlahBarang(holder.kuantitasBarang.getText().toString());
                editor.putString(item.getArtikelBarang(), holder.kuantitasBarang.getText().toString());
                editor.apply();
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.kuantitasBarang.setText(String.valueOf(Integer.valueOf(holder.kuantitasBarang.getText().toString()) - 1));
                int i = findIndex(item.getArtikelBarang() , keranjangItems);
                keranjangItems.get(i).setKuantitasBarang(holder.kuantitasBarang.getText().toString());
                keranjangItems.get(i).setTotalHargaBarang(String.valueOf(Double.valueOf(item.getHargaBarang()) * Double.valueOf(holder.kuantitasBarang.getText().toString())));
                keranjangItems.get(i).setJumlahBarang(holder.kuantitasBarang.getText().toString());
                editor.putString(item.getArtikelBarang(), holder.kuantitasBarang.getText().toString());
                editor.apply();
            }

        });

        Intent intent = new Intent(INTENT_KERANJANG);
        intent.putExtra("keranjangItem", (Serializable) keranjangItems);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return penjualanItems.size();
    }

    public static <T> int findIndex(T item, List<KeranjangItem> items) {
        for (int i=0; i<items.size(); i++) {
            if (items.get(i).getArtikelBarang() != null && items.get(i).getArtikelBarang().equals(item) || item == null && items.get(i).getArtikelBarang() == null) {
                return i;
            }
        }
        return -1;
    }

}
