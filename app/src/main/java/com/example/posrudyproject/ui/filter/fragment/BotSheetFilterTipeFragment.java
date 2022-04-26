package com.example.posrudyproject.ui.filter.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.filter.adapter.FilterTipeAdapter;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BotSheetFilterTipeFragment extends BottomSheetDialogFragment implements OnItemClickListener {

    RecyclerView rvTipeBarang;
    AppCompatImageButton btnClose;
    SearchView searchView;
    FilterTipeAdapter adapter;
    List<TipeItem> tipeItems;
    PenyimpananEndpoint penyimpananEndpoint;
    public static final String INTENT_FILTER_TIPE = "INTENT_FILTER_TIPE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_filter_tipe, container, false);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        String auth_token = ("Bearer ").concat(token);

        searchView = v.findViewById(R.id.search_filter_tipe);
        tipeItems = new ArrayList<>();

        SetupSearchView(auth_token);
        //INIT VIEW
        rvTipeBarang = v.findViewById(R.id.rv_tipe_botsheet);
        btnClose = v.findViewById(R.id.btn_close_botsheet);


        Call<List<TipeItem>> call = penyimpananEndpoint.getAllTipe(auth_token);
        call.enqueue(new Callback<List<TipeItem>>() {
            @Override
            public void onResponse(Call<List<TipeItem>> call, Response<List<TipeItem>> response) {
                if (!response.isSuccessful()){
                    tipeItems.add(new TipeItem(0,"Master Tipe Kosong"));
                }else{
                    for (int i=0; i < response.body().size(); i++){
                        tipeItems.add(new TipeItem(
                                response.body().get(i).getId(),
                                response.body().get(i).getNamaTipe()
                        ));
                    }
                    //Setup Adapter
                    adapter = new FilterTipeAdapter(tipeItems, BotSheetFilterTipeFragment.this);
                    rvTipeBarang.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    rvTipeBarang.setAdapter(adapter);
                    rvTipeBarang.setHasFixedSize(true);
                }

            }

            @Override
            public void onFailure(Call<List<TipeItem>> call, Throwable t) {
                List<TipeItem> tipeItems = new ArrayList<>();
                tipeItems.add(new TipeItem(0,"Master Tipe Kosong"));
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
        Toast.makeText(getActivity().getApplicationContext(), "Pilih " + tipeItems.get(position).getNamaTipe(), Toast.LENGTH_SHORT).show();
        Intent someIntent = new Intent(INTENT_FILTER_TIPE);
        someIntent.putExtra("nama_tipe",tipeItems.get(position).getNamaTipe());
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).sendBroadcast(someIntent);
        dismiss();
        //SELECT ITEM FILTER
    }

    private void SetupSearchView(String authToken){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<List<TipeItem>> call = penyimpananEndpoint.searchTipe(authToken, query);
                call.enqueue(new Callback<List<TipeItem>>() {
                    @Override
                    public void onResponse(Call<List<TipeItem>> call, Response<List<TipeItem>> response) {
                        tipeItems = new ArrayList<>();
                        for (int i=0; i < response.body().size(); i++){
                            tipeItems.add(new TipeItem(
                                    response.body().get(i).getId(),
                                    response.body().get(i).getNamaTipe()
                            ));
                        }
                        //Setup Adapter
                        adapter = new FilterTipeAdapter(tipeItems, BotSheetFilterTipeFragment.this);
                        rvTipeBarang.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                        rvTipeBarang.setAdapter(adapter);
                        rvTipeBarang.setHasFixedSize(true);
                    }

                    @Override
                    public void onFailure(Call<List<TipeItem>> call, Throwable t) {
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
                if (TextUtils.isEmpty(newText)) {
                    Call<List<TipeItem>> call = penyimpananEndpoint.searchTipe(authToken, "");
                    call.enqueue(new Callback<List<TipeItem>>() {
                        @Override
                        public void onResponse(Call<List<TipeItem>> call, Response<List<TipeItem>> response) {
                            tipeItems = new ArrayList<>();
                            for (int i=0; i < response.body().size(); i++){
                                tipeItems.add(new TipeItem(
                                        response.body().get(i).getId(),
                                        response.body().get(i).getNamaTipe()
                                ));
                            }
                            //Setup Adapter
                            adapter = new FilterTipeAdapter(tipeItems, BotSheetFilterTipeFragment.this);
                            rvTipeBarang.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                            rvTipeBarang.setAdapter(adapter);
                            rvTipeBarang.setHasFixedSize(true);
                        }

                        @Override
                        public void onFailure(Call<List<TipeItem>> call, Throwable t) {
                            new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(t.getMessage())
                                    .show();
                        }
                    });
                } else {
                    Call<List<TipeItem>> call = penyimpananEndpoint.searchTipe(authToken, newText);
                    call.enqueue(new Callback<List<TipeItem>>() {
                        @Override
                        public void onResponse(Call<List<TipeItem>> call, Response<List<TipeItem>> response) {
                            tipeItems = new ArrayList<>();
                            for (int i=0; i < response.body().size(); i++){
                                tipeItems.add(new TipeItem(
                                        response.body().get(i).getId(),
                                        response.body().get(i).getNamaTipe()
                                ));
                            }
                            //Setup Adapter
                            adapter = new FilterTipeAdapter(tipeItems, BotSheetFilterTipeFragment.this);
                            rvTipeBarang.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                            rvTipeBarang.setAdapter(adapter);
                            rvTipeBarang.setHasFixedSize(true);
                        }

                        @Override
                        public void onFailure(Call<List<TipeItem>> call, Throwable t) {
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