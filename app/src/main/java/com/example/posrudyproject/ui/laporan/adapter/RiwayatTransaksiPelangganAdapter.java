package com.example.posrudyproject.ui.laporan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiPelangganItem;
import com.example.posrudyproject.ui.laporan.viewholder.RiwayatTransaksiPelangganViewHolder;

import java.util.List;

public class RiwayatTransaksiPelangganAdapter extends RecyclerView.Adapter<RiwayatTransaksiPelangganViewHolder> implements OnItemClickListener {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private final List<RiwayatTransaksiPelangganItem> riwayatTransaksiPelangganItems;
    private final Context mContext;

    public RiwayatTransaksiPelangganAdapter(List<RiwayatTransaksiPelangganItem> riwayatTransaksiPelangganItems, Context mContext) {
        this.riwayatTransaksiPelangganItems = riwayatTransaksiPelangganItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RiwayatTransaksiPelangganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat_transaksi_pelanggan, parent, false);
        return new RiwayatTransaksiPelangganViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatTransaksiPelangganViewHolder holder, int position) {
        RiwayatTransaksiPelangganItem item = riwayatTransaksiPelangganItems.get(position);

        holder.bind(item);
        holder.itemView.setOnClickListener(v -> {
            // Get the current state of the item
            boolean expanded = item.isExpanded();
            // Change the state
            item.setExpanded(!expanded);
            // Notify the adapter that item has changed
            notifyItemChanged(position);
        });

        //Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rvSubItemRiwayatTransaksi.getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManager.setInitialPrefetchItemCount(item.getSubRiwayatTransaksiPelangganItems().size());

        //Create sub item view adapter
        SubRiwayatTransaksiPelangganAdapter adapter = new SubRiwayatTransaksiPelangganAdapter(item.getSubRiwayatTransaksiPelangganItems(), this);
        holder.rvSubItemRiwayatTransaksi.setLayoutManager(layoutManager);
        holder.rvSubItemRiwayatTransaksi.setAdapter(adapter);
        holder.rvSubItemRiwayatTransaksi.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return riwayatTransaksiPelangganItems.size();
    }

    @Override
    public void onItemClickListener(View view, int position) {
        //FUNCTION SUB ITEM
    }
}
