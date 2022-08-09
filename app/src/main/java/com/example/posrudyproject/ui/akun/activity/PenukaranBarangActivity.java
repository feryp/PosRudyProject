package com.example.posrudyproject.ui.akun.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenukaranBaranEndpoint;
import com.example.posrudyproject.ui.akun.adapter.PenukaranBarangAdapter;
import com.example.posrudyproject.ui.akun.model.BarangKembali;
import com.example.posrudyproject.ui.akun.model.PenukaranBarangItem;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenukaranBarangActivity extends AppCompatActivity implements View.OnClickListener{

    MaterialToolbar mToolbar;
    RecyclerView rvPenukaranBarang;
    TextInputEditText etAlasanPenukaran;
    AppCompatTextView tvTotalHarga;
    MaterialButton btnTukar;

    List<PenukaranBarangItem> penukaranBarangItems;
    List<BarangKembali> barangKembali;
    PenukaranBarangAdapter adapter;
    Double total = 0.0;
    Integer id_store;
    String auth_token,id_transaksi;
    PenukaranBaranEndpoint penukaranBaranEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penukaran_barang);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        SharedPreferences preferencesItem = getSharedPreferences("penukaranBarang", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesItem.edit();

        String token = preferences.getString("token","");
        id_store = preferences.getInt("id_store", 0);
        auth_token = ("Bearer ").concat(token);
        penukaranBaranEndpoint = ApiClient.getClient().create(PenukaranBaranEndpoint.class);
        //INIT VIEW
        initComponent();

        initToolbar();
        barangKembali = new ArrayList<>();
        id_transaksi = "";
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        //Penukaran Barang List
        penukaranBarangItems = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id_transaksi = bundle.getString("id_transaksi");
            for (int i = 0; i< ((List<KeranjangItem>) bundle.getSerializable("items")).size(); i++){
                penukaranBarangItems.add(new PenukaranBarangItem(
                        false,
                        ((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getTipeBarang(),
                        ((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getSkuCode(),
                        ((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getArtikelBarang(),
                        ((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getNamaBarang(),
                        ((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getHargaBarang(),
                        ((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getJumlahBarang()
                ));
                total += (Double.valueOf(((((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getHargaBarang().replace("Rp","")).replace(".","")).replace(".0","")) * Double.valueOf(((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getJumlahBarang()));
            }
            tvTotalHarga.setText("Rp" + formatter.format(total));
            editor.putString("totalHarga", String.valueOf(total));
            editor.apply();
        }

        //Setup Adapter Produk Tersedia
        adapter = new PenukaranBarangAdapter(penukaranBarangItems, this);
        rvPenukaranBarang.setLayoutManager(new LinearLayoutManager(this));
        rvPenukaranBarang.setAdapter(adapter);
        rvPenukaranBarang.setHasFixedSize(true);
        rvPenukaranBarang.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                tvTotalHarga.setText("Rp" + formatter.format(Double.valueOf(preferencesItem.getString("totalHarga", "0.00"))));

            }
        });
        tvTotalHarga.setText("Rp" + formatter.format(Double.valueOf(preferencesItem.getString("totalHarga", "0.00"))));
        //SET LISTENER
        btnTukar.setOnClickListener(this);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_penukaran_barang);
        rvPenukaranBarang = findViewById(R.id.rv_list_penukaran_barang);
        etAlasanPenukaran = findViewById(R.id.et_alasan_penukaran);
        tvTotalHarga = findViewById(R.id.tv_total_harga_penukaran_barang);
        btnTukar = findViewById(R.id.btn_tukar_barang);
    }

    @Override
    public void onClick(View view) {
        if (tvTotalHarga.getText().toString().equals("Rp0")) {
            Toast.makeText(this, "Tidak ada barang yang ditukar.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tukar Barang", Toast.LENGTH_SHORT).show();
            SharedPreferences preferencesItem = getSharedPreferences("penukaranBarang", Context.MODE_PRIVATE);
            for (int i=0; i<penukaranBarangItems.size(); i++) {
                barangKembali.add(new BarangKembali(
                        id_transaksi,
                        id_store,
                        penukaranBarangItems.get(i).getSku_code(),
                        penukaranBarangItems.get(i).getArtikelBarang(),
                        Double.valueOf(preferencesItem.getString(penukaranBarangItems.get(i).getArtikelBarang(), "0")),
                        Double.valueOf(((penukaranBarangItems.get(i).getHargaBarang().replace("Rp","")).replace(".","")).replace(".0","")),
                        String.valueOf(etAlasanPenukaran.getText())
                ));
            }

            Call<Map> call = penukaranBaranEndpoint.savePenukaran(auth_token,barangKembali);
            SweetAlertDialog pDialog = new SweetAlertDialog(PenukaranBarangActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            call.enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    if (!response.isSuccessful()){
                        pDialog.dismiss();
                        new SweetAlertDialog(PenukaranBarangActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(String.valueOf(response.code()))
                                .setContentText(response.message())
                                .show();
                    } else {
                        pDialog.dismiss();
                        Intent back = new Intent(PenukaranBarangActivity.this, RiwayatTransaksiActivity.class);
                        startActivity(back);
                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    pDialog.dismiss();
                    new SweetAlertDialog(PenukaranBarangActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(t.getMessage())
                            .show();
                }
            });
        }

    }
}