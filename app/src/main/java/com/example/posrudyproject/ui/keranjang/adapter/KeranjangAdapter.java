package com.example.posrudyproject.ui.keranjang.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
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

        SharedPreferences preferences = mContext.getSharedPreferences("keranjang", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        String fotoBarang = item.getImBarang() == null? "" : item.getFoto_barang();
        byte[] bytes = Base64.decode(fotoBarang.getBytes(), Base64.DEFAULT);

        Bitmap btm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.imBarang.setImageBitmap(btm);
        holder.tipeBarang.setText(item.getTipeBarang());
        holder.artikelBarang.setText(item.getArtikelBarang());
        holder.namaBarang.setText(item.getNamaBarang());
        holder.hargaBarang.setText(String.valueOf((Double.valueOf((item.getHargaBarang())))));
        holder.jumlahBarang.setText(item.getJumlahBarang());
        holder.totalHargaBarang.setText(String.valueOf((Double.valueOf((item.getHargaBarang())) * Double.valueOf(item.getJumlahBarang()))));
        holder.kuantitasBarang.setText(item.getKuantitasBarang());
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double subtotal = Double.valueOf(preferences.getString("subtotal", "0.0"));
                holder.kuantitasBarang.setText(String.valueOf(Integer.valueOf(item.getKuantitasBarang()) + 1));
                keranjangItems.get(holder.getAdapterPosition()).setKuantitasBarang(String.valueOf(Integer.valueOf(item.getKuantitasBarang()) + 1));

                holder.jumlahBarang.setText(holder.kuantitasBarang.getText());
                keranjangItems.get(holder.getAdapterPosition()).setJumlahBarang(holder.kuantitasBarang.getText().toString());

                holder.totalHargaBarang.setText(String.valueOf(Double.valueOf((item.getHargaBarang())) * Double.valueOf(holder.kuantitasBarang.getText().toString())));
                keranjangItems.get(holder.getAdapterPosition()).setTotalHargaBarang(String.valueOf(Double.valueOf((item.getHargaBarang())) * Double.valueOf(holder.kuantitasBarang.getText().toString())));

                editor.putString("subtotal", String.valueOf(subtotal + Double.valueOf((holder.hargaBarang.getText().toString()))));
                editor.apply();
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double subtotal = Double.valueOf(preferences.getString("subtotal", "0.0"));
                holder.kuantitasBarang.setText(String.valueOf(Integer.valueOf(item.getKuantitasBarang()) - 1));
                keranjangItems.get(holder.getAdapterPosition()).setKuantitasBarang(String.valueOf(Integer.valueOf(item.getKuantitasBarang()) - 1));

                holder.jumlahBarang.setText(holder.kuantitasBarang.getText());
                keranjangItems.get(holder.getAdapterPosition()).setJumlahBarang(holder.kuantitasBarang.getText().toString());

                holder.totalHargaBarang.setText(String.valueOf(Double.valueOf((item.getHargaBarang())) * Double.valueOf(holder.kuantitasBarang.getText().toString())));
                keranjangItems.get(holder.getAdapterPosition()).setTotalHargaBarang(String.valueOf(Double.valueOf((item.getHargaBarang())) * Double.valueOf(holder.kuantitasBarang.getText().toString())));
                editor.putString("subtotal", String.valueOf(subtotal - Double.valueOf(( holder.hargaBarang.getText().toString()))));
                editor.apply();
            }
        });

        //bind data by tipe
        viewBinderHelper.bind(holder.swipeRevealLayout, keranjangItems.get(position).getTipeBarang());
        //onclick layout delete item
        holder.layoutDelete.setOnClickListener(view -> new MaterialAlertDialogBuilder(mContext)
                .setTitle("Konfirmasi Hapus")
                .setMessage("Barang akan dihapus, Lanjutkan ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        holder.hapusItem();
                        keranjangItems.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
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
