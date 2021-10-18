package com.example.posrudyproject.ui.penjualan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
import com.example.posrudyproject.ui.penjualan.model.KategoriItem;
import com.example.posrudyproject.ui.penjualan.viewholder.KategoriViewHolder;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriViewHolder> {

    private List<KategoriItem> kategoriItems;
    private Context mContext;

    public KategoriAdapter(List<KategoriItem> kategoriItems, Context mContext) {
        this.kategoriItems = kategoriItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public KategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori, parent, false);
        return new KategoriViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriViewHolder holder, int position) {
        holder.namaKetegori.setText(kategoriItems.get(position).getNamaKetegori());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Kategori",Toast.LENGTH_SHORT).show();
                Intent kategori = new Intent(mContext, PenjualanActivity.class);
                mContext.startActivity(kategori);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kategoriItems.size();
    }
}
