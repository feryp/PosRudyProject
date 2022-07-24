package com.example.posrudyproject.ui.penyimpanan.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.filter.adapter.NothingSelectedSpinnerAdapter;
import com.example.posrudyproject.ui.keranjang.adapter.KeranjangAdapter;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.example.posrudyproject.ui.penyimpanan.activity.PemindahanBarangActivity;
import com.example.posrudyproject.ui.penyimpanan.adapter.PemindahanBarangAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.PemindahanBarangItem;
import com.example.posrudyproject.ui.produk.fragment.BotSheetProdukFragment;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihPemindahanBarangFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    AppCompatSpinner spinTipe;
    MaterialButton btnPilihBarang;
    RecyclerView rvListBarang;
    ConstraintLayout layoutEmpty;
    PenyimpananEndpoint penyimpananEndpoint;
    List<PemindahanBarangItem> pemindahanBarangItems;
    PemindahanBarangAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pilih_pemindahan_barang, container, false);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        String auth_token = ("Bearer ").concat(token);
        int id_store = preferences.getInt("id_store", 0);

        Bundle bundle = getArguments();
        SharedPreferences pemindahanPreferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
        String json = pemindahanPreferences.getString("pemindahanBarangItems","");
        //INIT VIEW
        spinTipe = v.findViewById(R.id.spinner_tipe_pemindahan_barang);
        btnPilihBarang = v.findViewById(R.id.btn_pilih_barang_pemindahan);
        rvListBarang = v.findViewById(R.id.rv_list_pemindahan_barang);
        layoutEmpty = v.findViewById(R.id.layout_ilustrasi_empty_pemindahan_barang);
        final int[] tipePrev = {(int) spinTipe.getSelectedItemId()};
        //SPINNER TIPE
        // Spinner Drop down elements
        Call<List<TipeItem>> call = penyimpananEndpoint.getAllTipe(auth_token);
        call.enqueue(new Callback<List<TipeItem>>() {
            @Override
            public void onResponse(Call<List<TipeItem>> call, Response<List<TipeItem>> response) {
                List<TipeItem> tipeItems = new ArrayList<>();
                if (!response.isSuccessful()){
                    tipeItems.add(new TipeItem(0,"Master Tipe Kosong"));
                }else{
                    for (int i=0; i < response.body().size(); i++){
                        tipeItems.add(new TipeItem(
                                response.body().get(i).getId(),
                                response.body().get(i).getNamaTipe()
                        ));
                    }
                    //Update object to string
                    List<String> tipeItem = new ArrayList<String>();
                    for (TipeItem item : tipeItems){
                        tipeItem.add(item.getNamaTipe());
                    }

                    // Creating adapter for spinner
                    ArrayAdapter<String> tipeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, tipeItem);
                    tipeAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
                    spinTipe.setAdapter(new NothingSelectedSpinnerAdapter(tipeAdapter, getActivity(), R.layout.spinner_text_nothing_selected, getString(R.string.hint_spinner_pilih_tipe)));
                }
                if (bundle != null) {
                    if (((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")) != null) {
                        spinTipe.setSelection(bundle.getInt("tipe"));
                    }
                } else if (json != null) {
                    spinTipe.setSelection(pemindahanPreferences.getInt("tipe",0));
                }
            }

            @Override
            public void onFailure(Call<List<TipeItem>> call, Throwable t) {
                List<TipeItem> tipeItems = new ArrayList<>();
                tipeItems.add(new TipeItem(0,"Master Tipe Kosong"));
            }
        });

        //Barang List;
        pemindahanBarangItems = new ArrayList<>();
        if (bundle != null) {
            if (((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")) != null) {

                for (int i=0; i< ((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")).size(); i++){
                    pemindahanBarangItems.add(new PemindahanBarangItem(
                            ((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")).get(i).getFoto_barang(),
                            ((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")).get(i).getTipeProduk(),
                            ((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")).get(i).getSkuCode(),
                            ((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")).get(i).getArtikelProduk(),
                            ((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")).get(i).getNamaProduk(),
                            ((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")).get(i).getJumlahProduk()

                    ));
                }
                //Setup Adapter
                adapter = new PemindahanBarangAdapter(pemindahanBarangItems, getActivity());
                rvListBarang.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvListBarang.setAdapter(adapter);
                rvListBarang.setHasFixedSize(true);
                if (adapter.getItemCount() > 0){
                    layoutEmpty.setVisibility(View.GONE);
                }

                rvListBarang.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        SharedPreferences preferences = v.getContext().getSharedPreferences("pindahItems", v.getContext().MODE_PRIVATE);
                        for (int i=0; i<pemindahanBarangItems.size(); i++) {
                            pemindahanBarangItems.get(i).setJumlahProduk(preferences.getString(pemindahanBarangItems.get(i).getArtikelProduk(),"0"));
                        }

                        SharedPreferences produkPreferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                        SharedPreferences.Editor editorProduk = produkPreferences.edit();

                        Gson gson = new Gson();
                        String json = gson.toJson(pemindahanBarangItems);
                        if (bundle != null) {
                            editorProduk.putInt("tipe",bundle.getInt("tipe"));
                        }
                        editorProduk.putString("pemindahanBarangItems", json);
                        editorProduk.apply();

                    }
                });
            }
        } else if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<PemindahanBarangItem>>(){}.getType();
            pemindahanBarangItems = gson.fromJson(json, type);

            //Setup Adapter
            adapter = new PemindahanBarangAdapter(pemindahanBarangItems, getActivity());
            rvListBarang.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvListBarang.setAdapter(adapter);
            rvListBarang.setHasFixedSize(true);
            if (adapter.getItemCount() > 0){
                layoutEmpty.setVisibility(View.GONE);
            }

            rvListBarang.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    SharedPreferences produkPreferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    SharedPreferences.Editor editorProduk = produkPreferences.edit();

                    SharedPreferences preferences = getContext().getSharedPreferences("pindahItems", Context.MODE_PRIVATE);
                    for (int i=0; i<pemindahanBarangItems.size(); i++) {
                        pemindahanBarangItems.get(i).setJumlahProduk(preferences.getString(pemindahanBarangItems.get(i).getArtikelProduk(),"0"));
                    }

                    Gson gson = new Gson();
                    String json = gson.toJson(pemindahanBarangItems);
                    if (bundle != null) {
                        editorProduk.putInt("tipe",bundle.getInt("tipe"));
                    }
                    editorProduk.putString("pemindahanBarangItems", json);
                    editorProduk.apply();
                }
            });
        }

        //Setup Adapter
        adapter = new PemindahanBarangAdapter(pemindahanBarangItems, getActivity());
        rvListBarang.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvListBarang.setAdapter(adapter);
        rvListBarang.setHasFixedSize(true);

        if (adapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
        }

        //SET LISTENER
        btnPilihBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tipe = (int) (spinTipe.getSelectedItemId() + 1);
                if (tipePrev[0] == 0) {
                    tipePrev[0] = tipe;

                    if (pemindahanBarangItems.size() > 0) {
                        SharedPreferences preferences = getContext().getSharedPreferences("pindahItems", Context.MODE_PRIVATE);
                        for (int i=0; i<pemindahanBarangItems.size(); i++) {
                            pemindahanBarangItems.get(i).setJumlahProduk(preferences.getString(pemindahanBarangItems.get(i).getArtikelProduk(),"0"));
                        }
                        Bundle bundle = new Bundle();
                        bundle.putInt("tipe", tipe);
                        bundle.putString("token", auth_token);
                        bundle.putInt("id_store", id_store);
                        bundle.putString("for", "PemindahanBarang");
                        bundle.putSerializable("prevBarangPindah" ,(Serializable)pemindahanBarangItems);
                        BotSheetProdukFragment botSheetProduk = new BotSheetProdukFragment();
                        botSheetProduk.setCancelable(false);
                        botSheetProduk.setArguments(bundle);
                        botSheetProduk.show(getChildFragmentManager(), botSheetProduk.getTag());
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putInt("tipe", tipe);
                        bundle.putString("token", auth_token);
                        bundle.putInt("id_store", id_store);
                        bundle.putString("for", "PemindahanBarang");
                        BotSheetProdukFragment botSheetProduk = new BotSheetProdukFragment();
                        botSheetProduk.setCancelable(false);
                        botSheetProduk.setArguments(bundle);
                        botSheetProduk.show(getChildFragmentManager(), botSheetProduk.getTag());
                    }
                } else if (tipe != tipePrev[0] && tipe > 0) {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("Apakah Yakin mengganti tipe?");
                    sweetAlertDialog.setContentText("Barang terpilih akan di reset");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            pemindahanBarangItems = new ArrayList<>();
                            adapter = new PemindahanBarangAdapter(pemindahanBarangItems, getActivity());
                            rvListBarang.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvListBarang.setAdapter(adapter);
                            rvListBarang.setHasFixedSize(true);
                            Bundle bundle = new Bundle();
                            bundle.putInt("tipe", tipe);
                            bundle.putString("token", auth_token);
                            bundle.putInt("id_store", id_store);
                            bundle.putString("for", "PemindahanBarang");
                            BotSheetProdukFragment botSheetProduk = new BotSheetProdukFragment();
                            botSheetProduk.setCancelable(false);
                            botSheetProduk.setArguments(bundle);
                            botSheetProduk.show(getChildFragmentManager(), botSheetProduk.getTag());
                            sweetAlertDialog.dismiss();
                        }
                    });
                    sweetAlertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            spinTipe.setSelection(tipePrev[0]);
                            sweetAlertDialog.dismiss();
                        }
                    });
                    sweetAlertDialog.show();
                } else {
                    if (pemindahanBarangItems.size() > 0) {
                        SharedPreferences preferences = getContext().getSharedPreferences("pindahItems", Context.MODE_PRIVATE);
                        for (int i=0; i<pemindahanBarangItems.size(); i++) {
                            pemindahanBarangItems.get(i).setJumlahProduk(preferences.getString(pemindahanBarangItems.get(i).getArtikelProduk(),"0"));
                        }
                        Bundle bundle = new Bundle();
                        bundle.putInt("tipe", tipe);
                        bundle.putString("token", auth_token);
                        bundle.putInt("id_store", id_store);
                        bundle.putString("for", "PemindahanBarang");
                        bundle.putSerializable("prevBarangPindah" ,(Serializable)pemindahanBarangItems);
                        BotSheetProdukFragment botSheetProduk = new BotSheetProdukFragment();
                        botSheetProduk.setCancelable(false);
                        botSheetProduk.setArguments(bundle);
                        botSheetProduk.show(getChildFragmentManager(), botSheetProduk.getTag());
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putInt("tipe", tipe);
                        bundle.putString("token", auth_token);
                        bundle.putInt("id_store", id_store);
                        bundle.putString("for", "PemindahanBarang");
                        BotSheetProdukFragment botSheetProduk = new BotSheetProdukFragment();
                        botSheetProduk.setCancelable(false);
                        botSheetProduk.setArguments(bundle);
                        botSheetProduk.show(getChildFragmentManager(), botSheetProduk.getTag());
                    }

                }

            }
        });

        return v;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String item = adapterView.getItemAtPosition(position).toString();

        //Showing selected spinner item
        if (position > 0) {
            // Notify the selected item text
            Toast.makeText(getActivity(), "Pilih : " + item, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {

    }
}