package com.example.posrudyproject.ui.penyimpanan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.penyimpanan.adapter.PagerMenuAdapter;
import com.example.posrudyproject.ui.penyimpanan.fragment.BarangKeluarFragment;
import com.example.posrudyproject.ui.penyimpanan.fragment.BarangMasukFragment;
import com.example.posrudyproject.ui.penyimpanan.fragment.BarangPindahFragment;
import com.example.posrudyproject.ui.penyimpanan.model.BarangKeluarItem;
import com.example.posrudyproject.ui.penyimpanan.model.BarangMasukItem;
import com.example.posrudyproject.ui.penyimpanan.model.BarangPindahItem;
import com.example.posrudyproject.ui.penyimpanan.model.DokumenBarangPindahItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPenyimpananActivity extends AppCompatActivity {

    MaterialToolbar mToolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerMenuAdapter adapter;
    List<BarangMasukItem> barangMasukItems;
    List<BarangKeluarItem> barangKeluarItems;
    List<DokumenBarangPindahItem> dokumenBarangPindahItems;
    int betweenSpace = 16;
    PenyimpananEndpoint penyimpananEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_penyimpanan);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        int id_store = preferences.getInt("id_store", 0);
        String auth_token = ("Bearer ").concat(token);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);
        //INIT VIEW
        initComponent();

        initToolbar();

        //add fragment
        Call<Map> call = penyimpananEndpoint.getAllPerStore(auth_token, id_store);
        SweetAlertDialog pDialog = new SweetAlertDialog(RiwayatPenyimpananActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(RiwayatPenyimpananActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    pDialog.dismiss();
                    Map<String, Map<String, Object>> inner = (Map<String, Map<String, Object>>) response.body().get("result");
                    barangMasukItems = (List<BarangMasukItem>) inner.get("masuk");
                    barangKeluarItems = (List<BarangKeluarItem>) inner.get("keluar");
                    dokumenBarangPindahItems = (List<DokumenBarangPindahItem>) inner.get("pindah");

                    //initialize adapter
                    adapter = new PagerMenuAdapter(getSupportFragmentManager());
                    BarangMasukFragment barangMasukFragment = new BarangMasukFragment();
                    Bundle bundleMasuk = new Bundle();
                    bundleMasuk.putSerializable("barangMasukItems", (Serializable) barangMasukItems);
                    bundleMasuk.putString("authToken", auth_token);
                    bundleMasuk.putInt("idStore", id_store);
                    barangMasukFragment.setArguments(bundleMasuk);
                    adapter.AddFragment(barangMasukFragment, "Barang Masuk");

                    BarangKeluarFragment barangKeluarFragment = new BarangKeluarFragment();
                    Bundle bundleKeluar = new Bundle();
                    bundleKeluar.putSerializable("barangKeluarItems", (Serializable) barangKeluarItems);
                    bundleKeluar.putString("authToken", auth_token);
                    bundleKeluar.putInt("idStore", id_store);
                    barangKeluarFragment.setArguments(bundleKeluar);
                    adapter.AddFragment(barangKeluarFragment, "Barang Keluar");

                    BarangPindahFragment barangPindahFragment = new BarangPindahFragment();
                    Bundle bundlePindah = new Bundle();
                    bundlePindah.putSerializable("dokumenBarangPindahItems", (Serializable) dokumenBarangPindahItems);
                    bundlePindah.putString("authToken", auth_token);
                    bundlePindah.putInt("idStore", id_store);
                    barangPindahFragment.setArguments(bundlePindah);
                    adapter.AddFragment(barangPindahFragment, "Barang Pindah");

                    //set adapter
                    viewPager.setAdapter(adapter);
                    //connect tab layout with view pager
                    tabLayout.setupWithViewPager(viewPager);
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(RiwayatPenyimpananActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });


        //Setting margin tab layout
        ViewGroup slidingTabStrip = (ViewGroup) tabLayout.getChildAt(0);
        for (int i=0; i<slidingTabStrip.getChildCount()-1; i++){
            View v = slidingTabStrip.getChildAt(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            params.rightMargin = betweenSpace;
        }


    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_riwayat_barang);
        tabLayout = findViewById(R.id.tab_menu_kategori_barang);
        viewPager = findViewById(R.id.vp_riwayat_barang);
    }

}