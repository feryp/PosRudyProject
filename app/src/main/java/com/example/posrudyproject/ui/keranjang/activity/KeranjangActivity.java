package com.example.posrudyproject.ui.keranjang.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.retrofit.PesananTungguEndpoint;
import com.example.posrudyproject.ui.barcode.ScannerActivity;
import com.example.posrudyproject.ui.diskon.fragment.BotSheetDiskonFragment;
import com.example.posrudyproject.ui.keranjang.adapter.KeranjangAdapter;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.pelanggan.activity.PelangganActivity;
import com.example.posrudyproject.ui.pembayaran.activity.PembayaranActivity;
import com.example.posrudyproject.ui.penjual.activity.PenjualActivity;
import com.example.posrudyproject.ui.penjualan.activity.KategoriActivity;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
import com.example.posrudyproject.ui.penyimpanan.activity.PenyimpananActivity;
import com.example.posrudyproject.ui.penyimpanan.adapter.ProdukTersediaAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.example.posrudyproject.ui.pesananTunggu.activity.PesananTungguActivity;
import com.example.posrudyproject.ui.pesananTunggu.model.BarangPesananTungguItem;
import com.example.posrudyproject.ui.pesananTunggu.model.PesananTungguItem;
import com.example.posrudyproject.ui.produk.activity.CustomBarangActivity;
import com.example.posrudyproject.ui.ubahHarga.activity.UbahHargaActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatImageButton btnBarcode;
    AppCompatTextView tvSubtotalKeranjang,tvTotalHargaKeranjang;
    MaterialToolbar mToolbar;
    MaterialButton btnPotonganHarga, btnSimpanPesanan, btnAddPelanggan, btnAddPenjual,
            btnAddDiskon, btnAddOngkir, btnCustom, btnKonfirmasi,tvPelanggan,tvPenjual,tvDiskon,tvOngkir;
    RecyclerView rvKeranjang;
    KeranjangAdapter adapter;
    List<KeranjangItem> keranjangItems = new ArrayList<>();
    ConstraintLayout layoutKeranjang, layoutEmpty, layoutPenjual;
    PesananTungguEndpoint pesananTungguEndpoint;
    List<PesananTungguItem> pesananTungguItems;
    PesananTungguItem pesananTungguItem;
    String noHpPelanggan,namaPelanggan,namaPenjual,ongkir,ekspedisi,diskonRupiah,diskonPersen,diskon_remark;
    Integer idPenjual,id_store,id_karyawan;
    Double subtotal = 0.00;
    String auth_token,lokasi_store,nama_karyawan,id_kategori;
    List<BarangPesananTungguItem> barangPesananTungguItems;
    public static final String INTENT_DISKON = "INTENT_DISKON";
    private static String code;
    PenyimpananEndpoint penyimpananEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        SharedPreferences preferencesCart = getSharedPreferences("keranjang", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesCart.edit();
        String token = preferences.getString("token","");
        auth_token = ("Bearer ").concat(token);
        id_store = preferences.getInt("id_store", 0);
        lokasi_store = preferences.getString("lokasi_store","");
        id_karyawan = preferences.getInt("id_pengguna", 0);
        nama_karyawan = preferences.getString("nama_pengguna","");
        pesananTungguEndpoint = ApiClient.getClient().create(PesananTungguEndpoint.class);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);
        barangPesananTungguItems = new ArrayList<>();
        pesananTungguItems = new ArrayList<>();
        //INIT VIEW
        initComponent();
        initToolbar();

        //SET LISTENER
        btnBarcode.setOnClickListener(this);
        btnPotonganHarga.setOnClickListener(this);
        btnSimpanPesanan.setOnClickListener(this);
        btnAddPelanggan.setOnClickListener(this);
        btnAddPenjual.setOnClickListener(this);
        btnAddDiskon.setOnClickListener(this);
        btnAddOngkir.setOnClickListener(this);
        btnCustom.setOnClickListener(this);
        btnKonfirmasi.setOnClickListener(this);

        //SETTING BUTTON
        btnPotonganHarga.setPaintFlags(btnPotonganHarga.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnSimpanPesanan.setPaintFlags(btnSimpanPesanan.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //Keranjang List
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            keranjangItems = new ArrayList<>();
            if ((List<KeranjangItem>) extras.getSerializable("itemForBuyAfterPotong") != null) {
                keranjangItems = ((List<KeranjangItem>) extras.getSerializable("itemForBuyAfterPotong"));
                for (int i=0; i<keranjangItems.size(); i++) {
                    if (keranjangItems.get(i).getHarga_baru_remark() == "") {
                        subtotal += (Double.valueOf(keranjangItems.get(i).getHarga_baru()) * Double.valueOf(keranjangItems.get(i).getKuantitasBarang()));
                        editor.putString("subtotal", String.valueOf(subtotal));
                        editor.apply();
                    } else if (keranjangItems.get(i).getHarga_baru_remark() != "") {
                        subtotal += (Double.valueOf(keranjangItems.get(i).getHarga_baru()) * Double.valueOf(keranjangItems.get(i).getKuantitasBarang()));
                        editor.putString("subtotal", String.valueOf(subtotal));
                        editor.apply();
                    }
                }

                adapter = new KeranjangAdapter(keranjangItems,KeranjangActivity.this);
                rvKeranjang.setAdapter(adapter);
                rvKeranjang.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        DecimalFormat formatter = new DecimalFormat("#,###.##");
                        tvSubtotalKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0"))));
                        if (ongkir != null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) + Double.parseDouble(ongkir)));
                        }
                        if (diskonPersen != null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) * ((100 - Double.parseDouble(diskonPersen)) / 100)));
                        }
                        if (diskonRupiah != null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) - Double.parseDouble(diskonRupiah)));
                        }

                        if (ongkir != null && diskonPersen != null) {
                            tvTotalHargaKeranjang.setText(formatter.format((Double.valueOf(preferencesCart.getString("subtotal", "0")) * ((100 - Double.parseDouble(diskonPersen)) / 100)) + Double.valueOf(ongkir)));
                        }
                        if (ongkir != null && diskonRupiah != null) {
                            tvTotalHargaKeranjang.setText(formatter.format((Double.valueOf(preferencesCart.getString("subtotal", "0")) - Double.valueOf(diskonRupiah)) + Double.valueOf(ongkir)));
                        }
                        if (ongkir == null && diskonRupiah == null && diskonPersen == null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0"))));
                        }
                    }
                });

                rvKeranjang.setLayoutManager(new LinearLayoutManager(KeranjangActivity.this));
                rvKeranjang.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutKeranjang.setVisibility(View.VISIBLE);
                }
            } else if ((List<KeranjangItem>) extras.getSerializable("itemForBuyAddPelanggan") != null) {
                keranjangItems = ((List<KeranjangItem>) extras.getSerializable("itemForBuyAddPelanggan"));
                for (int i=0; i<keranjangItems.size(); i++) {
                    subtotal += Double.valueOf(keranjangItems.get(i).getTotalHargaBarang());
                    editor.putString("subtotal", String.valueOf(subtotal));
                    editor.apply();
                }

                adapter = new KeranjangAdapter(keranjangItems,KeranjangActivity.this);
                rvKeranjang.setAdapter(adapter);
                rvKeranjang.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        DecimalFormat formatter = new DecimalFormat("#,###.##");
                        tvSubtotalKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0"))));
                        if (ongkir != null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) + Double.parseDouble(ongkir)));
                        }
                        if (diskonPersen != null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) * ((100 - Double.parseDouble(diskonPersen)) / 100)));
                        }
                        if (diskonRupiah != null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) - Double.parseDouble(diskonRupiah)));
                        }

                        if (ongkir != null && diskonPersen != null) {
                            tvTotalHargaKeranjang.setText(formatter.format((Double.valueOf(preferencesCart.getString("subtotal", "0")) * ((100 - Double.parseDouble(diskonPersen)) / 100)) + Double.valueOf(ongkir)));
                        }
                        if (ongkir != null && diskonRupiah != null) {
                            tvTotalHargaKeranjang.setText(formatter.format((Double.valueOf(preferencesCart.getString("subtotal", "0")) - Double.valueOf(diskonRupiah)) + Double.valueOf(ongkir)));
                        }
                        if (ongkir == null && diskonRupiah == null && diskonPersen == null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0"))));
                        }
                    }
                });

                rvKeranjang.setLayoutManager(new LinearLayoutManager(KeranjangActivity.this));
                rvKeranjang.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutKeranjang.setVisibility(View.VISIBLE);
                }
                namaPelanggan = extras.getString("namaPelanggan");
                noHpPelanggan = extras.getString("noHp");

                tvPelanggan.setVisibility(View.VISIBLE);
                btnAddPelanggan.setVisibility(View.GONE);
                tvPelanggan.setText(extras.getString("namaPelanggan"));

                //part penjual
                if (extras.getString("namaPenjualFromPelanggan") != null) {
                    namaPenjual = extras.getString("namaPenjualFromPelanggan");
                    idPenjual = extras.getInt("idPenjualFromPelanggan");

                    tvPenjual.setVisibility(View.VISIBLE);
                    btnAddPenjual.setVisibility(View.GONE);
                    tvPenjual.setText(extras.getString("namaPenjualFromPelanggan"));
                }
                // end part penjual

            } else if ((List<KeranjangItem>) extras.getSerializable("itemForBuyAddPenjual") != null) {
                keranjangItems = ((List<KeranjangItem>) extras.getSerializable("itemForBuyAddPenjual"));
                for (int i=0; i<keranjangItems.size(); i++) {
                    subtotal += Double.valueOf(keranjangItems.get(i).getTotalHargaBarang());
                    editor.putString("subtotal", String.valueOf(subtotal));
                    editor.apply();
                }

                adapter = new KeranjangAdapter(keranjangItems,KeranjangActivity.this);
                rvKeranjang.setAdapter(adapter);
                rvKeranjang.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        DecimalFormat formatter = new DecimalFormat("#,###.##");
                        tvSubtotalKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0"))));
                        if (ongkir != null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) + Double.parseDouble(ongkir)));
                        }
                        if (diskonPersen != null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) * ((100 - Double.parseDouble(diskonPersen)) / 100)));
                        }
                        if (diskonRupiah != null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) - Double.parseDouble(diskonRupiah)));
                        }

                        if (ongkir != null && diskonPersen != null) {
                            tvTotalHargaKeranjang.setText(formatter.format((Double.valueOf(preferencesCart.getString("subtotal", "0")) * ((100 - Double.parseDouble(diskonPersen)) / 100)) + Double.valueOf(ongkir)));
                        }
                        if (ongkir != null && diskonRupiah != null) {
                            tvTotalHargaKeranjang.setText(formatter.format((Double.valueOf(preferencesCart.getString("subtotal", "0")) - Double.valueOf(diskonRupiah)) + Double.valueOf(ongkir)));
                        }
                        if (ongkir == null && diskonRupiah == null && diskonPersen == null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0"))));
                        }
                    }
                });

                rvKeranjang.setLayoutManager(new LinearLayoutManager(KeranjangActivity.this));
                rvKeranjang.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutKeranjang.setVisibility(View.VISIBLE);
                }
                //part penjual
                namaPenjual = extras.getString("namaPenjual");
                idPenjual = extras.getInt("idPenjual");

                tvPenjual.setVisibility(View.VISIBLE);
                btnAddPenjual.setVisibility(View.GONE);
                tvPenjual.setText(extras.getString("namaPenjual"));
                // end part penjual

                //part pelanggan
                if (extras.getString("namaPelangganFromPenjual") != null) {
                    namaPelanggan = extras.getString("namaPelangganFromPenjual");
                    noHpPelanggan = extras.getString("noHpPelangganFromPenjual");

                    tvPelanggan.setVisibility(View.VISIBLE);
                    btnAddPelanggan.setVisibility(View.GONE);
                    tvPelanggan.setText(extras.getString("namaPelangganFromPenjual"));

                }
                //end part pelanggan
            } else if ((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue") != null) {
                for (int j=0; j < ((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).size(); j++) {
                    keranjangItems.add(new KeranjangItem(
                            ((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getImage(),
                            ((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getType_name(),
                            ((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getSku_code(),
                            ((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getArtikel(),
                            ((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getNamaBarang(),
                            String.valueOf(Double.valueOf(((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getHarga())),
                            String.valueOf(Double.valueOf(((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getHarga_baru() == null ? ((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getHarga() : ((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getHarga_baru())),
                            ((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getHarga_baru_remark(),
                            ((((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getKuantitasBarang()).toString()),
                            String.valueOf(Double.valueOf(((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getHarga()) * Double.valueOf(((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getKuantitasBarang())),
                            ((((List<BarangPesananTungguItem>) extras.getSerializable("itemFromQueue")).get(j).getKuantitasBarang()).toString())
                    ));
                }
                for (int i=0; i<keranjangItems.size(); i++) {
                    subtotal += Double.valueOf(keranjangItems.get(i).getTotalHargaBarang());
                    editor.putString("subtotal", String.valueOf(subtotal));
                    editor.apply();
                }

                adapter = new KeranjangAdapter(keranjangItems,KeranjangActivity.this);
                rvKeranjang.setAdapter(adapter);
                rvKeranjang.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        DecimalFormat formatter = new DecimalFormat("#,###.##");
                        tvSubtotalKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0"))));
                        if (ongkir != null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) + Double.parseDouble(ongkir)));
                        }
                        if (diskonPersen != null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) * ((100 - Double.parseDouble(diskonPersen)) / 100)));
                        }
                        if (diskonRupiah != null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) - Double.parseDouble(diskonRupiah)));
                        }

                        if (ongkir != null && diskonPersen != null) {
                            tvTotalHargaKeranjang.setText(formatter.format((Double.valueOf(preferencesCart.getString("subtotal", "0")) * ((100 - Double.parseDouble(diskonPersen)) / 100)) + Double.valueOf(ongkir)));
                        }
                        if (ongkir != null && diskonRupiah != null) {
                            tvTotalHargaKeranjang.setText(formatter.format((Double.valueOf(preferencesCart.getString("subtotal", "0")) - Double.valueOf(diskonRupiah)) + Double.valueOf(ongkir)));
                        }
                        if (ongkir == null && diskonRupiah == null && diskonPersen == null) {
                            tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0"))));
                        }
                    }
                });

                rvKeranjang.setLayoutManager(new LinearLayoutManager(KeranjangActivity.this));
                rvKeranjang.setHasFixedSize(true);

                //Jika ada list item ilustrasi hilang
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                    layoutKeranjang.setVisibility(View.VISIBLE);
                }
                namaPelanggan = extras.getString("namaPelanggan");
                noHpPelanggan = extras.getString("noHp");

                tvPelanggan.setVisibility(View.VISIBLE);
                btnAddPelanggan.setVisibility(View.GONE);
                tvPelanggan.setText(extras.getString("namaPelanggan"));

                tvPenjual.setVisibility(View.GONE);
                btnAddPenjual.setVisibility(View.VISIBLE);
            }
        } else {
            Call<List<ProdukTersediaItem>> call_stock = penyimpananEndpoint.stockAvailPerStore(auth_token,id_store);
            call_stock.enqueue(new Callback<List<ProdukTersediaItem>>() {
                @Override
                public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                    if (response.isSuccessful()){
                        for (int i=0; i<response.body().size(); i++){
                            if (Integer.valueOf(preferencesCart.getString(response.body().get(i).getArtikelBarang(), "0")) > 0) {
                                keranjangItems.add(new KeranjangItem(
                                        response.body().get(i).getFoto_barang(),
                                        response.body().get(i).getTipeBarang(),
                                        response.body().get(i).getSkuCode(),
                                        response.body().get(i).getArtikelBarang(),
                                        response.body().get(i).getNamaBarang(),
                                        String.valueOf(Double.valueOf(response.body().get(i).getHargaBarang())),
                                        String.valueOf(Double.valueOf(response.body().get(i).getHargaBarang())),
                                        "",
                                        preferencesCart.getString(response.body().get(i).getArtikelBarang(), "0"),
                                        String.valueOf(Double.valueOf(response.body().get(i).getHargaBarang()) * Double.valueOf(preferencesCart.getString(response.body().get(i).getArtikelBarang(), "0"))),
                                        preferencesCart.getString(response.body().get(i).getArtikelBarang(), "0")
                                ));
                            }
                        }
                        for (int i=0; i<keranjangItems.size(); i++) {
                            subtotal += Double.valueOf(keranjangItems.get(i).getTotalHargaBarang());
                            editor.putString("subtotal", String.valueOf(subtotal));
                            editor.apply();
                        }

                        adapter = new KeranjangAdapter(keranjangItems,KeranjangActivity.this);
                        rvKeranjang.setAdapter(adapter);
                        rvKeranjang.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                DecimalFormat formatter = new DecimalFormat("#,###.##");
                                tvSubtotalKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0"))));
                                if (ongkir != null) {
                                    tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) + Double.parseDouble(ongkir)));
                                }
                                if (diskonPersen != null) {
                                    tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) * ((100 - Double.parseDouble(diskonPersen)) / 100)));
                                }
                                if (diskonRupiah != null) {
                                    tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0")) - Double.parseDouble(diskonRupiah)));
                                }

                                if (ongkir != null && diskonPersen != null) {
                                    tvTotalHargaKeranjang.setText(formatter.format((Double.valueOf(preferencesCart.getString("subtotal", "0")) * ((100 - Double.parseDouble(diskonPersen)) / 100)) + Double.valueOf(ongkir)));
                                }
                                if (ongkir != null && diskonRupiah != null) {
                                    tvTotalHargaKeranjang.setText(formatter.format((Double.valueOf(preferencesCart.getString("subtotal", "0")) - Double.valueOf(diskonRupiah)) + Double.valueOf(ongkir)));
                                }
                                if (ongkir == null && diskonRupiah == null && diskonPersen == null) {
                                    tvTotalHargaKeranjang.setText(formatter.format(Double.valueOf(preferencesCart.getString("subtotal", "0"))));
                                }
                            }
                        });

                        rvKeranjang.setLayoutManager(new LinearLayoutManager(KeranjangActivity.this));
                        rvKeranjang.setHasFixedSize(true);

                        //Jika ada list item ilustrasi hilang
                        if (adapter.getItemCount() > 0){
                            layoutEmpty.setVisibility(View.GONE);
                            layoutKeranjang.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {

                }
            });
        }
        //Setup Adapter

        if (tvPelanggan.getVisibility() == View.VISIBLE) {
            tvPelanggan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent updatePelanggan = new Intent(getApplicationContext(), PelangganActivity.class);
                    updatePelanggan.putExtra("itemForBuy", (Serializable) keranjangItems);
                    updatePelanggan.putExtra("isPenjualan", 1);
                    if (namaPenjual != null) {
                        updatePelanggan.putExtra("namaPenjual", namaPenjual);
                        updatePelanggan.putExtra("idPenjual", idPenjual);
                    }
                    if (namaPelanggan != null) {
                        updatePelanggan.putExtra("namaPelanggan", namaPelanggan);
                        updatePelanggan.putExtra("noHpPelanggan", noHpPelanggan);
                    }
                    startActivity(updatePelanggan);
                }
            });
        }
        if (tvPenjual.getVisibility() == View.VISIBLE) {
            tvPenjual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent updatePenjual = new Intent(getApplicationContext(), PenjualActivity.class);
                    updatePenjual.putExtra("itemForBuy", (Serializable) keranjangItems);
                    updatePenjual.putExtra("isPenjualan", 1);
                    if (namaPelanggan != null) {
                        updatePenjual.putExtra("namaPelanggan", namaPelanggan);
                        updatePenjual.putExtra("noHpPelanggan", noHpPelanggan);
                    }
                    if (namaPenjual != null) {
                        updatePenjual.putExtra("namaPenjual", namaPenjual);
                        updatePenjual.putExtra("idPenjual", idPenjual);
                    }
                    startActivity(updatePenjual);
                }
            });
        }
        if (tvOngkir.getVisibility() == View.VISIBLE) {
            tvOngkir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogOngkir();
                }
            });
        }
    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent penjualan = new Intent(KeranjangActivity.this, KategoriActivity.class);
                startActivity(penjualan);
            }

        });
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_notes_order){
                Intent pesananTunggu = new Intent(this, PesananTungguActivity.class);
                startActivity(pesananTunggu);
                return true;
            }

            return false;
        });
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_keranjang);
        btnBarcode = findViewById(R.id.btn_barcode_keranjang);
        btnPotonganHarga = findViewById(R.id.btn_potongan_harga);
        btnSimpanPesanan = findViewById(R.id.btn_simpan_pesanan);
        btnAddPelanggan = findViewById(R.id.btn_add_pelanggan);
        btnAddPenjual = findViewById(R.id.btn_add_penjual);
        btnAddDiskon = findViewById(R.id.btn_add_diskon);
        btnAddOngkir = findViewById(R.id.btn_add_ongkir);
        btnCustom = findViewById(R.id.btn_custom_item_keranjang);
        btnKonfirmasi = findViewById(R.id.btn_konfirmasi_keranjang);
        rvKeranjang = findViewById(R.id.rv_keranjang);
        layoutKeranjang = findViewById(R.id.layout_keranjang);
        layoutEmpty = findViewById(R.id.layout_ilustrasi_empty_keranjang);

        layoutPenjual = findViewById(R.id.layout_pilih_penjual);
        tvPelanggan = findViewById(R.id.tv_pelanggan_filled_keranjang);
        tvPenjual = findViewById(R.id.tv_penjual_filled_keranjang);
        tvDiskon = findViewById(R.id.tv_diskon_filled_keranjang);
        tvOngkir = findViewById(R.id.tv_ongkir_filled_keranjang);
        tvSubtotalKeranjang = findViewById(R.id.tv_subtotal_keranjang);
        tvTotalHargaKeranjang = findViewById(R.id.tv_total_harga_keranjang);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_simpan_pesanan:
                dialogSimpanPesanan();
                break;
            case R.id.btn_potongan_harga:
                Intent potonganHarga = new Intent(this, UbahHargaActivity.class);
                potonganHarga.putExtra("itemForBuy", (Serializable) keranjangItems);
                startActivity(potonganHarga);
                break;
            case R.id.btn_add_pelanggan:
                Intent tambahPelanggan = new Intent(this, PelangganActivity.class);
                tambahPelanggan.putExtra("itemForBuy", (Serializable) keranjangItems);
                tambahPelanggan.putExtra("isPenjualan", 1);
                if (namaPenjual != null) {
                    tambahPelanggan.putExtra("namaPenjual", namaPenjual);
                    tambahPelanggan.putExtra("idPenjual", idPenjual);
                }
                if (namaPelanggan != null) {
                    tambahPelanggan.putExtra("namaPelanggan", namaPelanggan);
                    tambahPelanggan.putExtra("noHpPelanggan", noHpPelanggan);
                }
                if (ongkir != null) {
                    tambahPelanggan.putExtra("ongkir", ongkir);
                    tambahPelanggan.putExtra("ekspedisi", ekspedisi);
                }
                if (diskonPersen != null) {
                    tambahPelanggan.putExtra("diskonPersen", diskonPersen);
                    tambahPelanggan.putExtra("diskon_remark", diskon_remark);
                }
                if (diskonRupiah != null) {
                    tambahPelanggan.putExtra("diskonRupiah", diskonRupiah);
                    tambahPelanggan.putExtra("diskon_remark", diskon_remark);
                }
                startActivity(tambahPelanggan);
                break;

            case R.id.btn_add_penjual:
                Intent tambahPenjual = new Intent(this, PenjualActivity.class);
                tambahPenjual.putExtra("itemForBuy", (Serializable) keranjangItems);
                tambahPenjual.putExtra("isPenjualan", 1);
                if (namaPelanggan != null) {
                    tambahPenjual.putExtra("namaPelanggan", namaPelanggan);
                    tambahPenjual.putExtra("noHpPelanggan", noHpPelanggan);
                }
                if (namaPenjual != null) {
                    tambahPenjual.putExtra("namaPenjual", namaPenjual);
                    tambahPenjual.putExtra("idPenjual", idPenjual);
                }
                if (ongkir != null) {
                    tambahPenjual.putExtra("ongkir", ongkir);
                    tambahPenjual.putExtra("ekspedisi", ekspedisi);
                }
                if (diskonPersen != null) {
                    tambahPenjual.putExtra("diskonPersen", diskonPersen);
                }
                if (diskonRupiah != null) {
                    tambahPenjual.putExtra("diskonRupiah", diskonRupiah);
                }
                startActivity(tambahPenjual);
                break;
            case R.id.btn_add_diskon:
                BotSheetDiskonFragment botSheetDiskon = new BotSheetDiskonFragment();
                botSheetDiskon.setCancelable(false);
                botSheetDiskon.show(getSupportFragmentManager(), botSheetDiskon.getTag());
                break;
            case R.id.btn_add_ongkir:
                dialogOngkir();
                break;
            case R.id.btn_custom_item_keranjang:
                Intent custom = new Intent(this, CustomBarangActivity.class);
                startActivity(custom);
                break;
            case R.id.btn_konfirmasi_keranjang:
                Intent konfirmasi = new Intent(this, PembayaranActivity.class);
                if (ongkir == null && diskonRupiah == null && diskonPersen == null ) {
                    konfirmasi.putExtra("total_harga", tvSubtotalKeranjang.getText().toString());
                } else {
                    konfirmasi.putExtra("total_harga", tvTotalHargaKeranjang.getText().toString());
                }
                konfirmasi.putExtra("items",(Serializable) keranjangItems);
                konfirmasi.putExtra("ekspedisi", ekspedisi);
                konfirmasi.putExtra("ongkir", ongkir);
                konfirmasi.putExtra("diskonRupiah", diskonRupiah);
                konfirmasi.putExtra("diskonPersen", diskonPersen);
                konfirmasi.putExtra("diskon_remark", diskon_remark);
                konfirmasi.putExtra("namaPelanggan", namaPelanggan);
                konfirmasi.putExtra("noHpPelanggan", noHpPelanggan);
                konfirmasi.putExtra("namaPenjual", namaPenjual);
                konfirmasi.putExtra("idPenjual", idPenjual);
                startActivity(konfirmasi);
                break;
            case R.id.btn_barcode_keranjang:
                if (ContextCompat.checkSelfPermission(KeranjangActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.shouldShowRequestPermissionRationale(KeranjangActivity.this, Manifest.permission.CAMERA)){
                        startScan();
                    } else {
                        ActivityCompat.requestPermissions(KeranjangActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                    }
                } else {
                    startScan();
                }
                break;
        }
    }

    private BroadcastReceiver someBroadcastReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO extract extras from intent

            Double total = 0.00;
            DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
            df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
            SharedPreferences preferencesCart = getSharedPreferences("keranjang", Context.MODE_PRIVATE);
            if (intent.getStringExtra("diskon_rupiah") != null) {
                DecimalFormat formatter = new DecimalFormat("#,###.##");
                tvDiskon.setVisibility(View.VISIBLE);
                btnAddDiskon.setVisibility(View.GONE);
                tvDiskon.setText(String.valueOf(Double.valueOf(intent.getStringExtra("diskon_rupiah"))));
                if (ongkir != null) {
                    total = (Double.valueOf(preferencesCart.getString("subtotal", "0")) - Double.valueOf(intent.getStringExtra("diskon_rupiah"))) + Double.valueOf(ongkir);
                } else {
                    total = Double.valueOf(preferencesCart.getString("subtotal", "0")) - Double.valueOf(intent.getStringExtra("diskon_rupiah"));
                }
                diskonRupiah = intent.getStringExtra("diskon_rupiah");
                diskon_remark = intent.getStringExtra("diskon_remark");
                diskonPersen = null;
                tvTotalHargaKeranjang.setText(formatter.format(total));

            } else if (intent.getStringExtra("diskon_persen") != null) {
                DecimalFormat formatter = new DecimalFormat("#,###.##");
                tvDiskon.setVisibility(View.VISIBLE);
                btnAddDiskon.setVisibility(View.GONE);
                tvDiskon.setText(intent.getStringExtra("diskon_persen") + "%");
                if (ongkir != null) {
                    total = (Double.valueOf(preferencesCart.getString("subtotal", "0")) * ((100 - Double.valueOf(intent.getStringExtra("diskon_persen"))) / 100)) + Double.valueOf(ongkir);
                } else {
                    total = Double.valueOf(preferencesCart.getString("subtotal", "0")) * ((100 - Double.valueOf(intent.getStringExtra("diskon_persen"))) / 100);
                }
                diskonPersen = intent.getStringExtra("diskon_persen");
                diskon_remark = intent.getStringExtra("diskon_remark");
                diskonRupiah = null;
                tvTotalHargaKeranjang.setText(formatter.format(total));
            }

            if (tvDiskon.getVisibility() == View.VISIBLE) {
                tvDiskon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BotSheetDiskonFragment botSheetDiskon = new BotSheetDiskonFragment();
                        botSheetDiskon.setCancelable(false);
                        botSheetDiskon.show(getSupportFragmentManager(), botSheetDiskon.getTag());
                    }
                });
            }

        }
    };
    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(someBroadcastReceiver,
                new IntentFilter(INTENT_DISKON));
    }
    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(someBroadcastReceiver);
        super.onPause();
    }


    private void startScan() {
        Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
        startActivity(intent);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void dialogOngkir() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(KeranjangActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_ongkir,null);

        //init view
        final TextInputEditText etNamaEkspedisi = mView.findViewById(R.id.et_nama_ekspedisi);
        final TextInputEditText etOngkosKirim = mView.findViewById(R.id.et_ongkos_kirim);
        MaterialButton btnSimpan = mView.findViewById(R.id.btn_simpan_ongkir);
        MaterialButton btnCancel = mView.findViewById(R.id.btn_cancel_dialog);
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_white));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        int count = (tvSubtotalKeranjang.getText().toString()).length() - (tvSubtotalKeranjang.getText().toString()).replace(".", "").length();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat formatter = new DecimalFormat("#,###.##");
                Toast.makeText(KeranjangActivity.this, "Simpan", Toast.LENGTH_SHORT).show();
                Double total = 0.0;
                tvOngkir.setText(etNamaEkspedisi.getText().toString() +" - "+ String.valueOf(Double.valueOf(etOngkosKirim.getText().toString())));
                tvOngkir.setVisibility(View.VISIBLE);
                btnAddOngkir.setVisibility(View.GONE);

                ongkir = etOngkosKirim.getText().toString();
                ekspedisi = etNamaEkspedisi.getText().toString();

                if (diskonPersen != null) {
                    if (count > 1) {
                        total = Double.valueOf((tvSubtotalKeranjang.getText().toString().replace(".", ""))) * (100 - Double.valueOf(diskonPersen)) / 100;
                    } else {
                        total = Double.valueOf((tvSubtotalKeranjang.getText().toString().replace(",", ""))) * (100 - Double.valueOf(diskonPersen)) / 100;
                    }
                } else if (diskonRupiah != null) {
                    if (count > 1) {
                        total = Double.valueOf((tvSubtotalKeranjang.getText().toString().replace(".", ""))) - Double.valueOf(diskonRupiah);
                    } else {
                        total = Double.valueOf((tvSubtotalKeranjang.getText().toString().replace(",", ""))) - Double.valueOf(diskonRupiah);
                    }
                } else {
                    if (count > 1) {
                        total = Double.valueOf((tvSubtotalKeranjang.getText().toString().replace(".", "")));
                    } else {
                        total = Double.valueOf((tvSubtotalKeranjang.getText().toString().replace(",", "")));
                    }

                }
                tvTotalHargaKeranjang.setText(formatter.format(total + Double.valueOf(etOngkosKirim.getText().toString())));
                if (tvOngkir.getVisibility() == View.VISIBLE) {
                    tvOngkir.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogOngkir();
                        }
                    });
                }
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private void dialogSimpanPesanan() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(KeranjangActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_simpan_pesanan,null);

        //init view

        MaterialButton btnSimpan = mView.findViewById(R.id.btn_simpan_keterangan_pesanan);
        MaterialButton btnCancel = mView.findViewById(R.id.btn_cancel_dialog);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_white));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText etKetPesanan = mView.findViewById(R.id.et_ket_simpan_pesanan);
                pesananTungguItem = new PesananTungguItem(
                        null,
                        "",
                        "",
                        id_store,
                        lokasi_store,
                        noHpPelanggan,
                        namaPelanggan,
                        tvTotalHargaKeranjang.getText().toString(),
                        etKetPesanan.getText().toString(),
                        subItems()
                );
                if (pesananTungguItem != null) {
                    Call<List<PesananTungguItem>> call = pesananTungguEndpoint.savePesananTunggu(auth_token,pesananTungguItem);
                    SweetAlertDialog pDialog = new SweetAlertDialog(KeranjangActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading ...");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    call.enqueue(new Callback<List<PesananTungguItem>>() {
                        @Override
                        public void onResponse(Call<List<PesananTungguItem>> call, Response<List<PesananTungguItem>> response) {
                            if (!response.isSuccessful()){
                                pDialog.dismiss();
                                new SweetAlertDialog(KeranjangActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText(String.valueOf(response.code()))
                                        .setContentText(response.message())
                                        .show();
                            } else {
                                pDialog.dismiss();
                                Toast.makeText(KeranjangActivity.this, "Simpan", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<List<PesananTungguItem>> call, Throwable t) {
                            pDialog.dismiss();
                            new SweetAlertDialog(KeranjangActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                }

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

    public List<BarangPesananTungguItem> subItems(){
        for (int i=0; i<keranjangItems.size(); i++) {
            barangPesananTungguItems.add(new BarangPesananTungguItem(
                    keranjangItems.get(i).getFoto_barang(),
                    id_store,
                    lokasi_store,
                    keranjangItems.get(i).getSkuCode(),
                    keranjangItems.get(i).getTipeBarang(),
                    "",
                    keranjangItems.get(i).getArtikelBarang(),
                    keranjangItems.get(i).getNamaBarang(),
                    Double.valueOf(keranjangItems.get(i).getHargaBarang()),
                    Double.valueOf(keranjangItems.get(i).getHarga_baru()),
                    keranjangItems.get(i).getHarga_baru_remark(),
                    Double.valueOf(keranjangItems.get(i).getKuantitasBarang()),
                    Double.valueOf(keranjangItems.get(i).getTotalHargaBarang())
            ));
        }
        return barangPesananTungguItems;
    }

    private void Scanning(String cd) {

        final SearchView searchView = findViewById(R.id.search_barang_keranjang);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    keranjangItems = new ArrayList<>();
                    for (int i = 0; i < ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).size(); i++) {
                        if ((List<KeranjangItem>) extras.getSerializable("itemForBuy") != null) {
                            if (((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getArtikelBarang().equals(query)) {
                                keranjangItems.add(new KeranjangItem(
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getImBarang(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getTipeBarang(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getSkuCode(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getArtikelBarang(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getNamaBarang(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHarga_baru(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHarga_baru_remark(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang(),
                                        String.valueOf(Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang()) * Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang())),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()
                                ));
                            }
                        }
                    }
                    adapter = new KeranjangAdapter(keranjangItems, KeranjangActivity.this);
                    rvKeranjang.setAdapter(adapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    Bundle extras = getIntent().getExtras();
                    if (extras != null) {
                        keranjangItems = new ArrayList<>();
                        for (int i = 0; i < ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).size(); i++) {
                            if (Integer.parseInt(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()) != 0) {
                                keranjangItems.add(new KeranjangItem(
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getImBarang(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getTipeBarang(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getSkuCode(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getArtikelBarang(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getNamaBarang(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHarga_baru(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHarga_baru_remark(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang(),
                                        String.valueOf(Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang()) * Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang())),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()
                                ));
                            }
                        }
                        adapter = new KeranjangAdapter(keranjangItems, KeranjangActivity.this);
                        rvKeranjang.setAdapter(adapter);
                    }
                } else {
                    Bundle extras = getIntent().getExtras();
                    if (extras != null) {
                        keranjangItems = new ArrayList<>();
                        for (int i = 0; i < ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).size(); i++) {
                            if (Integer.parseInt(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()) != 0) {
                                if (((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getArtikelBarang().equals(newText)) {
                                    keranjangItems.add(new KeranjangItem(
                                            ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getImBarang(),
                                            ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getTipeBarang(),
                                            ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getSkuCode(),
                                            ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getArtikelBarang(),
                                            ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getNamaBarang(),
                                            ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang(),
                                            ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHarga_baru(),
                                            ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHarga_baru_remark(),
                                            ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang(),
                                            String.valueOf(Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang()) * Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang())),
                                            ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()
                                    ));
                                }
                            }
                        }
                        adapter = new KeranjangAdapter(keranjangItems, KeranjangActivity.this);
                        rvKeranjang.setAdapter(adapter);
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20){
            if (resultCode == RESULT_OK && data != null){
                String code = data.getStringExtra("result");
                //SET CODE
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startScan();
            } else {
                Toast.makeText(this, "Gagal membuka kamera!", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
