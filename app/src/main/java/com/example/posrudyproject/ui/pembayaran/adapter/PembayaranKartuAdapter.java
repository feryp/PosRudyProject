package com.example.posrudyproject.ui.pembayaran.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pembayaran.model.BankItem;
import com.example.posrudyproject.ui.pembayaran.viewholder.PembayaranKartuViewHolder;

import java.util.List;

public class PembayaranKartuAdapter extends RecyclerView.Adapter<PembayaranKartuViewHolder> {

    private final List<BankItem> bankItems;
    private final OnItemClickListener listener;

    public PembayaranKartuAdapter(List<BankItem> bankItems, OnItemClickListener listener) {
        this.bankItems = bankItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PembayaranKartuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_bank,parent,false);
        return new PembayaranKartuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PembayaranKartuViewHolder holder, int position) {
        BankItem item = bankItems.get(position);
        String fotoBarang = item.getLogoBank();
        if (fotoBarang != null && !fotoBarang.isEmpty()) {
            byte[] bytes = Base64.decode(fotoBarang, Base64.DEFAULT);

            // Decode bitmap with inJustDecodeBounds = true to get dimensions
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

            // Calculate sample size (scale down the image)
            options.inSampleSize = calculateInSampleSize(options, 200, 200); // Target size
            options.inJustDecodeBounds = false;

            // Decode the actual bitmap
            Bitmap btm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            holder.logoBank.setImageBitmap(btm);
        }

        holder.namaBank.setText(item.getNamaBank());
        holder.noRek.setText(item.getNoRekening());
        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view, position));
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;

            // Scale down while keeping power of 2
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    @Override
    public int getItemCount() {
        return bankItems.size();
    }
}
