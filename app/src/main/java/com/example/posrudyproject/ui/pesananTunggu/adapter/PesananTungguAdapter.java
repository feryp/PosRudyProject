package com.example.posrudyproject.ui.pesananTunggu.adapter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.pesananTunggu.model.PesananTungguItem;
import com.example.posrudyproject.ui.pesananTunggu.viewholder.PesananTungguViewHolder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class PesananTungguAdapter extends RecyclerView.Adapter<PesananTungguViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private final List<PesananTungguItem> pesananTungguItems;
    private final OnItemClickListener listener;

    public PesananTungguAdapter(List<PesananTungguItem> pesananTungguItems, OnItemClickListener listener) {
        this.pesananTungguItems = pesananTungguItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PesananTungguViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesanan_tunggu, parent, false);
        return new PesananTungguViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PesananTungguViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PesananTungguItem item = pesananTungguItems.get(position);
        holder.noPesanan.setText(item.getNoPesanan());
        holder.tglPesanan.setText(item.getTglPesanan());
        holder.jamPesanan.setText(item.getJamPesanan());
        holder.totalHargaPesanan.setText(item.getTotalHargaPesanan());
        holder.ketPesanan.setText(item.getKetPesanan());
        holder.pelangganPesanan.setText(item.getPelangganPesanan());
        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(view.getContext())
                        .setTitle("Konfirmasi Hapus")
                        .setMessage("Pesanan tunggu akan dihapus, Lanjutkan ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Function delete
                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });

        //Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rvItemPesanan.getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManager.setInitialPrefetchItemCount(item.getBarangPesanan().size());

        //Create sub item view adapter
        BarangPesananAdapter adapter = new BarangPesananAdapter(item.getBarangPesanan());
        holder.rvItemPesanan.setLayoutManager(layoutManager);
        holder.rvItemPesanan.setAdapter(adapter);
        holder.rvItemPesanan.setRecycledViewPool(viewPool);


        holder.itemView.setOnClickListener(view -> listener.onItemClickListener(view,position));
    }

    @Override
    public int getItemCount() {
        return pesananTungguItems.size();
    }
}
