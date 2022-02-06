package com.example.posrudyproject.ui.penjual.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penjual.model.PenjualItem;
import com.example.posrudyproject.ui.penjual.viewholder.PenjualViewHolder;

import android.util.Base64;
import java.util.List;

public class PenjualAdapter extends RecyclerView.Adapter<PenjualViewHolder> {

    private final List<PenjualItem> penjualItems;
    private final OnItemClickListener listener;

    PenjualItem selectedItemPositon = null;

    public PenjualAdapter(List<PenjualItem> penjualItems, OnItemClickListener listener) {
        this.penjualItems = penjualItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PenjualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penjual, parent, false);
        return new PenjualViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PenjualItem item = penjualItems.get(position);

        byte[] bytes = Base64.decode(item.getImage().getBytes(), Base64.DEFAULT);
        Bitmap btm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.fotoPenjual.setImageBitmap(btm);
        holder.namaPenjual.setText(item.getNama_karyawan());
        holder.jabatanPenjual.setText(item.getJabatan());

        if (selectedItemPositon != null){
            if (selectedItemPositon.getNama_karyawan().equals(penjualItems.get(position).getNama_karyawan())){
                holder.itemView.setBackgroundResource(R.drawable.bg_rounded_neon_blue_shadow_outlined);
            } else {
                holder.itemView.setBackgroundResource(R.drawable.bg_container_shadow);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if (listener != null) listener.onItemClickListener(view, position);
                selectItem(penjualItems.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return penjualItems.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void selectItem(PenjualItem selected){
        selectedItemPositon = selected;
        notifyDataSetChanged();
    }

}
