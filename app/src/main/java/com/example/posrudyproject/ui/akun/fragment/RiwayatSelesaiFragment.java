package com.example.posrudyproject.ui.akun.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualanEndpoint;
import com.example.posrudyproject.ui.akun.activity.DetailTransaksiSelesaiActivity;
import com.example.posrudyproject.ui.akun.adapter.TransaksiSelesaiAdapter;
import com.example.posrudyproject.ui.akun.model.TransaksiSelesaiItem;
import com.example.posrudyproject.ui.pembayaran.model.Penjualan;
import com.example.posrudyproject.ui.penjualan.model.PenjualanItem;
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.example.posrudyproject.ui.produk.activity.ProdukActivity;
import com.example.posrudyproject.ui.produk.adapter.ProdukAdapter;
import com.example.posrudyproject.ui.produk.model.ProdukItem;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatSelesaiFragment extends Fragment implements OnItemClickListener {

    RecyclerView rvSelesaiTransaksi;
    TransaksiSelesaiAdapter adapter;
    List<TransaksiSelesaiItem> transaksiSelesaiItems;
    ConstraintLayout layoutEmpty;
    String auth_token;
    Integer id_store;
    PenjualanEndpoint penjualanEndpoint;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_riwayat_selesai, container, false);
        SharedPreferences preferences = getContext().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        id_store = preferences.getInt("id_store",0);
        auth_token = ("Bearer ").concat(token);
        penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);
        searchView = v.findViewById(R.id.search_transaksi);
        //INIT VIEW
        rvSelesaiTransaksi = v.findViewById(R.id.rv_selesai_transaksi);
        layoutEmpty = v.findViewById(R.id.layout_ilustrasi_empty_transaksi_selesai);
        SetupSearchView();
        //Transaksi List
        Call<List<Penjualan>> call = penjualanEndpoint.getAllPenjualanPerStore(auth_token,id_store);
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        call.enqueue(new Callback<List<Penjualan>>() {
            @Override
            public void onResponse(Call<List<Penjualan>> call, Response<List<Penjualan>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    transaksiSelesaiItems = new ArrayList<>();
                    DecimalFormat decim = new DecimalFormat("#,###.##");
                    for (int i=0; i<response.body().size(); i++){
                        transaksiSelesaiItems.add(new TransaksiSelesaiItem(
                                response.body().get(i).getId_transaksi(),
                                ("Rp").concat(decim.format(Double.valueOf(response.body().get(i).getTotal()))),
                                response.body().get(i).getMetode_bayar(),
                                response.body().get(i).getTanggal_transaksi()
                        ));
                    }

                    //Setup Adapter
                    adapter = new TransaksiSelesaiAdapter(transaksiSelesaiItems, new OnItemClickListener() {
                        @Override
                        public void onItemClickListener(View view, int position) {
                            Toast.makeText(getActivity(), "Pilih " + transaksiSelesaiItems.get(position).getNoInvoice(), Toast.LENGTH_SHORT).show();
                            Intent detailTransaksi = new Intent(getActivity(), DetailTransaksiSelesaiActivity.class);
                            detailTransaksi.putExtra("id_transaksi", response.body().get(position).getId_transaksi());
                            detailTransaksi.putExtra("bank_name", response.body().get(position).getBankName());
                            detailTransaksi.putExtra("norek", response.body().get(position).getNoRek());
                            detailTransaksi.putExtra("metode_bayar", response.body().get(position).getMetode_bayar());
                            detailTransaksi.putExtra("tanggal_transaksi", response.body().get(position).getTanggal_transaksi());
                            detailTransaksi.putExtra("nama_penjual", response.body().get(position).getNama_karyawan());
                            detailTransaksi.putExtra("nama_pelanggan", response.body().get(position).getNama_pelanggan());
                            detailTransaksi.putExtra("no_hp_pelanggan", response.body().get(position).getNo_hp_pelanggan());
                            detailTransaksi.putExtra("diskon", response.body().get(position).getDiskon() < 100 ? decim.format(response.body().get(position).getDiskon()) + "%" : ("Rp" + decim.format(response.body().get(position).getDiskon())));
                            detailTransaksi.putExtra("kembalian", response.body().get(position).getKembalian());
                            detailTransaksi.putExtra("total", response.body().get(position).getTotal());
                            detailTransaksi.putExtra("ongkir", response.body().get(position).getOngkir());
                            detailTransaksi.putExtra("detailPesanan", (Serializable) response.body().get(position).getDetailPesananList());
                            startActivity(detailTransaksi);
                        }
                    });
                    rvSelesaiTransaksi.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvSelesaiTransaksi.setAdapter(adapter);
                    rvSelesaiTransaksi.setHasFixedSize(true);

                    //Jika ada list item ilustrasi hilang
                    if (adapter.getItemCount() > 0){
                        layoutEmpty.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Penjualan>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        return v;
    }

    @Override
    public void onItemClickListener(View view, int position) {

    }

    private void SetupSearchView(){
        //final SearchView searchView = findViewById(R.id.search_barang_produk);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<List<Penjualan>> call = penjualanEndpoint.searchPerStore(auth_token,id_store,query);
                call.enqueue(new Callback<List<Penjualan>>() {
                    @Override
                    public void onResponse(Call<List<Penjualan>> call, Response<List<Penjualan>> response) {
                        transaksiSelesaiItems = new ArrayList<>();
                        DecimalFormat decim = new DecimalFormat("#,###.##");
                        for (int i=0; i<response.body().size(); i++){
                            transaksiSelesaiItems.add(new TransaksiSelesaiItem(
                                    response.body().get(i).getId_transaksi(),
                                    ("Rp").concat(decim.format(Double.valueOf(response.body().get(i).getTotal()))),
                                    response.body().get(i).getMetode_bayar(),
                                    response.body().get(i).getTanggal_transaksi()
                            ));
                        }

                        //Setup Adapter
                        adapter = new TransaksiSelesaiAdapter(transaksiSelesaiItems, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(View view, int position) {
                                Toast.makeText(getActivity(), "Pilih " + transaksiSelesaiItems.get(position).getNoInvoice(), Toast.LENGTH_SHORT).show();
                                Intent detailTransaksi = new Intent(getActivity(), DetailTransaksiSelesaiActivity.class);
                                detailTransaksi.putExtra("id_transaksi", response.body().get(position).getId_transaksi());
                                detailTransaksi.putExtra("bank_name", response.body().get(position).getBankName());
                                detailTransaksi.putExtra("norek", response.body().get(position).getNoRek());
                                detailTransaksi.putExtra("metode_bayar", response.body().get(position).getMetode_bayar());
                                detailTransaksi.putExtra("tanggal_transaksi", response.body().get(position).getTanggal_transaksi());
                                detailTransaksi.putExtra("nama_penjual", response.body().get(position).getNama_karyawan());
                                detailTransaksi.putExtra("nama_pelanggan", response.body().get(position).getNama_pelanggan());
                                detailTransaksi.putExtra("diskon", response.body().get(position).getDiskon() < 100 ? decim.format(response.body().get(position).getDiskon()) + "%" : ("Rp" + decim.format(response.body().get(position).getDiskon())));
                                detailTransaksi.putExtra("kembalian", response.body().get(position).getKembalian());
                                detailTransaksi.putExtra("total", response.body().get(position).getTotal());
                                detailTransaksi.putExtra("ongkir", response.body().get(position).getOngkir());
                                detailTransaksi.putExtra("detailPesanan", (Serializable) response.body().get(position).getDetailPesananList());
                                startActivity(detailTransaksi);
                            }
                        });
                        rvSelesaiTransaksi.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvSelesaiTransaksi.setAdapter(adapter);
                        rvSelesaiTransaksi.setHasFixedSize(true);

                        //Jika ada list item ilustrasi hilang
                        if (adapter.getItemCount() > 0){
                            layoutEmpty.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Penjualan>> call, Throwable t) {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    Call<List<Penjualan>> call = penjualanEndpoint.searchPerStore(auth_token,id_store,"");
                    call.enqueue(new Callback<List<Penjualan>>() {
                        @Override
                        public void onResponse(Call<List<Penjualan>> call, Response<List<Penjualan>> response) {
                            transaksiSelesaiItems = new ArrayList<>();
                            DecimalFormat decim = new DecimalFormat("#,###.##");
                            for (int i=0; i<response.body().size(); i++){
                                transaksiSelesaiItems.add(new TransaksiSelesaiItem(
                                        response.body().get(i).getId_transaksi(),
                                        ("Rp").concat(decim.format(Double.valueOf(response.body().get(i).getTotal()))),
                                        response.body().get(i).getMetode_bayar(),
                                        response.body().get(i).getTanggal_transaksi()
                                ));
                            }

                            //Setup Adapter
                            adapter = new TransaksiSelesaiAdapter(transaksiSelesaiItems, new OnItemClickListener() {
                                @Override
                                public void onItemClickListener(View view, int position) {
                                    Toast.makeText(getActivity(), "Pilih " + transaksiSelesaiItems.get(position).getNoInvoice(), Toast.LENGTH_SHORT).show();
                                    Intent detailTransaksi = new Intent(getActivity(), DetailTransaksiSelesaiActivity.class);
                                    detailTransaksi.putExtra("id_transaksi", response.body().get(position).getId_transaksi());
                                    detailTransaksi.putExtra("bank_name", response.body().get(position).getBankName());
                                    detailTransaksi.putExtra("norek", response.body().get(position).getNoRek());
                                    detailTransaksi.putExtra("metode_bayar", response.body().get(position).getMetode_bayar());
                                    detailTransaksi.putExtra("tanggal_transaksi", response.body().get(position).getTanggal_transaksi());
                                    detailTransaksi.putExtra("nama_penjual", response.body().get(position).getNama_karyawan());
                                    detailTransaksi.putExtra("nama_pelanggan", response.body().get(position).getNama_pelanggan());
                                    detailTransaksi.putExtra("diskon", response.body().get(position).getDiskon() < 100 ? decim.format(response.body().get(position).getDiskon()) + "%" : ("Rp" + decim.format(response.body().get(position).getDiskon())));
                                    detailTransaksi.putExtra("kembalian", response.body().get(position).getKembalian());
                                    detailTransaksi.putExtra("total", response.body().get(position).getTotal());
                                    detailTransaksi.putExtra("ongkir", response.body().get(position).getOngkir());
                                    detailTransaksi.putExtra("detailPesanan", (Serializable) response.body().get(position).getDetailPesananList());
                                    startActivity(detailTransaksi);
                                }
                            });
                            rvSelesaiTransaksi.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvSelesaiTransaksi.setAdapter(adapter);
                            rvSelesaiTransaksi.setHasFixedSize(true);

                            //Jika ada list item ilustrasi hilang
                            if (adapter.getItemCount() > 0){
                                layoutEmpty.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Penjualan>> call, Throwable t) {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                } else {
                    Call<List<Penjualan>> call = penjualanEndpoint.searchPerStore(auth_token,id_store,newText);
                    call.enqueue(new Callback<List<Penjualan>>() {
                        @Override
                        public void onResponse(Call<List<Penjualan>> call, Response<List<Penjualan>> response) {
                            transaksiSelesaiItems = new ArrayList<>();
                            DecimalFormat decim = new DecimalFormat("#,###.##");
                            for (int i=0; i<response.body().size(); i++){
                                transaksiSelesaiItems.add(new TransaksiSelesaiItem(
                                        response.body().get(i).getId_transaksi(),
                                        ("Rp").concat(decim.format(Double.valueOf(response.body().get(i).getTotal()))),
                                        response.body().get(i).getMetode_bayar(),
                                        response.body().get(i).getTanggal_transaksi()
                                ));
                            }

                            //Setup Adapter
                            adapter = new TransaksiSelesaiAdapter(transaksiSelesaiItems, new OnItemClickListener() {
                                @Override
                                public void onItemClickListener(View view, int position) {
                                    Toast.makeText(getActivity(), "Pilih " + transaksiSelesaiItems.get(position).getNoInvoice(), Toast.LENGTH_SHORT).show();
                                    Intent detailTransaksi = new Intent(getActivity(), DetailTransaksiSelesaiActivity.class);
                                    detailTransaksi.putExtra("id_transaksi", response.body().get(position).getId_transaksi());
                                    detailTransaksi.putExtra("bank_name", response.body().get(position).getBankName());
                                    detailTransaksi.putExtra("norek", response.body().get(position).getNoRek());
                                    detailTransaksi.putExtra("metode_bayar", response.body().get(position).getMetode_bayar());
                                    detailTransaksi.putExtra("tanggal_transaksi", response.body().get(position).getTanggal_transaksi());
                                    detailTransaksi.putExtra("nama_penjual", response.body().get(position).getNama_karyawan());
                                    detailTransaksi.putExtra("nama_pelanggan", response.body().get(position).getNama_pelanggan());
                                    detailTransaksi.putExtra("diskon", response.body().get(position).getDiskon() < 100 ? decim.format(response.body().get(position).getDiskon()) + "%" : ("Rp" + decim.format(response.body().get(position).getDiskon())));
                                    detailTransaksi.putExtra("kembalian", response.body().get(position).getKembalian());
                                    detailTransaksi.putExtra("total", response.body().get(position).getTotal());
                                    detailTransaksi.putExtra("ongkir", response.body().get(position).getOngkir());
                                    detailTransaksi.putExtra("detailPesanan", (Serializable) response.body().get(position).getDetailPesananList());
                                    startActivity(detailTransaksi);
                                }
                            });
                            rvSelesaiTransaksi.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvSelesaiTransaksi.setAdapter(adapter);
                            rvSelesaiTransaksi.setHasFixedSize(true);

                            //Jika ada list item ilustrasi hilang
                            if (adapter.getItemCount() > 0){
                                layoutEmpty.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Penjualan>> call, Throwable t) {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                }
                return false;
            }
        });
    }
}