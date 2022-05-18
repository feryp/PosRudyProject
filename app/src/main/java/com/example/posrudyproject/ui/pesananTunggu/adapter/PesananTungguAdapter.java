package com.example.posrudyproject.ui.pesananTunggu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PesananTungguEndpoint;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
import com.example.posrudyproject.ui.pesananTunggu.activity.PesananTungguActivity;
import com.example.posrudyproject.ui.pesananTunggu.model.PesananTungguItem;
import com.example.posrudyproject.ui.pesananTunggu.viewholder.PesananTungguViewHolder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananTungguAdapter extends RecyclerView.Adapter<PesananTungguViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private final List<PesananTungguItem> pesananTungguItems;
    private final OnItemClickListener listener;
    PesananTungguEndpoint pesananTungguEndpoint;
    String auth_token;

    public PesananTungguAdapter(List<PesananTungguItem> pesananTungguItems,String auth_token, OnItemClickListener listener) {
        this.pesananTungguItems = pesananTungguItems;
        this.listener = listener;
        this.auth_token = auth_token;
    }

    @NonNull
    @Override
    public PesananTungguViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesanan_tunggu, parent, false);
        return new PesananTungguViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PesananTungguViewHolder holder, @SuppressLint("RecyclerView") int position) {
        pesananTungguEndpoint = ApiClient.getClient().create(PesananTungguEndpoint.class);

        PesananTungguItem item = pesananTungguItems.get(position);
        holder.id.setText(item.getId().toString());
        holder.noPesanan.setText(item.getNoPesanan());
        holder.tglPesanan.setText(item.getTglPesanan());
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
                                Call<Map> delete = pesananTungguEndpoint.delete(auth_token,item.getId());
                                delete.enqueue(new Callback<Map>() {
                                    @Override
                                    public void onResponse(Call<Map> call, Response<Map> response) {
                                        Intent back = new Intent(view.getContext(), PesananTungguActivity.class);
                                        view.getContext().startActivity(back);
                                    }

                                    @Override
                                    public void onFailure(Call<Map> call, Throwable t) {
                                        System.out.println(t.getMessage());
                                    }
                                });
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
