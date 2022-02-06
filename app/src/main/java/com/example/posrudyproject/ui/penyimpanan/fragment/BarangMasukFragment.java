package com.example.posrudyproject.ui.penyimpanan.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.penyimpanan.adapter.BarangMasukAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.BarangMasukItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangMasukFragment extends Fragment {

    ConstraintLayout layoutEmpty;
    SearchView cariBarang;
    RecyclerView rvBarangMasuk;
    BarangMasukAdapter adapter;
    List<BarangMasukItem> barangMasukItems;
    PenyimpananEndpoint penyimpananEndpoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_barang_masuk, container, false);

        //INIT VIEW
        layoutEmpty = v.findViewById(R.id.layout_ilustrasi_empty_barang_masuk);
        rvBarangMasuk = v.findViewById(R.id.rv_barang_masuk);

        //Barang Masuk List
        Bundle bundle = getArguments();

        barangMasukItems = (List<BarangMasukItem>) bundle.getSerializable("barangMasukItems");

        SetupSearchView((SearchView) v.findViewById(R.id.search_barang_masuk),bundle.getString("authToken"),bundle.getInt("idStore"));

        //Setup adapter
        adapter = new BarangMasukAdapter(barangMasukItems, getActivity());
        rvBarangMasuk.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBarangMasuk.setAdapter(adapter);
        rvBarangMasuk.setHasFixedSize(true);

        //Jikaada list item ilustrasi hilang
        if (adapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
        }

        return v;
    }

    private void SetupSearchView(SearchView searchView,String authToken, int id_store){
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<Map> call = penyimpananEndpoint.searchBarangMasuk(authToken, id_store, query);
                call.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {

                        //Setup adapter
                        adapter = new BarangMasukAdapter((List<BarangMasukItem>) response.body().get("result"), getActivity());
                        rvBarangMasuk.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvBarangMasuk.setAdapter(adapter);
                        rvBarangMasuk.setHasFixedSize(true);
                        //Jikaada list item ilustrasi hilang
                        if (adapter.getItemCount() > 0){
                            layoutEmpty.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Map> call, Throwable t) {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(t.getMessage())
                                .show();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    Call<Map> call = penyimpananEndpoint.searchBarangMasuk(authToken, id_store, "");
                    call.enqueue(new Callback<Map>() {
                        @Override
                        public void onResponse(Call<Map> call, Response<Map> response) {

                            //Setup adapter
                            adapter = new BarangMasukAdapter((List<BarangMasukItem>) response.body().get("result"), getActivity());
                            rvBarangMasuk.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvBarangMasuk.setAdapter(adapter);
                            rvBarangMasuk.setHasFixedSize(true);
                            //Jikaada list item ilustrasi hilang
                            if (adapter.getItemCount() > 0){
                                layoutEmpty.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Map> call, Throwable t) {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                } else {
                    Call<Map> call = penyimpananEndpoint.searchBarangMasuk(authToken, id_store, newText);
                    call.enqueue(new Callback<Map>() {
                        @Override
                        public void onResponse(Call<Map> call, Response<Map> response) {

                            //Setup adapter
                            adapter = new BarangMasukAdapter((List<BarangMasukItem>) response.body().get("result"), getActivity());
                            rvBarangMasuk.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvBarangMasuk.setAdapter(adapter);
                            rvBarangMasuk.setHasFixedSize(true);
                            //Jikaada list item ilustrasi hilang
                            if (adapter.getItemCount() > 0){
                                layoutEmpty.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Map> call, Throwable t) {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                }
                return false;
            }
        });
    }
}