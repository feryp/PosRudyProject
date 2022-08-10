package com.example.posrudyproject.ui.pembayaran.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualanEndpoint;
import com.example.posrudyproject.ui.pembayaran.activity.PembayaranActivity;
import com.example.posrudyproject.ui.pembayaran.model.Penjualan;
import com.example.posrudyproject.ui.penjualan.activity.TransaksiSuksesActivity;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TunaiFragment extends Fragment implements View.OnClickListener {

    AppCompatEditText etUangDiterima;
    MaterialButton btnUangPas, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btn00, btnDel, btnClear, btnLanjut;
    public static final String INTENT_TUNAI_PAS = "INTENT_TUNAI_PAS";
    String curr;
    PenjualanEndpoint penjualanEndpoint;
    Penjualan penjualan;
    String auth_token,lokasi_store,nama_karyawan;
    Integer id_store,id_karyawan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tunai, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        auth_token = ("Bearer ").concat(token);
        id_store = preferences.getInt("id_store", 0);
        lokasi_store = preferences.getString("lokasi_store","");
        id_karyawan = preferences.getInt("id_pengguna", 0);
        nama_karyawan = preferences.getString("nama_pengguna","");
        curr = "";

        //INIT VIEW
        etUangDiterima = v.findViewById(R.id.et_uang_diterima);
        btnUangPas = v.findViewById(R.id.btn_uang_pas);
        btn1 = v.findViewById(R.id.btn_1);
        btn2 = v.findViewById(R.id.btn_2);
        btn3 = v.findViewById(R.id.btn_3);
        btn4 = v.findViewById(R.id.btn_4);
        btn5 = v.findViewById(R.id.btn_5);
        btn6 = v.findViewById(R.id.btn_6);
        btn7 = v.findViewById(R.id.btn_7);
        btn8 = v.findViewById(R.id.btn_8);
        btn9 = v.findViewById(R.id.btn_9);
        btn0 = v.findViewById(R.id.btn_0);
        btn00 = v.findViewById(R.id.btn_00);

        btnClear = v.findViewById(R.id.btn_clear);
        btnDel = v.findViewById(R.id.btn_delete);
        btnLanjut = v.findViewById(R.id.btn_lanjut);

        //SET LISTENER
        btnUangPas.setOnClickListener(TunaiFragment.this);
        btn1.setOnClickListener(TunaiFragment.this);
        btn2.setOnClickListener(TunaiFragment.this);
        btn3.setOnClickListener(TunaiFragment.this);
        btn4.setOnClickListener(TunaiFragment.this);
        btn5.setOnClickListener(TunaiFragment.this);
        btn6.setOnClickListener(TunaiFragment.this);
        btn7.setOnClickListener(TunaiFragment.this);
        btn8.setOnClickListener(TunaiFragment.this);
        btn9.setOnClickListener(TunaiFragment.this);
        btn0.setOnClickListener(TunaiFragment.this);
        btn00.setOnClickListener(TunaiFragment.this);
        btnClear.setOnClickListener(TunaiFragment.this);
        btnDel.setOnClickListener(TunaiFragment.this);
        btnLanjut.setOnClickListener(TunaiFragment.this);


        return v;
    }

    @SuppressLint("SetTextI18n")
    public void display(){
        DecimalFormat decim = new DecimalFormat("#,###");
        if (curr.isEmpty()) {
            etUangDiterima.setText("Rp " + 0);
        } else {
            etUangDiterima.setText("Rp " + decim.format(Double.valueOf(curr)));
        }
    }
    public void clear(){
        curr = "";
    }
    public void delete(){
        if (!curr.isEmpty()){
            if (curr.charAt(curr.length() - 1) == ' '){
                curr = curr.substring(0, curr.length()-3);
            }
            else {
                curr = curr.substring(0, curr.length()-1);
            }
        } else {
            curr = "0.0";
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent someIntent = new Intent(INTENT_TUNAI_PAS);
        PembayaranActivity pembayaranActivity = (PembayaranActivity) getActivity();
        switch (view.getId()){
            case R.id.btn_uang_pas:
                //Function uang pas
                int count = (pembayaranActivity.getTotal().replace("Rp","")).length() - (pembayaranActivity.getTotal().replace("Rp","")).replace(".", "").length();
                if (count > 1) {
                    curr = (pembayaranActivity.getTotal().replace("Rp","")).replace(".","");
                } else {
                    curr = (pembayaranActivity.getTotal().replace("Rp","")).replace(",","");
                }
                display();
                someIntent.putExtra("uang_diterima",pembayaranActivity.getTotal());
                break;
            case R.id.btn_0:
                curr = curr + "0";
                display();
                someIntent.putExtra("uang_diterima",curr);
                break;
            case R.id.btn_1:
                curr = curr + "1";
                display();
                someIntent.putExtra("uang_diterima",curr);
                break;
            case R.id.btn_2:
                curr = curr + "2";
                display();
                someIntent.putExtra("uang_diterima",curr);
                break;
            case R.id.btn_3:
                curr = curr + "3";
                display();
                someIntent.putExtra("uang_diterima",curr);
                break;
            case R.id.btn_4:
                curr = curr + "4";
                display();
                someIntent.putExtra("uang_diterima",curr);
                break;
            case R.id.btn_5:
                curr = curr + "5";
                display();
                someIntent.putExtra("uang_diterima",curr);
                break;
            case R.id.btn_6:
                curr = curr + "6";
                display();
                someIntent.putExtra("uang_diterima",curr);
                break;
            case R.id.btn_7:
                curr = curr + "7";
                display();
                someIntent.putExtra("uang_diterima",curr);
                break;
            case R.id.btn_8:
                curr = curr + "8";
                display();
                someIntent.putExtra("uang_diterima",curr);
                break;
            case R.id.btn_9:
                curr = curr + "9";
                display();
                someIntent.putExtra("uang_diterima",curr);
                break;
            case R.id.btn_00:
                curr = curr + "00";
                display();
                someIntent.putExtra("uang_diterima",curr);
                break;
            case R.id.btn_clear:
                clear();
                display();
                break;
            case R.id.btn_delete:
                delete();
                display();
                someIntent.putExtra("uang_diterima",curr);
                break;
            case R.id.btn_lanjut:
                if (curr.isEmpty()) {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Uang diterima 0")
                            .show();
                } else {
                    penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);
                    Call<List<Penjualan>> call = penjualanEndpoint.savePenjualan(auth_token, pembayaranActivity.konfirmasiPenjualan("Tunai","-","-"));
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
                                    System.out.println((Serializable) response.body().get(i).getDetailPesananList());
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
                                lanjut.putExtra("metode_bayar", "Tunai");
                                lanjut.putExtra("bank_name", "-");
                                lanjut.putExtra("no_rek", "-");

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
                    display();
                    someIntent.putExtra("uang_diterima",curr);
                }

                break;

        }
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).sendBroadcast(someIntent);
    }
}