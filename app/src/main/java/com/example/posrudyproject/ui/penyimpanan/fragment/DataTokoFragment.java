package com.example.posrudyproject.ui.penyimpanan.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenjualEndpoint;
import com.example.posrudyproject.retrofit.TokoEndpoint;
import com.example.posrudyproject.ui.filter.adapter.NothingSelectedSpinnerAdapter;
import com.example.posrudyproject.ui.penjual.model.PenjualItem;
import com.example.posrudyproject.ui.penjual.model.TokoItem;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataTokoFragment extends Fragment {

    AppCompatSpinner spinTokoTujuan, spinPenjual;
    TextInputEditText etCatatan;
    PenjualEndpoint penjualEndpoint;
    TokoEndpoint tokoEndpoint;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_data_toko, container, false);
        tokoEndpoint = ApiClient.getClient().create(TokoEndpoint.class);
        penjualEndpoint = ApiClient.getClient().create(PenjualEndpoint.class);
        //INIT VIEW
        spinTokoTujuan = v.findViewById(R.id.spinner_toko_tujuan_pemindahan);
        spinPenjual = v.findViewById(R.id.spinner_penjual_pemindahan);
        etCatatan = v.findViewById(R.id.et_catatan_pemindahan);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        String token = preferences.getString("token","");
        String auth_token = ("Bearer ").concat(token);
        int id_store = preferences.getInt("id_store", 0);

        List<TokoItem> tokoItems = new ArrayList<>();
        List<PenjualItem> penjualItems = new ArrayList<>();

        Call<List<TokoItem>> callToko = tokoEndpoint.getAll(auth_token);
        callToko.enqueue(new Callback<List<TokoItem>>() {
            @Override
            public void onResponse(Call<List<TokoItem>> call, Response<List<TokoItem>> response) {
                if (response.isSuccessful()){
                    //SPINNER TOKO TUJUAN
                    // Spinner Drop down elements

                    for (int i=0; i<response.body().size(); i++){
                        tokoItems.add(new TokoItem(
                                response.body().get(i).getId(),
                                response.body().get(i).getNamaToko()
                        ));
                    }
                    //Update object to string
                    List<String> tokoItem = new ArrayList<String>();
                    for (TokoItem item : tokoItems){
                        tokoItem.add(item.getNamaToko());
                    }
                    // Creating adapter for spinner
                    ArrayAdapter<String> tokoAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, tokoItem);
                    tokoAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
                    spinTokoTujuan.setAdapter(new NothingSelectedSpinnerAdapter(tokoAdapter, getActivity(), R.layout.spinner_text_nothing_selected, getString(R.string.hint_spinner_pilih_toko)));

                }
            }

            @Override
            public void onFailure(Call<List<TokoItem>> call, Throwable t) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        //Penjual List
        Call<List<PenjualItem>> call = penjualEndpoint.getAllByIdStore(auth_token, id_store);
        call.enqueue(new Callback<List<PenjualItem>>() {
            @Override
            public void onResponse(Call<List<PenjualItem>> call, Response<List<PenjualItem>> response) {
                if (!response.isSuccessful()){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                } else {
                    //SPINNER PENJUAL
                    // Spinner Drop down elements

                    for (int i=0; i<response.body().size(); i++){
                        penjualItems.add(new PenjualItem(
                                response.body().get(i).getId(),
                                response.body().get(i).getTanggal_join(),
                                response.body().get(i).getNama_karyawan(),
                                response.body().get(i).getId_office(),
                                response.body().get(i).getLokasi_office(),
                                response.body().get(i).getId_store(),
                                response.body().get(i).getLokasi_store(),
                                response.body().get(i).getJabatan(),
                                response.body().get(i).getNo_hp(),
                                response.body().get(i).getEmail(),
                                response.body().get(i).getAlamat(),
                                response.body().get(i).getTotal_transaksi(),
                                response.body().get(i).getRowstatus(),
                                response.body().get(i).getImage()
                        ));
                    }
                    //Update object to string
                    List<String> penjualItem = new ArrayList<String>();
                    for (PenjualItem item : penjualItems){
                        penjualItem.add(item.getNama_karyawan());
                    }

                    // Creating adapter for spinner
                    ArrayAdapter<String> penjualAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, penjualItem);
                    penjualAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
                    spinPenjual.setAdapter(new NothingSelectedSpinnerAdapter(penjualAdapter, getActivity(), R.layout.spinner_text_nothing_selected, getString(R.string.hint_spinner_pilih_penjual)));

                }
            }

            @Override
            public void onFailure(Call<List<PenjualItem>> call, Throwable t) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });

        SharedPreferences produkPreferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
        SharedPreferences.Editor editorProduk = produkPreferences.edit();

        etCatatan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editorProduk.putString("catatan",etCatatan.getText().toString());
                editorProduk.apply();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editorProduk.putString("catatan",etCatatan.getText().toString());
                editorProduk.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                editorProduk.putString("catatan",etCatatan.getText().toString());
                editorProduk.apply();
            }
        });

        spinTokoTujuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = i - 1;
                if (index != -1) {
                    editorProduk.putInt("tokoTujuanId", tokoItems.get(index).getId());
                    editorProduk.putString("tokoTujuanName", tokoItems.get(index).getNamaToko());
                    editorProduk.apply();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                editorProduk.putInt("tokoTujuanId", 0);
                editorProduk.putString("tokoTujuanName", "");
                editorProduk.apply();
            }
        });

        spinPenjual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = i - 1;
                if (index != -1) {
                    editorProduk.putInt("penjualId", penjualItems.get(index).getId());
                    editorProduk.putString("penjualName", penjualItems.get(index).getNama_karyawan());
                    editorProduk.apply();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                editorProduk.putInt("penjualId", 0);
                editorProduk.putString("penjualName", "");
                editorProduk.apply();
            }
        });

        return v;
    }
}