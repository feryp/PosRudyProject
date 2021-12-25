package com.example.posrudyproject.ui.penyimpanan.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.model.KategoriPenyimpananItem;
import com.example.posrudyproject.ui.penyimpanan.viewholder.KategoriPenyimpananViewHolder;

import java.util.List;

public class KategoriPenyimpananAdapter extends RecyclerView.Adapter<KategoriPenyimpananViewHolder> {

    private final List<KategoriPenyimpananItem> kategoriPenyimpananItems;
    private final OnItemClickListener listener;

    public KategoriPenyimpananAdapter(List<KategoriPenyimpananItem> kategoriPenyimpananItems, OnItemClickListener listener) {
        this.kategoriPenyimpananItems = kategoriPenyimpananItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public KategoriPenyimpananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori_penyimpanan, parent,false);
        return new KategoriPenyimpananViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriPenyimpananViewHolder holder, @SuppressLint("RecyclerView") int position) {
        KategoriPenyimpananItem item = kategoriPenyimpananItems.get(position);
        holder.jumlahBarang.setText(item.getJmlBarangPenyimpanan());
        holder.kategoriBarang.setText(item.getKatBarangPenyimpanan());
        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view, position));
    }

    @Override
    public int getItemCount() {
        return kategoriPenyimpananItems.size();
    }

}
