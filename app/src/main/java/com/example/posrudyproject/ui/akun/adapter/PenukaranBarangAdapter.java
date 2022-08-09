package com.example.posrudyproject.ui.akun.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.akun.model.PenukaranBarangItem;
import com.example.posrudyproject.ui.akun.viewholder.PenukaranBarangViewHolder;

import java.io.Serializable;
import java.util.List;

public class PenukaranBarangAdapter extends RecyclerView.Adapter<PenukaranBarangViewHolder> {

    private final List<PenukaranBarangItem> penukaranBarangItems;
    private final Context mContext;
    public static final String INTENT_PENUKARAN = "INTENT_PENUKARAN";

    public PenukaranBarangAdapter(List<PenukaranBarangItem> penukaranBarangItems, Context mContext) {
        this.penukaranBarangItems = penukaranBarangItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PenukaranBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_penukaran,parent,false);
        return new PenukaranBarangViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PenukaranBarangViewHolder holder, int position) {
        PenukaranBarangItem item = penukaranBarangItems.get(position);
        SharedPreferences preferences = mContext.getSharedPreferences("penukaranBarang", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        holder.tipeBarang.setText(item.getTipeBarang());
        holder.artikelBarang.setText(item.getArtikelBarang());
        holder.namaBarang.setText(item.getNamaBarang());
        holder.jumlahBarang.setText(item.getJumlahBarang());
        holder.hargaBarang.setText(item.getHargaBarang());

        editor.putString(item.getArtikelBarang(), item.getJumlahBarang());
        //editor.putString("totalHarga", String.valueOf(Double.valueOf(item.getJumlahBarang()) * Double.valueOf((item.getHargaBarang().replace(".","")).replace("Rp",""))));
        editor.apply();

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double total = Double.valueOf(preferences.getString("totalHarga", "0.00").replace(".",""));
                holder.jumlahBarang.setText(String.valueOf(Integer.valueOf(holder.jumlahBarang.getText().toString()) + 1));

                editor.putString(item.getArtikelBarang(), holder.jumlahBarang.getText().toString());
                editor.putString("totalHarga", String.valueOf(total + Double.valueOf(((item.getHargaBarang().replace(".","")).replace("Rp", "")).replace(".0",""))));
                editor.apply();
                if (Integer.valueOf(holder.jumlahBarang.getText().toString()) == Integer.valueOf(item.getJumlahBarang())) {
                    holder.btnPlus.setEnabled(false);
                    holder.btnMinus.setEnabled(true);
                }
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double total = Double.valueOf(preferences.getString("totalHarga", "0.00").replace(".",""));
                holder.jumlahBarang.setText(String.valueOf(Integer.valueOf(holder.jumlahBarang.getText().toString()) - 1));
                editor.putString(item.getArtikelBarang(), holder.jumlahBarang.getText().toString());
                editor.putString("totalHarga", String.valueOf(total - Double.valueOf(((item.getHargaBarang().replace(".","")).replace("Rp", "")).replace(".0",""))));
                editor.apply();
                if (Integer.valueOf(holder.jumlahBarang.getText().toString()) == 0) {
                    holder.btnPlus.setEnabled(true);
                    holder.btnMinus.setEnabled(false);
                }
            }
        });


        holder.btnPlus.setEnabled(false);
        holder.btnMinus.setEnabled(true);


        /*holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Double total = Double.valueOf(preferences.getString("totalHarga", "0.00").replace(".",""));
                if (b) {
                    if (holder.jumlahBarang.getText().toString().equals("1")) {
                        holder.btnMinus.setEnabled(false);
                        holder.btnPlus.setEnabled(false);
                    } else if (holder.jumlahBarang.getText().toString().equals(item.getJumlahBarang())) {
                        holder.btnMinus.setEnabled(true);
                        holder.btnPlus.setEnabled(false);
                    } else {
                        holder.btnMinus.setEnabled(true);
                        holder.btnPlus.setEnabled(true);
                    }
                    editor.putBoolean(item.getArtikelBarang()+ "-checkBox", true);
                    editor.putString("totalHarga",String.valueOf(total + (Double.valueOf(item.getJumlahBarang()) * Double.valueOf(((item.getHargaBarang().replace(".","")).replace("Rp", "")).replace(".0","")))));
                } else {
                    holder.btnMinus.setEnabled(false);
                    holder.btnPlus.setEnabled(false);
                    holder.jumlahBarang.setText(item.getJumlahBarang());
                    editor.putBoolean(item.getArtikelBarang()+ "-checkBox", false);
                    if (total.equals(Double.valueOf(((item.getHargaBarang().replace(".","")).replace("Rp", "")).replace(".0","")))) {
                        editor.putString("totalHarga", "0.00");
                    } else if (total < Double.valueOf(((item.getHargaBarang().replace(".","")).replace("Rp", "")).replace(".0",""))) {
                        editor.putString("totalHarga", "0.00");
                    } else {
                        editor.putString("totalHarga",String.valueOf(total - (Double.valueOf(item.getJumlahBarang()) * Double.valueOf(((item.getHargaBarang().replace(".","")).replace("Rp", "")).replace(".0","")))));
                    }
                    System.out.println(total);
                    System.out.println(Double.valueOf(((item.getHargaBarang().replace(".","")).replace("Rp", "")).replace(".0","")));


                }
                editor.apply();
            }
        });*/

        if (!holder.btnPlus.isEnabled() && !holder.btnMinus.isEnabled()) {
            editor.putString("totalHarga", "0.00");
            editor.apply();
        }
    }

    @Override
    public int getItemCount() {
        return penukaranBarangItems.size();
    }
}
