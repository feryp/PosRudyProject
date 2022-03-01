package com.example.posrudyproject.ui.penyimpanan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.penyimpanan.adapter.BarangPindahAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.BarangPindahItem;
import com.google.android.material.appbar.MaterialToolbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRiwayatActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    RecyclerView rvBarangPindah;
    ConstraintLayout layoutEmpty;
    BarangPindahAdapter adapter;
    List<BarangPindahItem> barangPindahItems;
    AppCompatTextView docNo;
    PenyimpananEndpoint penyimpananEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        int id_store = preferences.getInt("id_store", 0);
        String auth_token = ("Bearer ").concat(token);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);
        //INIT VIEW
        initComponent();

        initToolbar();
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            TextView docNo = (TextView) findViewById(R.id.tv_no_dokumen_barang_pindah);
            TextView tanggalPemindahan = (TextView) findViewById(R.id.tv_tgl_pemindahan_barang);
            TextView tokoTujuan = (TextView) findViewById(R.id.tv_toko_tujuan_barang_pindah);
            TextView karyawan = (TextView) findViewById(R.id.tv_nama_penjual_barang_pindah);
            TextView keterangan = (TextView) findViewById(R.id.tv_catatan_barang_pindah);
            docNo.setText(extras.getString("noDocBarang"));
            tanggalPemindahan.setText(extras.getString("tanggal_pengiriman"));
            tokoTujuan.setText(extras.getString("lokasi_store_tujuan"));
            karyawan.setText(extras.getString("nama_karyawan"));
            keterangan.setText(extras.getString("keterangan"));

            Call<List<BarangPindahItem>> call = penyimpananEndpoint.allPindah(auth_token, extras.getString("noDocBarang"));
            SweetAlertDialog pDialog = new SweetAlertDialog(DetailRiwayatActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            call.enqueue(new Callback<List<BarangPindahItem>>() {
                @Override
                public void onResponse(Call<List<BarangPindahItem>> call, Response<List<BarangPindahItem>> response) {
                    if (!response.isSuccessful()){
                        pDialog.dismiss();
                        new SweetAlertDialog(DetailRiwayatActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(String.valueOf(response.code()))
                                .setContentText(response.message())
                                .show();
                    } else {
                        pDialog.dismiss();
                        barangPindahItems = new ArrayList<>();
                        for (int i=0; i<response.body().size(); i++){
                            barangPindahItems.add(new BarangPindahItem(
                                    R.drawable.im_example,
                                    response.body().get(i).getTipeBarangPindah(),
                                    response.body().get(i).getSkuCode(),
                                    response.body().get(i).getArtikelBarangPindah(),
                                    response.body().get(i).getNamaBarangPindah(),
                                    response.body().get(i).getKuantitasBarangPindah()
                            ));
                        }

                        //Setup Adapter Barang Pindah
                        adapter = new BarangPindahAdapter(barangPindahItems, DetailRiwayatActivity.this);
                        rvBarangPindah.setAdapter(adapter);
                        //Jika ada list item ilustrasi hilang
                        if (adapter.getItemCount() > 0){
                            layoutEmpty.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<BarangPindahItem>> call, Throwable t) {
                    pDialog.dismiss();
                    new SweetAlertDialog(DetailRiwayatActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops..")
                            .setContentText(t.getMessage())
                            .show();
                }
            });
        }
        rvBarangPindah.setLayoutManager(new LinearLayoutManager(this));
        rvBarangPindah.setHasFixedSize(true);

    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_detail_riwayat_barang_pindah);
        rvBarangPindah = findViewById(R.id.rv_detail_barang_pindah);
        layoutEmpty = findViewById(R.id.layout_ilustrasi_empty_detail_barang_pindah);
    }
}