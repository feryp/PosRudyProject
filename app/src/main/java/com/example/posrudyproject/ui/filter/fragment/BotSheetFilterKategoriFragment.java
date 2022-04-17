package com.example.posrudyproject.ui.filter.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.KategoriEndpoint;
import com.example.posrudyproject.ui.filter.adapter.FilterKategoriAdapter;
import com.example.posrudyproject.ui.filter.adapter.FilterTipeAdapter;
import com.example.posrudyproject.ui.penjualan.activity.KategoriActivity;
import com.example.posrudyproject.ui.penjualan.adapter.KategoriAdapter;
import com.example.posrudyproject.ui.penjualan.model.KategoriItem;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BotSheetFilterKategoriFragment extends BottomSheetDialogFragment implements OnItemClickListener {

    RecyclerView rvKategoriBarang;
    AppCompatImageButton btnClose;
    SearchView searchView;
    FilterKategoriAdapter adapter;
    List<KategoriItem> kategoriItems;
    KategoriEndpoint kategoriEndpoint;
    public static final String INTENT_FILTER_KATEGORI = "INTENT_FILTER_KATEGORI";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_filter_kategori, container, false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        String auth_token = ("Bearer ").concat(token);
        kategoriEndpoint = ApiClient.getClient().create(KategoriEndpoint.class);
        kategoriItems = new ArrayList<>();
        //INIT VIEW
        rvKategoriBarang = v.findViewById(R.id.rv_kategori_botsheet);
        btnClose = v.findViewById(R.id.btn_close_botsheet);
        searchView = v.findViewById(R.id.search_filter_kategori);

        SetupSearchView(auth_token);

        Call<List<KategoriItem>> call = kategoriEndpoint.getAll(auth_token);
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<List<KategoriItem>>() {
            @Override
            public void onResponse(Call<List<KategoriItem>> call, Response<List<KategoriItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                }else{
                    pDialog.dismiss();
                    for (int i=0; i<response.body().size(); i++){
                        kategoriItems.add(new KategoriItem(
                                response.body().get(i).getId(),
                                response.body().get(i).getNamaKetegori()
                        ));
                    }
                    //Setup Adapter
                    adapter = new FilterKategoriAdapter(kategoriItems, BotSheetFilterKategoriFragment.this);
                    rvKategoriBarang.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvKategoriBarang.setAdapter(adapter);
                    rvKategoriBarang.setHasFixedSize(true);

                }
            }

            @Override
            public void onFailure(Call<List<KategoriItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(t.getMessage())
                        .show();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(getContext(), "Pilih " + kategoriItems.get(position).getNamaKetegori(), Toast.LENGTH_SHORT).show();
        //SELECT ITEM FILTER
        Intent someIntent = new Intent(INTENT_FILTER_KATEGORI);
        someIntent.putExtra("nama_kategori",kategoriItems.get(position).getNamaKetegori());
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(someIntent);
        dismiss();
    }

    private void SetupSearchView(String authToken){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<List<KategoriItem>> call = kategoriEndpoint.search(authToken,query);
                call.enqueue(new Callback<List<KategoriItem>>() {
                    @Override
                    public void onResponse(Call<List<KategoriItem>> call, Response<List<KategoriItem>> response) {
                        kategoriItems = new ArrayList<>();
                        for (int i=0; i<response.body().size(); i++){
                            kategoriItems.add(new KategoriItem(
                                            response.body().get(i).getId(),
                                            response.body().get(i).getNamaKetegori()
                                    )
                            );
                        }
                        //Setup Adapter
                        adapter = new FilterKategoriAdapter(kategoriItems, BotSheetFilterKategoriFragment.this);
                        rvKategoriBarang.setLayoutManager(new LinearLayoutManager(getContext()));
                        rvKategoriBarang.setAdapter(adapter);
                        rvKategoriBarang.setHasFixedSize(true);
                    }

                    @Override
                    public void onFailure(Call<List<KategoriItem>> call, Throwable t) {
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
                if (TextUtils.isEmpty(newText)){
                    Call<List<KategoriItem>> call = kategoriEndpoint.search(authToken,"");
                    call.enqueue(new Callback<List<KategoriItem>>() {
                        @Override
                        public void onResponse(Call<List<KategoriItem>> call, Response<List<KategoriItem>> response) {
                            kategoriItems = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                kategoriItems.add(new KategoriItem(
                                                response.body().get(i).getId(),
                                                response.body().get(i).getNamaKetegori()
                                        )
                                );
                            }
                            //Setup Adapter
                            adapter = new FilterKategoriAdapter(kategoriItems, BotSheetFilterKategoriFragment.this);
                            rvKategoriBarang.setLayoutManager(new LinearLayoutManager(getContext()));
                            rvKategoriBarang.setAdapter(adapter);
                            rvKategoriBarang.setHasFixedSize(true);
                        }

                        @Override
                        public void onFailure(Call<List<KategoriItem>> call, Throwable t) {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                } else {
                    Call<List<KategoriItem>> call = kategoriEndpoint.search(authToken,newText);
                    call.enqueue(new Callback<List<KategoriItem>>() {
                        @Override
                        public void onResponse(Call<List<KategoriItem>> call, Response<List<KategoriItem>> response) {
                            kategoriItems = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                kategoriItems.add(new KategoriItem(
                                                response.body().get(i).getId(),
                                                response.body().get(i).getNamaKetegori()
                                        )
                                );
                            }
                            //Setup Adapter
                            adapter = new FilterKategoriAdapter(kategoriItems, BotSheetFilterKategoriFragment.this);
                            rvKategoriBarang.setLayoutManager(new LinearLayoutManager(getContext()));
                            rvKategoriBarang.setAdapter(adapter);
                            rvKategoriBarang.setHasFixedSize(true);
                        }

                        @Override
                        public void onFailure(Call<List<KategoriItem>> call, Throwable t) {
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