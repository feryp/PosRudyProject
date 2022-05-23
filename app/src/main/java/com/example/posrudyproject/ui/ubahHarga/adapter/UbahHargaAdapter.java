package com.example.posrudyproject.ui.ubahHarga.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.ubahHarga.viewholder.UbahHargaViewHolder;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class UbahHargaAdapter extends RecyclerView.Adapter<UbahHargaViewHolder> {

    private final List<KeranjangItem> keranjangItems;
    private final Context mContext;

    public UbahHargaAdapter(List<KeranjangItem> keranjangItems, Context mContext) {
        this.keranjangItems = keranjangItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public UbahHargaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_ubah_harga,parent,false);
        return new UbahHargaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UbahHargaViewHolder holder, int position) {
        KeranjangItem item = keranjangItems.get(position);
        String fotoBarang = item.getImBarang() == null? "" : item.getFoto_barang();
        byte[] bytes = Base64.decode(fotoBarang.getBytes(), Base64.DEFAULT);
        Bitmap btm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.imBarang.setImageBitmap(btm);
        holder.tipeBarang.setText(keranjangItems.get(position).getTipeBarang());
        holder.artikelBarang.setText(keranjangItems.get(position).getArtikelBarang());
        holder.namaBarang.setText(keranjangItems.get(position).getNamaBarang());
        holder.hargaBarang.setText(keranjangItems.get(position).getHargaBarang());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                View mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_ubah_harga, null);

                TextInputEditText etNominal = mView.findViewById(R.id.et_nominal_ubah_harga);
                TextInputEditText etKeterangan = mView.findViewById(R.id.et_ket_ubah_harga);
                MaterialButton btnSimpan = mView.findViewById(R.id.btn_simpan_harga);
                MaterialButton btnCancel = mView.findViewById(R.id.btn_cancel_dialog);

                alert.setView(mView);

                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_rounded_white));
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                btnSimpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "Simpan", Toast.LENGTH_SHORT).show();
                        holder.hargaBarang.setText(etNominal.getText());
                        keranjangItems.get(holder.getAdapterPosition()).setHargaBarang(etNominal.getText().toString());
                        keranjangItems.get(holder.getAdapterPosition()).setHarga_baru(etNominal.getText().toString());
                        keranjangItems.get(holder.getAdapterPosition()).setHarga_baru_remark(etKeterangan.getText().toString());
                        alertDialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return keranjangItems.size();
    }
}
