package com.example.posrudyproject.ui.penyimpanan.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PelangganEndpoint;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.main.MainActivity;
import com.example.posrudyproject.ui.pelanggan.activity.PelangganActivity;
import com.example.posrudyproject.ui.pelanggan.activity.TambahPelangganActivity;
import com.example.posrudyproject.ui.pelanggan.adapter.PelangganAdapter;
import com.example.posrudyproject.ui.pelanggan.model.Pelanggan;
import com.example.posrudyproject.ui.penjual.activity.PenjualActivity;
import com.example.posrudyproject.ui.penyimpanan.adapter.KategoriPenyimpananAdapter;
import com.example.posrudyproject.ui.penyimpanan.adapter.ProdukTersediaAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.KategoriPenyimpananItem;
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenyimpananActivity extends AppCompatActivity implements OnItemClickListener, View.OnClickListener{

    MaterialToolbar mToolbar;
    MaterialButton btnPemindahanBarang;
    RecyclerView rvKategoriPenyimpanan, rvProdukTersedia;
    AppCompatTextView title;
    KategoriPenyimpananAdapter katPenyimpananAdapter;
    List<KategoriPenyimpananItem> kategoriPenyimpananItems;
    ProdukTersediaAdapter proTersediaAdapter;
    List<ProdukTersediaItem> produkTersediaItems;
    PenyimpananEndpoint penyimpananEndpoint;
    Double total_stock = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyimpanan);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        int id_store = preferences.getInt("id_store", 0);
        String auth_token = ("Bearer ").concat(token);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);
        rvKategoriPenyimpanan = findViewById(R.id.rv_kategori_penyimpanan);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        rvKategoriPenyimpanan.setLayoutManager(manager);
        rvKategoriPenyimpanan.setHasFixedSize(true);

        //INIT VIEW
        initComponent();

        initToolbar();

        //Kategori Penyimpanan
        Call<Map> call = penyimpananEndpoint.totalQtyMasukAndKeluar(auth_token,id_store);
        Call<List<ProdukTersediaItem>> call_stock = penyimpananEndpoint.stockAvailPerStore(auth_token,id_store);
        SweetAlertDialog pDialog = new SweetAlertDialog(PenyimpananActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(PenyimpananActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                }else{
                    pDialog.dismiss();
                    Map<String, String> inner = (Map<String, String>)response.body().get("result");
                    kategoriPenyimpananItems = new ArrayList<>();
                    kategoriPenyimpananItems.add(new KategoriPenyimpananItem(0, inner.get("val A"),"Barang Masuk"));
                    kategoriPenyimpananItems.add(new KategoriPenyimpananItem(1, inner.get("val B"),"Barang Keluar"));
                    kategoriPenyimpananItems.add(new KategoriPenyimpananItem(2, inner.get("val C"),"Barang Pindah"));

                    KategoriPenyimpananAdapter adapter = new KategoriPenyimpananAdapter(kategoriPenyimpananItems, PenyimpananActivity.this);
                    rvKategoriPenyimpanan.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                new SweetAlertDialog(PenyimpananActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        //Produk Tersedia
        call_stock.enqueue(new Callback<List<ProdukTersediaItem>>() {
            @Override
            public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(PenyimpananActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                }else{
                    pDialog.dismiss();
                    produkTersediaItems = new ArrayList<>();
                    for (int i=0; i<response.body().size(); i++){
                        produkTersediaItems.add(new ProdukTersediaItem(
                                response.body().get(i).getFoto_barang(),
                                response.body().get(i).getTipeBarang(),
                                response.body().get(i).getSkuCode(),
                                response.body().get(i).getArtikelBarang(),
                                response.body().get(i).getNamaBarang(),
                                response.body().get(i).getHargaBarang(),
                                response.body().get(i).getJumlahStok()
                        ));

                        total_stock += Double.valueOf(response.body().get(i).getJumlahStok());
                    }
                    ProdukTersediaAdapter adapter = new ProdukTersediaAdapter(produkTersediaItems, PenyimpananActivity.this);
                    rvProdukTersedia.setAdapter(adapter);
                    title.setText("Produk yang tersedia " + String.valueOf(total_stock).replace(".0", ""));
                }
            }

            @Override
            public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(PenyimpananActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        //SET LISTENER
        btnPemindahanBarang.setOnClickListener(this);
        rvProdukTersedia.setLayoutManager(new LinearLayoutManager(this));
        rvProdukTersedia.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PenyimpananActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_penyimpanan);
        btnPemindahanBarang = findViewById(R.id.btn_pemindahan_barang);
        title = findViewById(R.id.title_produk_tersedia);
        rvProdukTersedia = findViewById(R.id.rv_produk_tersedia);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(this, "Pilih " + kategoriPenyimpananItems.get(position).getKatBarangPenyimpanan(), Toast.LENGTH_SHORT).show();
        Intent pilihKategori = new Intent(this, RiwayatPenyimpananActivity.class);
        startActivity(pilihKategori);
    }

    @Override
    public void onClick(View view) {
        Intent pemindahanBarang = new Intent(this, PemindahanBarangActivity.class);
        startActivity(pemindahanBarang);
    }

}