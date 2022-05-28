package com.example.posrudyproject.ui.penyimpanan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.penjual.activity.PenjualActivity;
import com.example.posrudyproject.ui.penyimpanan.fragment.DataTokoFragment;
import com.example.posrudyproject.ui.penyimpanan.fragment.DetailPemindahanFragment;
import com.example.posrudyproject.ui.penyimpanan.fragment.PilihPemindahanBarangFragment;
import com.example.posrudyproject.ui.penyimpanan.model.PemindahanBarangItem;
import com.example.posrudyproject.ui.penyimpanan.model.PengirimanBarang;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shuhart.stepview.StepView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemindahanBarangActivity extends AppCompatActivity implements View.OnClickListener{

    MaterialToolbar mToolbar;
    View layoutBtnStep1, layoutBtnStep2, layoutBtnStep3;
    MaterialButton btnLanjutStep1, btnLanjutStep2, btnKembaliStep2, btnKembaliStep3, btnSelesai;
    StepView stepView;
    List<PemindahanBarangItem> pemindahanBarangItems;
    FragmentManager fragmentManager = null;
    int stepIndex = 0;
    int id_store;
    String lokasi_store,auth_token;
    PenyimpananEndpoint penyimpananEndpoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemindahan_barang);
        fragmentManager = getSupportFragmentManager();
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);
        SharedPreferences preferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");

        auth_token = ("Bearer ").concat(token);
        id_store = preferences.getInt("id_store", 0);
        lokasi_store = preferences.getString("lokasi_store","");

        Bundle bundle = new Bundle();
        bundle.putString("token", auth_token);
        bundle.putInt("id_store",id_store);

        if (savedInstanceState == null) {
            PilihPemindahanBarangFragment fragment = new PilihPemindahanBarangFragment();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_pemindahan_barang, fragment, null)
                    .commit();
        }

        //INIT VIEW
        initComponent();

        initToolbar();

        //Set Step View
        stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .stepsNumber(3)
                .steps(new ArrayList<String>(){{
                    add("Pilih Barang");
                    add("Data Toko");
                    add("Detail Dokumen");
                }})
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .typeface(ResourcesCompat.getFont(this, R.font.work_sans_medium))
                .commit();

        //SET LISTENER
        btnLanjutStep1.setOnClickListener(this);
        btnLanjutStep2.setOnClickListener(this);
        btnKembaliStep2.setOnClickListener(this);
        btnKembaliStep3.setOnClickListener(this);
        btnSelesai.setOnClickListener(this);

    }

    private void initComponent() {
        mToolbar = findViewById(R.id.toolbar_pemindahan_barang);
        stepView = findViewById(R.id.step_view_pemindahan_barang);
        btnLanjutStep1 = findViewById(R.id.btn_lanjut_step_1);
        btnLanjutStep2 = findViewById(R.id.btn_lanjut_step_2);
        btnKembaliStep2 = findViewById(R.id.btn_kembali_step_2);
        btnKembaliStep3 = findViewById(R.id.btn_kembali_step_3);
        btnSelesai = findViewById(R.id.btn_selesai);
        layoutBtnStep1 = findViewById(R.id.layout_button1);
        layoutBtnStep2 = findViewById(R.id.layout_button2);
        layoutBtnStep3 = findViewById(R.id.layout_button3);

    }

    private void initToolbar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PemindahanBarangActivity.this, PenyimpananActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("token", auth_token);
        bundle.putInt("id_store",id_store);

        switch (view.getId()){
            case R.id.btn_lanjut_step_1:
                stepIndex++;
                stepView.go(stepIndex, true);
                changeStepFragment(DataTokoFragment.class);
                layoutBtnStep1.setVisibility(View.GONE);
                layoutBtnStep2.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_lanjut_step_2:
                stepIndex++;
                stepView.go(stepIndex, true);
                changeStepFragment(DetailPemindahanFragment.class);
                layoutBtnStep1.setVisibility(View.GONE);
                layoutBtnStep2.setVisibility(View.GONE);
                layoutBtnStep3.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_kembali_step_2:
                stepIndex--;
                stepView.go(stepIndex, true);
                changeStepFragment(PilihPemindahanBarangFragment.class);
                layoutBtnStep1.setVisibility(View.VISIBLE);
                layoutBtnStep2.setVisibility(View.GONE);
                layoutBtnStep3.setVisibility(View.GONE);
                break;
            case R.id.btn_kembali_step_3:
                SharedPreferences produkPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editorProduk = produkPreferences.edit();
                editorProduk.putInt("tokoTujuanId", 0);
                editorProduk.putString("tokoTujuanName", "");
                editorProduk.putInt("penjualId", 0);
                editorProduk.putString("penjualName", "");
                editorProduk.putString("catatan", "");
                editorProduk.apply();
                stepIndex--;
                stepView.go(stepIndex, true);
                changeStepFragment(DataTokoFragment.class);
                layoutBtnStep1.setVisibility(View.GONE);
                layoutBtnStep2.setVisibility(View.VISIBLE);
                layoutBtnStep3.setVisibility(View.GONE);
                break;
            case R.id.btn_selesai:
                SharedPreferences pemindahanPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                pemindahanBarangItems = new ArrayList<>();
                String json = pemindahanPreferences.getString("pemindahanBarangItems","");
                int id_toko_tujuan = pemindahanPreferences.getInt("tokoTujuanId",0);
                String nama_toko_tujuan = pemindahanPreferences.getString("tokoTujuanName","");
                int id_penjual = pemindahanPreferences.getInt("penjualId",0);
                String nama_penjual = pemindahanPreferences.getString("penjualName","");
                String catatan = pemindahanPreferences.getString("catatan","");

                if (json != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<PemindahanBarangItem>>(){}.getType();
                    pemindahanBarangItems = gson.fromJson(json, type);
                }
                List<PengirimanBarang> items = new ArrayList<>();
                items.add(new PengirimanBarang(
                        id_store,
                        lokasi_store,
                        id_toko_tujuan,
                        nama_toko_tujuan,
                        id_penjual,
                        nama_penjual,
                        catatan,
                        pemindahanBarangItems
                ));
                Call<Map> call = penyimpananEndpoint.saveProduct(auth_token, items.get(0));
                SweetAlertDialog pDialog = new SweetAlertDialog(PemindahanBarangActivity.this, SweetAlertDialog.WARNING_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();
                call.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {
                        pDialog.dismiss();
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(PemindahanBarangActivity.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText(response.body().get("message").toString());
                        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Intent intent = new Intent(PemindahanBarangActivity.this, PemindahanBarangActivity.class);
                                startActivity(intent);
                            }
                        });
                        sweetAlertDialog.show();
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(PemindahanBarangActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        Intent intent = new Intent(PemindahanBarangActivity.this, PemindahanBarangActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                });
                break;
        }
    }

    private void openDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View mView = View.inflate(this, R.layout.dialog_alert_message,null);

        //init view
        AppCompatTextView titleDialog = mView.findViewById(R.id.txt_title_dialog);
        AppCompatTextView pesanDialog = mView.findViewById(R.id.txt_pesan_dialog);
        MaterialButton btnOke = mView.findViewById(R.id.btn_oke_dialog);
        MaterialButton btnCancel = mView.findViewById(R.id.btn_cancel_dialog);

        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_rounded_white);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        titleDialog.setText(getString(R.string.title_dialog_pemindahan));
        pesanDialog.setText(getString(R.string.pesan_dialog_pemindahan));

        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FUNCTION CUSTOM FINISH
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

    protected void changeStepFragment(@NonNull Class<? extends Fragment> fragmentClass){
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_pemindahan_barang, fragmentClass, null)
                .addToBackStack("name")
                .commit();
    }
}