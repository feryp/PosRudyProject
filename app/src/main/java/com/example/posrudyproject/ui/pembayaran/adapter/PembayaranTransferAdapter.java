package com.example.posrudyproject.ui.pembayaran.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pembayaran.model.BankItem;
import com.example.posrudyproject.ui.pembayaran.viewholder.PembayaranTransferViewHolder;

import java.util.List;

public class PembayaranTransferAdapter extends RecyclerView.Adapter<PembayaranTransferViewHolder> {

    private final List<BankItem> bankItems;
    private final Context mContext;

    public PembayaranTransferAdapter(List<BankItem> bankItems, Context mContext) {
        this.bankItems = bankItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PembayaranTransferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_bank,parent,false);
        return new PembayaranTransferViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PembayaranTransferViewHolder holder, int position) {
        BankItem item = bankItems.get(position);
        holder.logoBank.setImageResource(item.getLogoBank());
        holder.namaBank.setText(item.getNamaBank());
        holder.noRek.setText(item.getNoRekening());
    }

    @Override
    public int getItemCount() {
        return bankItems.size();
    }
}
