package com.example.posrudyproject.ui.akun.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PesananTungguEndpoint;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.pesananTunggu.activity.PesananTungguActivity;
import com.example.posrudyproject.ui.pesananTunggu.adapter.PesananTungguAdapter;
import com.example.posrudyproject.ui.pesananTunggu.model.BarangPesananTungguItem;
import com.example.posrudyproject.ui.pesananTunggu.model.PesananTungguItem;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatProsesFragment extends Fragment implements OnItemClickListener {

    RecyclerView rvProsesTransaksi;
    PesananTungguAdapter pesananTungguAdapter;
    ConstraintLayout layoutEmpty;
    PesananTungguEndpoint pesananTungguEndpoint;
    String auth_token;
    List<PesananTungguItem> pesananTungguItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_riwayat_proses, container, false);
        SharedPreferences preferences = getContext().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        auth_token = ("Bearer ").concat(token);
        pesananTungguEndpoint = ApiClient.getClient().create(PesananTungguEndpoint.class);

        //INIT VIEW
        rvProsesTransaksi = v.findViewById(R.id.rv_proses_transaksi);
        layoutEmpty = v.findViewById(R.id.layout_ilustrasi_empty_transaksi_proses);

        Call<List<PesananTungguItem>> call = pesananTungguEndpoint.getAll(auth_token);
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        call.enqueue(new Callback<List<PesananTungguItem>>() {
            @Override
            public void onResponse(Call<List<PesananTungguItem>> call, Response<List<PesananTungguItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    pesananTungguItems = new ArrayList<>();

                    for (int i=0; i<response.body().size(); i++) {
                        pesananTungguItems.add(new PesananTungguItem(
                                response.body().get(i).getId(),
                                response.body().get(i).getNoPesanan(),
                                response.body().get(i).getTglPesanan(),
                                response.body().get(i).getId_store(),
                                response.body().get(i).getLokasi_store(),
                                response.body().get(i).getNo_hp_pelanggan(),
                                response.body().get(i).getPelangganPesanan(),
                                ("Rp").concat(String.valueOf(Double.valueOf(response.body().get(i).getTotalHargaPesanan()))),
                                response.body().get(i).getKetPesanan(),
                                response.body().get(i).getBarangPesanan()
                        ));
                    }
                    //Setup Adapter
                    pesananTungguAdapter = new PesananTungguAdapter(pesananTungguItems, auth_token, new OnItemClickListener() {
                        @Override
                        public void onItemClickListener(View view, int position) {
                            Intent masukKeranjang = new Intent(getContext(), KeranjangActivity.class);
                            masukKeranjang.putExtra("itemFromQueue", (Serializable) pesananTungguItems.get(position).getBarangPesanan());
                            masukKeranjang.putExtra("namaPelanggan",pesananTungguItems.get(position).getPelangganPesanan());
                            masukKeranjang.putExtra("noHp",pesananTungguItems.get(position).getNo_hp_pelanggan());
                            Call<Map> delete = pesananTungguEndpoint.delete(auth_token,pesananTungguItems.get(position).getId());
                            delete.enqueue(new Callback<Map>() {
                                @Override
                                public void onResponse(Call<Map> call, Response<Map> response) {
                                    startActivity(masukKeranjang);
                                }

                                @Override
                                public void onFailure(Call<Map> call, Throwable t) {
                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText(t.getMessage())
                                            .show();
                                }
                            });
                        }
                    });
                    rvProsesTransaksi.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    rvProsesTransaksi.setAdapter(pesananTungguAdapter);
                    rvProsesTransaksi.setHasFixedSize(true);
                    //Jika ada list item ilustrasi hilang
                    if (pesananTungguItems.size() > 0){
                        layoutEmpty.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PesananTungguItem>> call, Throwable t) {
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
        Toast.makeText(getActivity(), "Pilih " + pesananTungguItems.get(position).getNoPesanan(), Toast.LENGTH_SHORT).show();
    }
}