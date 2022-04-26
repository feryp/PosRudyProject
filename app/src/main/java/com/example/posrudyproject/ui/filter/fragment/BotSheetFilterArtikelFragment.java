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
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.filter.adapter.FilterArtikelAdapter;
import com.example.posrudyproject.ui.filter.adapter.FilterTipeAdapter;
import com.example.posrudyproject.ui.penjualan.model.ArtikelItem;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.example.posrudyproject.ui.produk.model.ProdukItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BotSheetFilterArtikelFragment extends BottomSheetDialogFragment implements OnItemClickListener {

    RecyclerView rvArtikelBarang;
    AppCompatImageButton btnClose;
    SearchView searchView;
    FilterArtikelAdapter adapter;
    List<ArtikelItem> artikelItem;
    public static final String INTENT_FILTER_ARTIKEL = "INTENT_FILTER_ARTIKEL";
    PenyimpananEndpoint penyimpananEndpoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_filter_artikel, container, false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        int id_store = preferences.getInt("id_store", 0);
        String auth_token = ("Bearer ").concat(token);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);

        //INIT VIEW
        rvArtikelBarang = v.findViewById(R.id.rv_artikel_botsheet);
        btnClose = v.findViewById(R.id.btn_close_botsheet);
        searchView = v.findViewById(R.id.search_filter_artikel);

        SetupSearchView(auth_token,id_store);

        //Tipe List
        artikelItem = new ArrayList<>();

        Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.stockAllItemPerStore(auth_token,id_store);
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        call.enqueue(new Callback<List<ProdukTersediaItem>>() {
            @Override
            public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                if (!response.isSuccessful()){
                    pDialog.dismiss();
                    new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                }else{
                    pDialog.dismiss();
                    for (int i=0; i<response.body().size(); i++){
                        artikelItem.add(new ArtikelItem(response.body().get(i).getArtikelBarang()));
                    }

                    //Setup Adapter
                    adapter = new FilterArtikelAdapter(artikelItem, BotSheetFilterArtikelFragment.this);
                    rvArtikelBarang.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    rvArtikelBarang.setAdapter(adapter);
                    rvArtikelBarang.setHasFixedSize(true);

                }
            }

            @Override
            public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
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
        Toast.makeText(getActivity().getApplicationContext(), "Pilih " + artikelItem.get(position).getNamaArtikel(), Toast.LENGTH_SHORT).show();
        //SELECT ITEM FILTER
        Intent someIntent = new Intent(INTENT_FILTER_ARTIKEL);
        someIntent.putExtra("artikel",artikelItem.get(position).getNamaArtikel());
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).sendBroadcast(someIntent);
        dismiss();
    }

    private void SetupSearchView(String authToken, int id_store){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchAllStock(authToken,id_store, query);
                call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                    @Override
                    public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                        artikelItem = new ArrayList<>();
                        for (int i=0; i<response.body().size(); i++){
                            artikelItem.add(new ArtikelItem(response.body().get(i).getArtikelBarang()));
                        }

                        //Setup Adapter
                        adapter = new FilterArtikelAdapter(artikelItem, BotSheetFilterArtikelFragment.this);
                        rvArtikelBarang.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                        rvArtikelBarang.setAdapter(adapter);
                        rvArtikelBarang.setHasFixedSize(true);
                    }

                    @Override
                    public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                        new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
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
                    Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchAllStock(authToken,id_store,"");
                    call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                        @Override
                        public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                            artikelItem = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                artikelItem.add(new ArtikelItem(response.body().get(i).getArtikelBarang()));
                            }

                            //Setup Adapter
                            adapter = new FilterArtikelAdapter(artikelItem, BotSheetFilterArtikelFragment.this);
                            rvArtikelBarang.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                            rvArtikelBarang.setAdapter(adapter);
                            rvArtikelBarang.setHasFixedSize(true);
                        }

                        @Override
                        public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                            new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                } else {
                    Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchAllStock(authToken,id_store,newText);
                    call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                        @Override
                        public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                            artikelItem = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                artikelItem.add(new ArtikelItem(response.body().get(i).getArtikelBarang()));
                            }

                            //Setup Adapter
                            adapter = new FilterArtikelAdapter(artikelItem, BotSheetFilterArtikelFragment.this);
                            rvArtikelBarang.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                            rvArtikelBarang.setAdapter(adapter);
                            rvArtikelBarang.setHasFixedSize(true);
                        }

                        @Override
                        public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
                            new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
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