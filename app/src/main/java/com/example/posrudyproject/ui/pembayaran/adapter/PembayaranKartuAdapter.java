package com.example.posrudyproject.ui.pembayaran.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pembayaran.model.BankItem;
import com.example.posrudyproject.ui.pembayaran.viewholder.PembayaranKartuViewHolder;

import java.util.List;

public class PembayaranKartuAdapter extends RecyclerView.Adapter<PembayaranKartuViewHolder> {

    private final List<BankItem> bankItems;
    private final Context mContext;

    public PembayaranKartuAdapter(List<BankItem> bankItems, Context mContext) {
        this.bankItems = bankItems;
        this.mContext = mContext;
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
        holder.logoBank.setImageResource(item.getLogoBank());
    }

    @Override
    public int getItemCount() {
        return bankItems.size();
    }
}
