package com.example.posrudyproject.ui.akun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.akun.model.PenukaranBarangItem;
import com.example.posrudyproject.ui.akun.viewholder.PenukaranBarangViewHolder;

import java.util.List;

public class PenukaranBarangAdapter extends RecyclerView.Adapter<PenukaranBarangViewHolder> {

    private final List<PenukaranBarangItem> penukaranBarangItems;
    private final Context mContext;

    public PenukaranBarangAdapter(List<PenukaranBarangItem> penukaranBarangItems, Context mContext) {
        this.penukaranBarangItems = penukaranBarangItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PenukaranBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_penukaran,parent,false);
        return new PenukaranBarangViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PenukaranBarangViewHolder holder, int position) {
        PenukaranBarangItem item = penukaranBarangItems.get(position);
        holder.checkBox.setChecked(item.isChecked());
        holder.tipeBarang.setText(item.getTipeBarang());
        holder.artikelBarang.setText(item.getArtikelBarang());
        holder.namaBarang.setText(item.getNamaBarang());
        holder.jumlahBarang.setText(item.getJumlahBarang());
        holder.hargaBarang.setText(item.getHargaBarang());
    }

    @Override
    public int getItemCount() {
        return penukaranBarangItems.size();
    }
}
