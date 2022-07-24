package com.example.posrudyproject.ui.rekapKas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.RekapKasEndpoint;
import com.example.posrudyproject.ui.beranda.fragment.BerandaFragment;
import com.example.posrudyproject.ui.main.MainActivity;
import com.example.posrudyproject.ui.pelanggan.activity.PelangganActivity;
import com.example.posrudyproject.ui.pelanggan.activity.TambahPelangganActivity;
import com.example.posrudyproject.ui.penjual.activity.PenjualActivity;
import com.example.posrudyproject.ui.penjual.fragment.BotSheetTokoTujuanFragment;
import com.example.posrudyproject.ui.penjualan.activity.KategoriActivity;
import com.example.posrudyproject.ui.penjualan.activity.TransaksiSuksesActivity;
import com.example.posrudyproject.ui.penyimpanan.activity.RiwayatPenyimpananActivity;
import com.example.posrudyproject.ui.penyimpanan.adapter.PagerMenuAdapter;
import com.example.posrudyproject.ui.penyimpanan.fragment.BarangKeluarFragment;
import com.example.posrudyproject.ui.penyimpanan.fragment.BarangMasukFragment;
import com.example.posrudyproject.ui.penyimpanan.fragment.BarangPindahFragment;
import com.example.posrudyproject.ui.penyimpanan.model.BarangKeluarItem;
import com.example.posrudyproject.ui.penyimpanan.model.BarangMasukItem;
import com.example.posrudyproject.ui.penyimpanan.model.DokumenBarangPindahItem;
import com.example.posrudyproject.ui.rekapKas.adapter.DetailKasAdapter;
import com.example.posrudyproject.ui.rekapKas.fragment.RekapKasFragment;
import com.example.posrudyproject.ui.rekapKas.model.KasKeluarItem;
import com.example.posrudyproject.ui.rekapKas.model.KasMasukItem;
import com.example.posrudyproject.ui.rekapKas.model.TutupKasirItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.internal.LinkedTreeMap;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKasActivity extends AppCompatActivity{

    MaterialToolbar mToolbar;
    RecyclerView rvDetailKas;
    ConstraintLayout layoutEmpty;
    MaterialButton btnTutupKasir;
    DetailKasAdapter adapter;
    ArrayList<Object> listKas = new ArrayList<>();
    RekapKasEndpoint rekapKasEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kas);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Kas List
        loadData();

        btnTutupKasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTutupKasir();
            }
        });

    }

    void loadData() {
        rekapKasEndpoint = ApiClient.getClient().create(RekapKasEndpoint.class);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        int id_store = preferences.getInt("id_store", 0);
        String auth_token = ("Bearer ").concat(token);
        Call<Map> call = rekapKasEndpoint.allKas(auth_token, id_store);
        SweetAlertDialog pDialog = new SweetAlertDialog(DetailKasActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(DetailKasActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");
                    List<KasMasukItem> kasMasukItems = (List<KasMasukItem>)inner.get("val A");
                    List<KasKeluarItem> kasKeluarItems =  (List<KasKeluarItem>)inner.get("val B");
                    for (int j = 0; j < (kasMasukItems.size() + kasKeluarItems.size()); j++){
                        KasMasukItem resMasuk = new KasMasukItem();
                        KasKeluarItem resKeluar = new KasKeluarItem();

                        if (j < kasMasukItems.size()){
                            Object itemMasuk = kasMasukItems.get(j);
                            LinkedTreeMap<Object,Object> tM = (LinkedTreeMap) itemMasuk;
                            resMasuk.setNominalKasMasuk(tM.get("nominalKasMasuk").toString());
                            resMasuk.setWaktuKasMasuk(tM.get("waktuMasuk").toString());
                            resMasuk.setPenjualKasMasuk(tM.get("nama_karyawan").toString());
                            resMasuk.setCatatanKasMasuk(tM.get("catatan").toString());
                            listKas.add(resMasuk);
                        }

                        if (j < kasKeluarItems.size()) {
                            Object itemKeluar = kasKeluarItems.get(j);
                            LinkedTreeMap<Object,Object> tK = (LinkedTreeMap) itemKeluar;
                            resKeluar.setNominalKasKeluar(tK.get("nominalKasKeluar").toString());
                            resKeluar.setWaktuKasKeluar(tK.get("waktuKeluar").toString());
                            resKeluar.setPenjualKasKeluar(tK.get("nama_karyawan").toString());
                            resKeluar.setCatatanKasKeluar(tK.get("catatan").toString());
                            listKas.add(resKeluar);
                        }
                    }

                    //Setup adapter
                    adapter = new DetailKasAdapter(listKas);
                    rvDetailKas.setLayoutManager(new LinearLayoutManager(DetailKasActivity.this));
                    rvDetailKas.setAdapter(adapter);
                    rvDetailKas.setHasFixedSize(true);

                    //Jika ada list item ilustrasi hilang
                    if (adapter.getItemCount() > 0){
                        layoutEmpty.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(DetailKasActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar_detail_kas);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        String auth_token = ("Bearer ").concat(token);
        Integer id_toko = preferences.getInt("id_store",0);
        String nama_toko = preferences.getString("lokasi_store","");
        Integer id_pengguna = preferences.getInt("id_pengguna",0);
        String nama_pengguna = preferences.getString("nama_pengguna","");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RekapKasFragment rekapKasFragment = new RekapKasFragment();
                Bundle bundle = new Bundle();
                bundle.putString("auth_token",auth_token);
                bundle.putInt("id_toko", id_toko);
                bundle.putString("nama_toko", nama_toko);
                bundle.putInt("id_pengguna", id_pengguna);
                bundle.putString("nama_pengguna", nama_pengguna);
                rekapKasFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.app_bar_layout, rekapKasFragment).commit();
                Intent intent = new Intent(DetailKasActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initComponent() {

        rvDetailKas = findViewById(R.id.rv_detail_kas);
        layoutEmpty = findViewById(R.id.layout_ilustrasi_empty_detail_detail_kas);
        btnTutupKasir = findViewById(R.id.btn_tutup_kasir);
    }

    private void dialogTutupKasir() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_tutup_kasir, null);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        String auth_token = ("Bearer ").concat(token);
        Integer id_toko = preferences.getInt("id_store",0);
        String nama_toko = preferences.getString("lokasi_store","");
        Integer id_pengguna = preferences.getInt("id_pengguna",0);
        String nama_pengguna = preferences.getString("nama_pengguna","");
        //init view
        final TextInputEditText etTunai = mView.findViewById(R.id.et_tunai_diterima);
        final TextInputEditText etNonTunai = mView.findViewById(R.id.et_non_tunai_diterima);
        MaterialButton btnTutupKasir = mView.findViewById(R.id.btn_tutup_kasir_dialog);
        MaterialButton btnCancel = mView.findViewById(R.id.btn_cancel_dialog);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_rounded_white);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        btnTutupKasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailKasActivity.this, "Tutup Kasir", Toast.LENGTH_SHORT).show();
                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MM yyyy | HH:mm");
                String dateString = sdf.format(date);
                TutupKasirItem item = new TutupKasirItem();
                item.setUangMasukTunai(Double.valueOf(etTunai.getText().toString()));
                item.setUangMasukNonTunai(Double.valueOf(etNonTunai.getText().toString()));
                item.setId_store(id_toko);
                item.setLokasi_store(nama_toko);
                item.setId_karyawan(id_pengguna);
                item.setNama_karyawan(nama_pengguna);
                item.setCatatan("Pemasukan " + nama_toko + "Pada " + dateString + ". Di rekap oleh " + nama_pengguna);
                SweetAlertDialog pDialog = new SweetAlertDialog(DetailKasActivity.this, SweetAlertDialog.WARNING_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();
                Call<Map> call = rekapKasEndpoint.tutupKasir(auth_token, item);
                call.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        pDialog.dismiss();
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DetailKasActivity.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText(response.body().get("message").toString());
                        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Intent intent = new Intent(DetailKasActivity.this, DetailKasActivity.class);
                                startActivity(intent);
                            }
                        });
                        sweetAlertDialog.show();
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(DetailKasActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        Intent intent = new Intent(DetailKasActivity.this, DetailKasActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                });

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

}