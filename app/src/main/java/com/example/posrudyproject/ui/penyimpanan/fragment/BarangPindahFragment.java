package com.example.posrudyproject.ui.penyimpanan.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.penyimpanan.activity.DetailRiwayatActivity;
import com.example.posrudyproject.ui.penyimpanan.adapter.DokumenBarangPindahAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.DokumenBarangPindahItem;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangPindahFragment extends Fragment implements OnItemClickListener {

    ConstraintLayout layoutEmpty;
    SearchView cariBarang;
    RecyclerView rvBarangPindah;
    DokumenBarangPindahAdapter adapter;
    List<DokumenBarangPindahItem> dokumenBarangPindahItems;
    PenyimpananEndpoint penyimpananEndpoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_barang_pindah, container, false);

        //INIT VIEW
        layoutEmpty = v.findViewById(R.id.layout_ilustrasi_empty_barang_pindah);
        cariBarang = v.findViewById(R.id.search_barang_pindah);
        rvBarangPindah = v.findViewById(R.id.rv_barang_pindah);


        //Barang Keluar List
        Bundle bundle = getArguments();
        dokumenBarangPindahItems = (List<DokumenBarangPindahItem>) bundle.getSerializable("dokumenBarangPindahItems");
        SetupSearchView((SearchView) v.findViewById(R.id.search_barang_pindah),bundle.getString("authToken"),bundle.getInt("idStore"));
        //Setup adapter
        adapter = new DokumenBarangPindahAdapter(dokumenBarangPindahItems, this);
        rvBarangPindah.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBarangPindah.setAdapter(adapter);
        rvBarangPindah.setHasFixedSize(true);

        //Jika ada list item ilustrasi hilang
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
                Call<Map> call = penyimpananEndpoint.searchBarangPindah(authToken, id_store, query);
                call.enqueue(new Callback<Map>() {
                    @Override
                    public void onResponse(Call<Map> call, Response<Map> response) {

                        //Setup adapter
                        adapter = new DokumenBarangPindahAdapter((List<DokumenBarangPindahItem>) response.body().get("result"), BarangPindahFragment.this);
                        rvBarangPindah.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvBarangPindah.setAdapter(adapter);
                        rvBarangPindah.setHasFixedSize(true);
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
                    Call<Map> call = penyimpananEndpoint.searchBarangPindah(authToken, id_store, "");
                    call.enqueue(new Callback<Map>() {
                        @Override
                        public void onResponse(Call<Map> call, Response<Map> response) {

                            //Setup adapter
                            adapter = new DokumenBarangPindahAdapter((List<DokumenBarangPindahItem>) response.body().get("result"), BarangPindahFragment.this);
                            rvBarangPindah.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvBarangPindah.setAdapter(adapter);
                            rvBarangPindah.setHasFixedSize(true);
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
                    Call<Map> call = penyimpananEndpoint.searchBarangPindah(authToken, id_store, newText);
                    call.enqueue(new Callback<Map>() {
                        @Override
                        public void onResponse(Call<Map> call, Response<Map> response) {

                            //Setup adapter
                            adapter = new DokumenBarangPindahAdapter((List<DokumenBarangPindahItem>) response.body().get("result"), BarangPindahFragment.this);
                            rvBarangPindah.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvBarangPindah.setAdapter(adapter);
                            rvBarangPindah.setHasFixedSize(true);
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

    @Override
    public void onItemClickListener(View view, int position) {
        Object item = dokumenBarangPindahItems.get(position);
        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) item;
        Toast.makeText(getContext(), "Pilih " + t.get("pengiriman_code"), Toast.LENGTH_SHORT).show();
        Intent detailRiwayat = new Intent(getActivity(), DetailRiwayatActivity.class);
        detailRiwayat.putExtra("noDocBarang", t.get("pengiriman_code").toString());

        detailRiwayat.putExtra("tanggal_pengiriman", t.get("tanggal_pengiriman").toString());
        detailRiwayat.putExtra("lokasi_store_tujuan", t.get("lokasi_store_tujuan").toString());
        detailRiwayat.putExtra("keterangan", t.get("keterangan").toString());
        detailRiwayat.putExtra("nama_karyawan", t.get("nama_karyawan").toString());

        detailRiwayat.putParcelableArrayListExtra("detail", (ArrayList<? extends Parcelable>) t.get("detailPengirimanList"));
        startActivity(detailRiwayat);
    }
}