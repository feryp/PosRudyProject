package com.example.posrudyproject.ui.notifikasi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.notifikasi.model.NotifikasiItem;
import com.example.posrudyproject.ui.notifikasi.viewholder.NotifikasiViewHolder;

import java.util.List;

public class NotifikasiAdapter extends RecyclerView.Adapter<NotifikasiViewHolder> {

    private final List<NotifikasiItem> notifikasiItems;
    private final OnItemClickListener listener;

    public NotifikasiAdapter(List<NotifikasiItem> notifikasiItems, OnItemClickListener listener) {
        this.notifikasiItems = notifikasiItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotifikasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifikasi,parent,false);
        return new NotifikasiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifikasiViewHolder holder, int position) {
        NotifikasiItem item = notifikasiItems.get(position);
        holder.judulNotif.setText(item.getJudulNotif());
        holder.pesanNotif.setText(item.getPesanNotif());
        holder.waktuNotif.setText(item.getWaktuNotif());

        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view, position));
    }

    @Override
    public int getItemCount() {
        return notifikasiItems.size();
    }
}
