package com.example.posrudyproject.ui.rekapKas.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.rekapKas.model.KasKeluarItem;
import com.example.posrudyproject.ui.rekapKas.model.KasMasukItem;
import com.example.posrudyproject.ui.rekapKas.viewholder.KasKeluarViewHolder;
import com.example.posrudyproject.ui.rekapKas.viewholder.KasMasukViewHolder;

import java.util.ArrayList;

public class DetailKasAdapter extends RecyclerView.Adapter {

    ArrayList<Object> itemKas;
    final static int KAS_MASUK = 1;
    final static int KAS_KELUAR = 2;

    public DetailKasAdapter(ArrayList<Object> itemKas) {
        this.itemKas = itemKas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == KAS_MASUK) {
            return new KasMasukViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kas_masuk, parent, false));
        }
        return new KasKeluarViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kas_keluar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (itemKas.get(position) instanceof KasMasukItem)
            ((KasMasukViewHolder)holder).Bind((KasMasukItem) itemKas.get(position));

        if (itemKas.get(position) instanceof KasKeluarItem)
            ((KasKeluarViewHolder)holder).Bind((KasKeluarItem) itemKas.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (itemKas.get(position) instanceof KasMasukItem)
            return KAS_MASUK;
        else
            return KAS_KELUAR;
    }

    @Override
    public int getItemCount() {
        return itemKas.size();
    }
}
