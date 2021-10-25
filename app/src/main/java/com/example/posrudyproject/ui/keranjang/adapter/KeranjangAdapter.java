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

    private List<KeranjangItem> keranjangItems;
    private Context mContext;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

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
        holder.imBarang.setImageResource(keranjangItems.get(position).getImBarang());
        holder.tipeBarang.setText(keranjangItems.get(position).getTipeBarang());
        holder.artikelBarang.setText(keranjangItems.get(position).getArtikelBarang());
        holder.namaBarang.setText(keranjangItems.get(position).getNamaBarang());
        holder.hargaBarang.setText(keranjangItems.get(position).getHargaBarang());
        holder.jumlahBarang.setText(keranjangItems.get(position).getJumlahBarang());
        holder.totalHargaBarang.setText(keranjangItems.get(position).getTotalHargaBarang());
        holder.kuantitasBarang.setText(keranjangItems.get(position).getKuantitasBarang());

        //bind data by tipe
        viewBinderHelper.bind(holder.swipeRevealLayout, keranjangItems.get(position).getTipeBarang());
        //onclick layout delete item
        holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(mContext)
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
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return keranjangItems.size();
    }
}
