package com.example.posrudyproject.ui.pembayaran.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualanEndpoint;
import com.example.posrudyproject.ui.keranjang.activity.KeranjangActivity;
import com.example.posrudyproject.ui.keranjang.model.KeranjangItem;
import com.example.posrudyproject.ui.pembayaran.adapter.ViewPagerPembayaranAdapter;
import com.example.posrudyproject.ui.pembayaran.fragment.NonTunaiFragment;
import com.example.posrudyproject.ui.pembayaran.fragment.TunaiFragment;
import com.example.posrudyproject.ui.pembayaran.model.BankItem;
import com.example.posrudyproject.ui.pembayaran.model.DetailPesanan;
import com.example.posrudyproject.ui.pembayaran.model.Penjualan;
import com.example.posrudyproject.ui.penjualan.activity.PenjualanActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    TabLayout tabLayout;
    private ViewPagerPembayaranAdapter adapter;
    ViewPager2 viewPager;
    AppCompatTextView tvTotalHargaPembayaran,tvKembalianPembayaran;
    public static final String INTENT_TUNAI_PAS = "INTENT_TUNAI_PAS";
    List<KeranjangItem> keranjangItems;
    String ongkir,diskonPersen,diskonRupiah,ekspedisi,namaPelanggan,noHpPelanggan,namaPenjual,diskon_remark,auth_token,lokasi_store,nama_karyawan;
    Integer id_store,id_karyawan,idPenjual;
    Penjualan penjualan;
    List<DetailPesanan> detailPesananList;
    List<BankItem> bankItems;
    PenjualanEndpoint penjualanEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        auth_token = ("Bearer ").concat(token);
        id_store = preferences.getInt("id_store", 0);
        lokasi_store = preferences.getString("lokasi_store","");
        id_karyawan = preferences.getInt("id_pengguna", 0);
        nama_karyawan = preferences.getString("nama_pengguna","");
        keranjangItems = new ArrayList<>();
        detailPesananList = new ArrayList<>();
        bankItems = new ArrayList<>();
        penjualanEndpoint = ApiClient.getClient().create(PenjualanEndpoint.class);

        Call<List<BankItem>> call = penjualanEndpoint.getAllBank(auth_token);
        SweetAlertDialog pDialog = new SweetAlertDialog(PembayaranActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<List<BankItem>>() {
            @Override
            public void onResponse(Call<List<BankItem>> call, Response<List<BankItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(PembayaranActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    for (int i=0; i<response.body().size(); i++) {
                        bankItems.add(new BankItem(
                                response.body().get(i).getLogoBank(),
                                response.body().get(i).getNamaBank(),
                                response.body().get(i).getNoRekening()
                        ));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BankItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(PembayaranActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });
        //INIT VIEW
        initComponent();

        initToolbar();
        
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tvTotalHargaPembayaran.setText(("Rp").concat((bundle.getString("total_harga","0.00"))));
            diskonPersen = bundle.getString("diskonPersen");
            diskonRupiah = bundle.getString("diskonRupiah");
            diskon_remark = bundle.getString("diskon_remark");
            ekspedisi = bundle.getString("ekspedisi");
            ongkir = bundle.getString("ongkir");
            namaPelanggan = bundle.getString("namaPelanggan");
            noHpPelanggan = bundle.getString("noHpPelanggan");
            namaPenjual = bundle.getString("namaPenjual");
            idPenjual = bundle.getInt("idPenjual");
            keranjangItems = (List<KeranjangItem>) bundle.getSerializable("items");

            for (int i = 0; i < ((List<KeranjangItem>) bundle.getSerializable("items")).size(); i++) {
                detailPesananList.add(new DetailPesanan(
                        id_store,
                        lokasi_store,
                        ((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getSkuCode(),
                        "",
                        "",
                        ((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getArtikelBarang(),
                        ((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getNamaBarang(),
                        Double.valueOf(((((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getHargaBarang().replace("Rp","")))),
                        Double.valueOf(((((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getHarga_baru().replace("Rp","")))),
                        ((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getHarga_baru_remark(),
                        Double.valueOf(((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getKuantitasBarang()),
                        Double.valueOf(((((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getHargaBarang().replace("Rp","")))) * Double.valueOf(((List<KeranjangItem>) bundle.getSerializable("items")).get(i).getKuantitasBarang())
                ));
            }
        }

        //set tablayout
        tabLayout.addTab(tabLayout.newTab().setText("Tunai"));
        tabLayout.addTab(tabLayout.newTab().setText("Non Tunai"));

        setupViewPager(viewPager);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {tab.setText(adapter.mFragmetnTitle.get(position));
        }).attach();

    }

    public Penjualan konfirmasiPenjualan(String metode_bayar,String bankName,String noRek) {
        Double diskon = 0.0;
        Double ongkir_val = 0.00;
        if (diskonPersen != null) {
            diskon = Double.valueOf(diskonPersen);
        } else if (diskonRupiah != null) {
            diskon = Double.valueOf(diskonRupiah);
        } else {
            diskon = 0.00;
        }

        if (ongkir != null) {
            ongkir_val = Double.valueOf(((ongkir).replace("Rp","")));
        } else {
            ongkir_val = 0.00;
        }
        int count = ((tvTotalHargaPembayaran.getText().toString()).replace("Rp","")).length() - ((tvTotalHargaPembayaran.getText().toString()).replace("Rp","")).replace(".", "").length();
        if (count > 1) {
            penjualan = new Penjualan(
                    "",
                    null,
                    id_store,
                    lokasi_store,
                    noHpPelanggan,
                    namaPelanggan,
                    idPenjual,
                    namaPenjual,
                    diskon,
                    diskon_remark,
                    metode_bayar,
                    bankName,
                    noRek,
                    ekspedisi,
                    ongkir_val,
                    Double.valueOf(((tvTotalHargaPembayaran.getText().toString()).replace("Rp","")).replace(".","")),
                    Double.valueOf(((tvKembalianPembayaran.getText().toString()).replace("Rp","")).replace(".","")),
                    detailPesananList
            );
        } else {
            penjualan = new Penjualan(
                    "",
                    null,
                    id_store,
                    lokasi_store,
                    noHpPelanggan,
                    namaPelanggan,
                    idPenjual,
                    namaPenjual,
                    diskon,
                    diskon_remark,
                    metode_bayar,
                    bankName,
                    noRek,
                    ekspedisi,
                    ongkir_val,
                    Double.valueOf(((tvTotalHargaPembayaran.getText().toString()).replace("Rp","")).replace(",","")),
                    Double.valueOf(((tvKembalianPembayaran.getText().toString()).replace("Rp","")).replace(",","")),
                    detailPesananList
            );
        }

        return penjualan;
    }

    public  List<KeranjangItem> GetItems() {
        return keranjangItems;
    }

    public Map Details(){
        Map<String,String> details = new HashMap<>();
        details.put("ongkir", ongkir);
        details.put("ekspedisi", ekspedisi);
        details.put("diskonRupiah", diskonRupiah);
        details.put("diskonPersen", diskonPersen);
        details.put("namaPelanggan", namaPelanggan);
        details.put("noHpPelanggan", noHpPelanggan);
        details.put("namaPenjual", namaPenjual);
        details.put("idPenjual", String.valueOf(idPenjual));
        return details;
    }

    private void setupViewPager(ViewPager2 viewPager) {
        adapter = new ViewPagerPembayaranAdapter(this.getSupportFragmentManager(),
                PembayaranActivity.this.getLifecycle());
        adapter.addFragment(new TunaiFragment(), "Tunai");
        adapter.addFragment(new NonTunaiFragment(), "Non Tunai");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_delete) {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Konfirmasi Hapus")
                        .setMessage("Pesanan akan dihapus, Lanjutkan ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //function delete
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
                return true;
            }
            return false;
        });
    }

    private void initComponent() {
        //init
        mToolbar = findViewById(R.id.toolbar_pembayaran);
        tabLayout = findViewById(R.id.tab_pembayaran);
        viewPager = findViewById(R.id.vp_pembayaran);
        tvTotalHargaPembayaran = findViewById(R.id.tv_total_harga_pembayaran);
        tvKembalianPembayaran = findViewById(R.id.tv_kembalian_pembayaran);
    }

    public String getTotal() {
        return tvTotalHargaPembayaran.getText().toString();
    }

    private BroadcastReceiver someBroadcastReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO extract extras from intent
            DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
            df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
            int countTotal = (tvTotalHargaPembayaran.getText().toString().replace("Rp","")).length() - (tvTotalHargaPembayaran.getText().toString().replace("Rp","")).replace(".", "").length();
            if (intent.getStringExtra("uang_diterima") == null ) {
                tvKembalianPembayaran.setText(String.valueOf(0.0));
            } else {
                if (countTotal > 1) {
                    tvKembalianPembayaran.setText(df.format(
                            Double.valueOf(
                                    (((intent.getStringExtra("uang_diterima")).replace("Rp",""))).equals("") ? "0" : ((intent.getStringExtra("uang_diterima")).replace("Rp","")).replace(".","")
                            ) -
                                    Double.valueOf(
                                            (((tvTotalHargaPembayaran.getText().toString().replace("Rp","")))).equals("") ? "0" : ((tvTotalHargaPembayaran.getText().toString().replace("Rp",""))).replace(".","")
                                    )
                    ));
                } else {
                    tvKembalianPembayaran.setText(df.format(
                            Double.valueOf(
                                    (((intent.getStringExtra("uang_diterima")).replace("Rp",""))).equals("") ? "0" : ((intent.getStringExtra("uang_diterima")).replace("Rp","")).replace(",","")
                            ) -
                                    Double.valueOf(
                                            (((tvTotalHargaPembayaran.getText().toString().replace("Rp","")))).equals("") ? "0" : (tvTotalHargaPembayaran.getText().toString().replace("Rp","")).replace(",","")
                                    )
                    ));
                }
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(someBroadcastReceiver,
                new IntentFilter(INTENT_TUNAI_PAS));
    }
    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(someBroadcastReceiver);
        super.onPause();
    }
}