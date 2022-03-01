package com.example.posrudyproject.ui.laporan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.RiwayatTransaksiItem;
import com.example.posrudyproject.ui.laporan.viewholder.RiwayatTransaksiViewHolder;

import java.util.List;

public class RiwayatTransaksiAdapter extends RecyclerView.Adapter<RiwayatTransaksiViewHolder> implements OnItemClickListener {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private final List<RiwayatTransaksiItem> riwayatTransaksiItems;
    private final Context mContext;

    public RiwayatTransaksiAdapter(List<RiwayatTransaksiItem> riwayatTransaksiItems, Context mContext) {
        this.riwayatTransaksiItems = riwayatTransaksiItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RiwayatTransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat_transaksi_laporan, parent, false);
        return new RiwayatTransaksiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatTransaksiViewHolder holder, int position) {
        RiwayatTransaksiItem item = riwayatTransaksiItems.get(position);
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

        layoutManager.setInitialPrefetchItemCount(item.getSubRiwayatTransaksiItems().size());

        //Create sub item view adapter
        SubRiwayatTransaksiAdapter adapter = new SubRiwayatTransaksiAdapter(item.getSubRiwayatTransaksiItems(), this);
        holder.rvSubItemRiwayatTransaksi.setLayoutManager(layoutManager);
        holder.rvSubItemRiwayatTransaksi.setAdapter(adapter);
        holder.rvSubItemRiwayatTransaksi.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return riwayatTransaksiItems.size();
    }

    @Override
    public void onItemClickListener(View view, int position) {
        //FUNCTION SUB ITEM
    }
}
