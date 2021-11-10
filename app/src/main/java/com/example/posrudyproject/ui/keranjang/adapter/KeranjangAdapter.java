package com.example.posrudyproject.ui.keranjang.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.keranjang.viewholder.KeranjangViewHolder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangViewHolder> {

    private final List<KeranjangItem> keranjangItems;
    private final Context mContext;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public KeranjangAdapter(List<KeranjangItem> keranjangItems, Context mContext) {
        this.keranjangItems = keranjangItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public KeranjangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_keranjang,parent,false);
        return new KeranjangViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KeranjangViewHolder holder, int position) {
        KeranjangItem item = keranjangItems.get(position);
        holder.imBarang.setImageResource(item.getImBarang());
        holder.tipeBarang.setText(item.getTipeBarang());
        holder.artikelBarang.setText(item.getArtikelBarang());
        holder.namaBarang.setText(item.getNamaBarang());
        holder.hargaBarang.setText(item.getHargaBarang());
        holder.jumlahBarang.setText(item.getJumlahBarang());
        holder.totalHargaBarang.setText(item.getTotalHargaBarang());
        holder.kuantitasBarang.setText(item.getKuantitasBarang());

        //bind data by tipe
        viewBinderHelper.bind(holder.swipeRevealLayout, keranjangItems.get(position).getTipeBarang());
        //onclick layout delete item
        holder.layoutDelete.setOnClickListener(view -> new MaterialAlertDialogBuilder(mContext)
                .setTitle("Konfirmasi Hapus")
                .setMessage("Pesanan akan dihapus, lanjutkan ?")
                .setPositiveButton("Lanjutkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        holder.hapusItem();
                        keranjangItems.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show());

    }

    @Override
    public int getItemCount() {
        return keranjangItems.size();
    }
}
