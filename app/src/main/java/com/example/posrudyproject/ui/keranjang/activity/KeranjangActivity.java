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
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.barcode.ScannerActivity;
import com.example.posrudyproject.ui.diskon.fragment.BotSheetDiskonFragment;
import com.example.posrudyproject.ui.keranjang.adapter.KeranjangAdapter;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.pelanggan.activity.PelangganActivity;
import com.example.posrudyproject.ui.pembayaran.activity.PembayaranActivity;
import com.example.posrudyproject.ui.penjual.activity.PenjualActivity;
import com.example.posrudyproject.ui.pesananTunggu.activity.PesananTungguActivity;
import com.example.posrudyproject.ui.produk.activity.CustomBarangActivity;
import com.example.posrudyproject.ui.ubahHarga.activity.UbahHargaActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class KeranjangActivity extends AppCompatActivity implements View.OnClickListener {

    SearchView searchView;
    AppCompatImageButton btnBarcode;
    AppCompatTextView tvSubtotalKeranjang,tvTotalHargaKeranjang;
    MaterialToolbar mToolbar;
    MaterialButton btnPotonganHarga, btnSimpanPesanan, btnAddPelanggan, btnAddPenjual, btnAddDiskon, btnAddOngkir, btnCustom, btnKonfirmasi,tvPelanggan,tvPenjual,tvDiskon,tvOngkir;
    RecyclerView rvKeranjang;
    KeranjangAdapter adapter;
    List<KeranjangItem> keranjangItems;
    ConstraintLayout layoutKeranjang, layoutEmpty, layoutPenjual;

    String noHpPelanggan,namaPelanggan,namaPenjual,ongkir,ekspedisi,diskonRupiah,diskonPersen;
    Integer idPenjual;
    Double subtotal = 0.00;
    public static final String INTENT_DISKON = "INTENT_DISKON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

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

        SetupSearchView();

        //Keranjang List
        Bundle extras = getIntent().getExtras();
        keranjangItems = new ArrayList<>();
        DecimalFormat formatter = new DecimalFormat("#,###.##");

        if (extras != null) {
            if ((List<KeranjangItem>) extras.getSerializable("itemForBuy") != null) {
                for (int i = 0; i < ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).size(); i++) {
                    if (Integer.parseInt(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()) != 0) {
                        keranjangItems.add(new KeranjangItem(
                                ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getImBarang(),
                                ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getTipeBarang(),
                                ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getSkuCode(),
                                ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getArtikelBarang(),
                                ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getNamaBarang(),
                                formatter.format(Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang())),
                                ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang(),
                                formatter.format(Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang()) * Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang())),
                                ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()
                        ));
                    }
                }
            } else if ((List<KeranjangItem>) extras.getSerializable("itemForBuyAfterPotong") != null) {
                keranjangItems = ((List<KeranjangItem>) extras.getSerializable("itemForBuyAfterPotong"));
            } else if ((List<KeranjangItem>) extras.getSerializable("itemForBuyAddPelanggan") != null) {
                keranjangItems = ((List<KeranjangItem>) extras.getSerializable("itemForBuyAddPelanggan"));
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
            }
        }
        //Setup Adapter
        KeranjangAdapter adapter = new KeranjangAdapter(keranjangItems,KeranjangActivity.this);
        rvKeranjang.setAdapter(adapter);
        rvKeranjang.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final Integer itemCount = adapter.getItemCount();
                subtotal = 0.00;
                if (itemCount != null) {
                    for (int i = 0; i<itemCount; i++) {
                        TextView total = rvKeranjang.getChildAt(i).findViewById(R.id.tv_total_harga_barang_keranjang);
                        subtotal  = subtotal + Double.valueOf(total.getText().toString().replace(",",""));
                    }
                    tvSubtotalKeranjang.setText(formatter.format(subtotal));
                    if (ongkir != null) {
                        tvTotalHargaKeranjang.setText(formatter.format(subtotal + Double.valueOf(ongkir)));
                    }
                    if (diskonPersen != null) {
                        tvTotalHargaKeranjang.setText(formatter.format(subtotal * ((100 - Double.valueOf(diskonPersen)) / 100)));
                    }
                    if (diskonRupiah != null) {
                        tvTotalHargaKeranjang.setText(formatter.format(subtotal - Double.valueOf(diskonRupiah)));
                    }

                    if (ongkir != null && diskonPersen != null) {
                        tvTotalHargaKeranjang.setText(formatter.format((subtotal * ((100 - Double.valueOf(diskonPersen)) / 100)) + Double.valueOf(ongkir)));
                    }
                    if (ongkir != null && diskonRupiah != null) {
                        tvTotalHargaKeranjang.setText(formatter.format((subtotal - Double.valueOf(diskonRupiah)) + Double.valueOf(ongkir)));
                    }
                }
            }
        });
        rvKeranjang.setLayoutManager(new LinearLayoutManager(this));
        rvKeranjang.setHasFixedSize(true);
        if (ongkir == null) {
            System.out.println(subtotal);
            tvTotalHargaKeranjang.setText(formatter.format(subtotal));
        }
        //Jika ada list item ilustrasi hilang
        if (adapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
            layoutKeranjang.setVisibility(View.VISIBLE);
        }
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
        mToolbar.setNavigationOnClickListener(view -> finish());
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
                Bundle extras = getIntent().getExtras();
                List<KeranjangItem> keranjangForPotong = new ArrayList<>();
                if (extras != null) {
                    if ((List<KeranjangItem>) extras.getSerializable("itemForBuy") != null) {
                        for (int i = 0; i < ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).size(); i++) {
                            if (Integer.parseInt(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()) != 0) {
                                keranjangForPotong.add(new KeranjangItem(
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getImBarang(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getTipeBarang(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getSkuCode(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getArtikelBarang(),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getNamaBarang(),
                                        String.valueOf(Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang())),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang(),
                                        String.valueOf(Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang()) * Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang())),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()
                                ));
                            }
                        }
                    }
                }
                potonganHarga.putExtra("itemForBuy", (Serializable) keranjangForPotong);
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
                startActivity(tambahPelanggan);
                break;

            case R.id.btn_add_penjual:
                Intent tambahPenjual = new Intent(this, PenjualActivity.class);
                tambahPenjual.putExtra("itemForBuy", (Serializable) keranjangItems);
                if (namaPelanggan != null) {
                    tambahPenjual.putExtra("namaPelanggan", namaPelanggan);
                    tambahPenjual.putExtra("noHpPelanggan", noHpPelanggan);
                }
                if (namaPenjual != null) {
                    tambahPenjual.putExtra("namaPenjual", namaPenjual);
                    tambahPenjual.putExtra("idPenjual", idPenjual);
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
            DecimalFormat decim = new DecimalFormat("#,###.##");
            Double total = 0.00;
            if (intent.getStringExtra("diskon_rupiah") != null) {
                tvDiskon.setVisibility(View.VISIBLE);
                btnAddDiskon.setVisibility(View.GONE);
                tvDiskon.setText(decim.format(Double.valueOf(intent.getStringExtra("diskon_rupiah"))));
                if (ongkir != null) {
                    total = (Double.valueOf(tvSubtotalKeranjang.getText().toString().replace(",","")) - Double.valueOf(intent.getStringExtra("diskon_rupiah"))) + Double.valueOf(ongkir);
                } else {
                    total = Double.valueOf(tvSubtotalKeranjang.getText().toString().replace(",","")) - Double.valueOf(intent.getStringExtra("diskon_rupiah"));
                }
                diskonRupiah = intent.getStringExtra("diskon_rupiah");
                diskonPersen = null;

            } else if (intent.getStringExtra("diskon_persen") != null) {
                tvDiskon.setVisibility(View.VISIBLE);
                btnAddDiskon.setVisibility(View.GONE);
                tvDiskon.setText(intent.getStringExtra("diskon_persen") + "%");
                if (ongkir != null) {
                    total = (Double.valueOf(tvSubtotalKeranjang.getText().toString().replace(",","")) * ((100 - Double.valueOf(intent.getStringExtra("diskon_persen"))) / 100)) + Double.valueOf(ongkir);
                } else {
                    total = Double.valueOf(tvSubtotalKeranjang.getText().toString().replace(",","")) * ((100 - Double.valueOf(intent.getStringExtra("diskon_persen"))) / 100);
                }
                diskonPersen = intent.getStringExtra("diskon_persen");
                diskonRupiah = null;
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
                tvTotalHargaKeranjang.setText(decim.format(total));
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
        startActivityForResult(intent, 20);
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

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_rounded_white));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat formatter = new DecimalFormat("#,###.##");
                Toast.makeText(KeranjangActivity.this, "Simpan", Toast.LENGTH_SHORT).show();
                Double total = 0.0;
                tvOngkir.setText(etNamaEkspedisi.getText().toString() +" - "+ formatter.format(Double.valueOf(etOngkosKirim.getText().toString())));
                tvOngkir.setVisibility(View.VISIBLE);
                btnAddOngkir.setVisibility(View.GONE);

                ongkir = etOngkosKirim.getText().toString();
                ekspedisi = etNamaEkspedisi.getText().toString();
                if (diskonPersen != null) {
                    total = Double.valueOf(tvSubtotalKeranjang.getText().toString().replace(",", "")) * (100 - Double.valueOf(diskonPersen)) / 100;
                } else if (diskonRupiah != null) {
                    total = Double.valueOf(tvSubtotalKeranjang.getText().toString().replace(",", "")) - Double.valueOf(diskonRupiah);
                } else {
                    total = Double.valueOf(tvSubtotalKeranjang.getText().toString().replace(",", ""));
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
        final TextInputEditText etKetPesanan = mView.findViewById(R.id.et_ket_simpan_pesanan);
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
                Toast.makeText(KeranjangActivity.this, "Simpan", Toast.LENGTH_SHORT).show();
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

    private void SetupSearchView() {

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
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang(),
                                        String.valueOf(Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang()) * Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang())),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()
                                ));
                            }
                        }
                    }
                    KeranjangAdapter adapter = new KeranjangAdapter(keranjangItems, KeranjangActivity.this);
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
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang(),
                                        String.valueOf(Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang()) * Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang())),
                                        ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()
                                ));
                            }
                        }
                        KeranjangAdapter adapter = new KeranjangAdapter(keranjangItems, KeranjangActivity.this);
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
                                            ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang(),
                                            String.valueOf(Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getHargaBarang()) * Double.valueOf(((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang())),
                                            ((List<KeranjangItem>) extras.getSerializable("itemForBuy")).get(i).getKuantitasBarang()
                                    ));
                                }
                            }
                        }
                        KeranjangAdapter adapter = new KeranjangAdapter(keranjangItems, KeranjangActivity.this);
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
