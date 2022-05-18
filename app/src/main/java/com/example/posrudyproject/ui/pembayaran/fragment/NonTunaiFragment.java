package com.example.posrudyproject.ui.pembayaran.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualanEndpoint;
import com.example.posrudyproject.ui.pembayaran.activity.PembayaranActivity;
import com.example.posrudyproject.ui.pembayaran.adapter.PembayaranKartuAdapter;
import com.example.posrudyproject.ui.pembayaran.adapter.PembayaranTransferAdapter;
import com.example.posrudyproject.ui.pembayaran.model.BankItem;
import com.example.posrudyproject.ui.pembayaran.model.Penjualan;
import com.example.posrudyproject.ui.penjual.activity.PenjualActivity;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
import com.example.posrudyproject.ui.penjualan.activity.TransaksiSuksesActivity;
import com.example.posrudyproject.ui.pesananTunggu.activity.PesananTungguActivity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NonTunaiFragment extends Fragment implements OnItemClickListener {

    private RecyclerView rvDebit, rvKredit, rvTransfer;
    private List<BankItem> bankItems;
    private PembayaranKartuAdapter cardAdapter;
    private PembayaranTransferAdapter transferAdapter;
    PenjualanEndpoint penjualanEndpoint;
    String metode,bank_name,norek,auth_token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_non_tunai, container, false);
        Bundle args = getArguments();
        if (args != null) {
            this.bankItems = (List<BankItem>) args.getSerializable("bankList");
        }
        SharedPreferences preferences = getContext().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        auth_token = ("Bearer ").concat(token);
        penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);
        //INIT VIEW
        rvDebit = v.findViewById(R.id.rv_debit_bank);
        rvKredit = v.findViewById(R.id.rv_kartu_kredit);
        rvTransfer = v .findViewById(R.id.rv_transfer_bank);
        metode = "";
        bank_name = "";
        norek = "";

        //CARD BANK LIST
        Call<List<BankItem>> call = penjualanEndpoint.getAllBank(auth_token);
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<List<BankItem>>() {
            @Override
            public void onResponse(Call<List<BankItem>> call, Response<List<BankItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    bankItems = new ArrayList<>();
                    for (int i=0; i<response.body().size(); i++) {
                        bankItems.add(new BankItem(
                                response.body().get(i).getLogoBank(),
                                response.body().get(i).getNamaBank(),
                                response.body().get(i).getNoRekening()
                        ));
                    }

                    cardAdapter = new PembayaranKartuAdapter(bankItems, new OnItemClickListener() {
                        @Override
                        public void onItemClickListener(View view, int position) {
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                            sweetAlertDialog.setTitleText("Transaksi sudah benar?");
                            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    PembayaranActivity pembayaranActivity = (PembayaranActivity) getActivity();
                                    penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);
                                    metode = "";
                                    bank_name = "";
                                    norek = "";

                                    BankItem item = bankItems.get(position);

                                    Call<List<Penjualan>> call = penjualanEndpoint.savePenjualan(auth_token, pembayaranActivity.konfirmasiPenjualan("Non Tunai" , item.getNamaBank() , item.getNoRekening()));
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
                                                Intent lanjut = new Intent(getActivity(), TransaksiSuksesActivity.class);
                                                for (int i=0; i<response.body().size(); i++) {

                                                    lanjut.putExtra("id_transaksi", response.body().get(i).getId_transaksi());
                                                    lanjut.putExtra("items",(Serializable) response.body().get(i).getDetailPesananList());
                                                    lanjut.putExtra("diskon", response.body().get(i).getDiskon());
                                                    lanjut.putExtra("kembalian",response.body().get(i).getKembalian());
                                                    lanjut.putExtra("total",response.body().get(i).getTotal());
                                                    lanjut.putExtra("ongkir", response.body().get(i).getOngkir());
                                                    lanjut.putExtra("ekspedisi", response.body().get(i).getEkspedisi());
                                                    lanjut.putExtra("namaPelanggan", response.body().get(i).getNama_pelanggan());
                                                    lanjut.putExtra("noHpPelanggan", response.body().get(i).getNo_hp_pelanggan());
                                                    lanjut.putExtra("namaPenjual", response.body().get(i).getNama_karyawan());
                                                    lanjut.putExtra("idPenjual", response.body().get(i).getId_karyawan());
                                                }
                                                lanjut.putExtra("metode_bayar", "Non Tunai");
                                                lanjut.putExtra("bank_name", item.getNamaBank());
                                                lanjut.putExtra("no_rek", item.getNoRekening());

                                                if (pembayaranActivity.Details().get("diskonRupiah") != null) {
                                                    lanjut.putExtra("tipe_diskon", "Rp");
                                                } else if (pembayaranActivity.Details().get("diskonPersen") != null){
                                                    lanjut.putExtra("tipe_diskon", "%");
                                                }
                                                startActivity(lanjut);
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
                                }
                            });
                            sweetAlertDialog.show();
                        }
                    });
                    transferAdapter = new PembayaranTransferAdapter(bankItems, new OnItemClickListener() {
                        @Override
                        public void onItemClickListener(View view, int position) {
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                            sweetAlertDialog.setTitleText("Transaksi sudah benar?");
                            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    PembayaranActivity pembayaranActivity = (PembayaranActivity) getActivity();
                                    penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);
                                    metode = "";
                                    bank_name = "";
                                    norek = "";

                                    BankItem item = bankItems.get(position);

                                    Call<List<Penjualan>> call = penjualanEndpoint.savePenjualan(auth_token, pembayaranActivity.konfirmasiPenjualan("Non Tunai" , item.getNamaBank() , item.getNoRekening()));
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
                                                Intent lanjut = new Intent(getActivity(), TransaksiSuksesActivity.class);
                                                for (int i=0; i<response.body().size(); i++) {

                                                    lanjut.putExtra("id_transaksi", response.body().get(i).getId_transaksi());
                                                    lanjut.putExtra("items",(Serializable) response.body().get(i).getDetailPesananList());
                                                    lanjut.putExtra("diskon", response.body().get(i).getDiskon());
                                                    lanjut.putExtra("kembalian",response.body().get(i).getKembalian());
                                                    lanjut.putExtra("total",response.body().get(i).getTotal());
                                                    lanjut.putExtra("ongkir", response.body().get(i).getOngkir());
                                                    lanjut.putExtra("ekspedisi", response.body().get(i).getEkspedisi());
                                                    lanjut.putExtra("namaPelanggan", response.body().get(i).getNama_pelanggan());
                                                    lanjut.putExtra("noHpPelanggan", response.body().get(i).getNo_hp_pelanggan());
                                                    lanjut.putExtra("namaPenjual", response.body().get(i).getNama_karyawan());
                                                    lanjut.putExtra("idPenjual", response.body().get(i).getId_karyawan());
                                                }
                                                lanjut.putExtra("metode_bayar", "Non Tunai");
                                                lanjut.putExtra("bank_name", item.getNamaBank());
                                                lanjut.putExtra("no_rek", item.getNoRekening());

                                                if (pembayaranActivity.Details().get("diskonRupiah") != null) {
                                                    lanjut.putExtra("tipe_diskon", "Rp");
                                                } else if (pembayaranActivity.Details().get("diskonPersen") != null){
                                                    lanjut.putExtra("tipe_diskon", "%");
                                                }
                                                startActivity(lanjut);
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
                                }
                            });
                            sweetAlertDialog.show();
                        }
                    });

                    //Setup adapter Debit
                    rvDebit.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    rvDebit.setAdapter(cardAdapter);
                    rvDebit.setHasFixedSize(true);

                    //Setup adapter Kartu Kredit
                    rvKredit.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    rvKredit.setAdapter(cardAdapter);
                    rvKredit.setHasFixedSize(true);

                    //Setup adapter Transfer Bank
                    rvTransfer.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvTransfer.setAdapter(transferAdapter);
                    rvTransfer.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<List<BankItem>> call, Throwable t) {
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


}