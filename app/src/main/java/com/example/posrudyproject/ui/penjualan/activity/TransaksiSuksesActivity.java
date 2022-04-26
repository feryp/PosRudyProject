package com.example.posrudyproject.ui.penjualan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PelangganEndpoint;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;

import com.example.posrudyproject.ui.pembayaran.model.DetailPesanan;
import com.example.posrudyproject.ui.penjualan.adapter.TransaksiSuksesAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TransaksiSuksesActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar mToolbar;
    MaterialButton btnCetakStruk, btnBuatPesananBaru;
    AppCompatTextView tvNoInvoiceDetailPesanan, tvJumlahNominalTransaksi, tvKembalianTransaksiSukses, tvMetodePembayaranTransaksiSukses,
            tvPelangganTransaksiSukses, tvDiskonTransaksiSukses, tvPenjualTransaksiSukses, tvOngkirTransaksiSukses, tvPointTransaksiSukses;
    RecyclerView rvTransaksi;
    TransaksiSuksesAdapter adapter;
    List<KeranjangItem> keranjangItems;
    PelangganEndpoint pelangganEndpoint;
    String auth_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_sukses);
        keranjangItems = new ArrayList<>();
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        pelangganEndpoint = ApiClient.getClient().create(PelangganEndpoint.class);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        auth_token = ("Bearer ").concat(token);
        //INIT VIEW
        initComponent();

        initToolbar();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvJumlahNominalTransaksi.setText("Rp" + formatter.format(bundle.getDouble("total")));
            tvKembalianTransaksiSukses.setText("Rp" + formatter.format(bundle.getDouble("kembalian")));
            tvMetodePembayaranTransaksiSukses.setText(bundle.getString("metode_bayar"));
            tvNoInvoiceDetailPesanan.setText(bundle.getString("id_transaksi"));
            tvPelangganTransaksiSukses.setText(bundle.getString("namaPelanggan"));

            if (bundle.getDouble("diskon") < 100.0 ) {
                tvDiskonTransaksiSukses.setText(formatter.format(bundle.getDouble("diskon")) + "%");
            } else if (bundle.getDouble("diskon") >= 100.0 ) {
                tvDiskonTransaksiSukses.setText("Rp" + formatter.format(bundle.getDouble("diskon")));
            } else {
                tvDiskonTransaksiSukses.setText("-");
            }

            tvPenjualTransaksiSukses.setText(bundle.getString("namaPenjual"));
            tvOngkirTransaksiSukses.setText("Rp" + formatter.format(bundle.getDouble("ongkir")));

            //Transaksi List
            if ((List<DetailPesanan>) bundle.getSerializable("items") != null) {
                for (int i = 0; i < ((List<DetailPesanan>) bundle.getSerializable("items")).size(); i++) {
                    keranjangItems.add(new KeranjangItem(
                            "",
                            ((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getType_name(),
                            ((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getSku_code(),
                            ((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getArtikel(),
                            ((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getNama_barang(),
                            "Rp" + formatter.format(((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getHarga()),
                            formatter.format(((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getKuantitas()),
                            "Rp" + formatter.format(((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getHarga() * ((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getKuantitas()),
                            formatter.format(((List<DetailPesanan>) bundle.getSerializable("items")).get(i).getKuantitas())
                    ));
                }
            }

            Call<Double> callPoint = pelangganEndpoint.totalPoin(auth_token,bundle.getString("namaPelanggan"),bundle.getString("noHpPelanggan"));
            callPoint.enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    if (!response.isSuccessful()){
                        tvPointTransaksiSukses.setText("0");
                    } else {
                        tvPointTransaksiSukses.setText(response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<Double> call, Throwable t) {
                    tvPointTransaksiSukses.setText("0");
                }
            });
        }
        //SET LISTENER
        btnCetakStruk.setOnClickListener(this);
        btnBuatPesananBaru.setOnClickListener(this);

        //Setup adapter
        adapter = new TransaksiSuksesAdapter(keranjangItems, this);
        rvTransaksi.setLayoutManager(new LinearLayoutManager(this));
        rvTransaksi.setAdapter(adapter);
        rvTransaksi.setHasFixedSize(true);
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_transaksi_sukses);
        btnCetakStruk = findViewById(R.id.btn_cetak_struk);
        btnBuatPesananBaru = findViewById(R.id.btn_buat_pesanan_baru);
        rvTransaksi = findViewById(R.id.rv_item_pesanan_transaksi_selesai);
        tvJumlahNominalTransaksi = findViewById(R.id.tv_jumlah_nominal_transaksi);
        tvKembalianTransaksiSukses = findViewById(R.id.tv_kembalian_transaksi_sukses);
        tvMetodePembayaranTransaksiSukses = findViewById(R.id.tv_metode_pembayaran_transaksi_sukses);
        tvPelangganTransaksiSukses = findViewById(R.id.tv_pelanggan_transaksi_sukses);
        tvDiskonTransaksiSukses = findViewById(R.id.tv_diskon_transaksi_sukses);
        tvPenjualTransaksiSukses = findViewById(R.id.tv_penjual_transaksi_sukses);
        tvOngkirTransaksiSukses = findViewById(R.id.tv_ongkir_transaksi_sukses);
        tvNoInvoiceDetailPesanan = findViewById(R.id.tv_no_invoice_detail_pesanan);
        tvPointTransaksiSukses = findViewById(R.id.tv_point_transaksi_sukses);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cetak_struk:

                Intent cetakStruk = new Intent(this, StrukPenjualanActivity.class);
                startActivity(cetakStruk);
                break;

            case R.id.btn_buat_pesanan_baru:
                Intent buatPesananBaru = new Intent(this, KategoriActivity.class);
                startActivity(buatPesananBaru);
                break;
        }
    }

}