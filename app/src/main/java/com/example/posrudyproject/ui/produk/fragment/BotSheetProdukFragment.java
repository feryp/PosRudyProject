package com.example.posrudyproject.ui.produk.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
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
import com.example.posrudyproject.ui.penyimpanan.adapter.PemindahanBarangAdapter;
import com.example.posrudyproject.ui.penyimpanan.fragment.PilihPemindahanBarangFragment;
import com.example.posrudyproject.ui.penyimpanan.model.PemindahanBarangItem;
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.example.posrudyproject.ui.produk.adapter.PilihProdukAdapter;
import com.example.posrudyproject.ui.produk.model.ProdukItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BotSheetProdukFragment extends BottomSheetDialogFragment implements OnItemClickListener {

    AppCompatImageButton btnClose;
    RecyclerView rvListBarang;
    SearchView searchView;
    List<ProdukItem> produkItems;
    PilihProdukAdapter adapter;
    PemindahanBarangAdapter pemindahanBarangAdapter;
    PenyimpananEndpoint penyimpananEndpoint;
    FragmentManager fragmentManager = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_produk, container, false);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);
        fragmentManager = getActivity().getSupportFragmentManager();
        //INIT VIEW
        btnClose = v.findViewById(R.id.btn_close_botsheet);
        searchView = v.findViewById(R.id.search_pilih_barang);
        rvListBarang = v.findViewById(R.id.rv_list_pilih_barang);
        rvListBarang.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvListBarang.setHasFixedSize(true);

        Bundle bundle = getArguments();
        int tipe = bundle.getInt("tipe",0);

        SetupSearchView(bundle.getString("token"), bundle.getInt("id_store"), tipe);

        if (tipe != 0) {
            Call<List<ProdukTersediaItem>> call_stock = penyimpananEndpoint.stockAvailPerStoreByType(bundle.getString("token"), bundle.getInt("id_store"), tipe);
            //Produk Tersedia
            call_stock.enqueue(new Callback<List<ProdukTersediaItem>>() {
                @Override
                public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                    if (!response.isSuccessful()){
                        new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(String.valueOf(response.code()))
                                .setContentText(response.message())
                                .show();
                    }else{
                        produkItems = new ArrayList<>();
                        for (int i=0; i<response.body().size(); i++){
                            produkItems.add(new ProdukItem(
                                    response.body().get(i).getFoto_barang(),
                                    response.body().get(i).getTipeBarang(),
                                    response.body().get(i).getSkuCode(),
                                    response.body().get(i).getArtikelBarang(),
                                    response.body().get(i).getNamaBarang(),
                                    response.body().get(i).getJumlahStok()
                            ));
                        }
                        adapter = new PilihProdukAdapter(produkItems, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(View view, int position) {
                                if (bundle.getString("for") == "PemindahanBarang") {
                                    List<PemindahanBarangItem> items = new ArrayList<>();
                                    boolean found = false;
                                    if (bundle.getSerializable("prevBarangPindah") != null) {
                                        for (int i=0; i < ((List<PemindahanBarangItem>)bundle.getSerializable("prevBarangPindah")).size(); i++) {
                                            items.add(new PemindahanBarangItem(
                                                    ((List<PemindahanBarangItem>)bundle.getSerializable("prevBarangPindah")).get(i).getFoto_barang(),
                                                    ((List<PemindahanBarangItem>)bundle.getSerializable("prevBarangPindah")).get(i).getTipeProduk(),
                                                    ((List<PemindahanBarangItem>)bundle.getSerializable("prevBarangPindah")).get(i).getSkuCode(),
                                                    ((List<PemindahanBarangItem>)bundle.getSerializable("prevBarangPindah")).get(i).getArtikelProduk(),
                                                    ((List<PemindahanBarangItem>)bundle.getSerializable("prevBarangPindah")).get(i).getNamaProduk(),
                                                    ((List<PemindahanBarangItem>)bundle.getSerializable("prevBarangPindah")).get(i).getJumlahProduk()
                                            ));
                                        }
                                        for (int j=0; j < ((List<PemindahanBarangItem>)bundle.getSerializable("prevBarangPindah")).size(); j++) {
                                            if (((List<PemindahanBarangItem>)bundle.getSerializable("prevBarangPindah")).get(j).getArtikelProduk().equals(produkItems.get(position).getArtikelProduk())){
                                                found = true;
                                            }
                                        }
                                        if (found) {
                                            Toast.makeText(getActivity(), "Barang tersebut sudah dipilih", Toast.LENGTH_SHORT).show();
                                        } else {
                                            items.add(new PemindahanBarangItem(
                                                    produkItems.get(position).getFoto_barang(),
                                                    produkItems.get(position).getTipeProduk(),
                                                    produkItems.get(position).getSkuCode(),
                                                    produkItems.get(position).getArtikelProduk(),
                                                    produkItems.get(position).getNamaProduk(),
                                                    "0"
                                            ));
                                            Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        items.add(new PemindahanBarangItem(
                                                produkItems.get(position).getFoto_barang(),
                                                produkItems.get(position).getTipeProduk(),
                                                produkItems.get(position).getSkuCode(),
                                                produkItems.get(position).getArtikelProduk(),
                                                produkItems.get(position).getNamaProduk(),
                                                "0"
                                        ));
                                        Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();
                                    }

                                    Bundle barangPindahBundle = new Bundle();
                                    barangPindahBundle.putSerializable("barangPindahItems", (Serializable) items);

                                    barangPindahBundle.putString("token", bundle.getString("token"));
                                    barangPindahBundle.putInt("id_store", bundle.getInt("id_store"));
                                    barangPindahBundle.putInt("tipe", tipe);
                                    PilihPemindahanBarangFragment pilihPemindahanBarangFragment = new PilihPemindahanBarangFragment();
                                    pilihPemindahanBarangFragment.setArguments(barangPindahBundle);
                                    fragmentManager.beginTransaction().
                                            setReorderingAllowed(true).
                                            replace(R.id.fragment_container_pemindahan_barang, pilihPemindahanBarangFragment, getTag())
                                            .commit();
                                    dismiss();
                                } else if (bundle.getString("for") == "CustomBarang1") {
                                    List<ProdukItem> items = new ArrayList<>();
                                    items.add(new ProdukItem(
                                            produkItems.get(position).getFoto_barang(),
                                            produkItems.get(position).getTipeProduk(),
                                            produkItems.get(position).getSkuCode(),
                                            produkItems.get(position).getArtikelProduk(),
                                            produkItems.get(position).getNamaProduk(),
                                            "0"
                                    ));
                                    Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();

                                    Bundle barangCustomBundle = new Bundle();
                                    barangCustomBundle.putSerializable("barangCustom1Items", (Serializable) items);
                                    if (bundle.getSerializable("items2") != null) {
                                        barangCustomBundle.putSerializable("barangCustom2Items", bundle.getSerializable("items2"));
                                    }
                                    PilihCustomBarangFragment pilihCustomBarangFragment = new PilihCustomBarangFragment();
                                    pilihCustomBarangFragment.setArguments(barangCustomBundle);
                                    fragmentManager.beginTransaction().
                                            setReorderingAllowed(true).
                                            replace(R.id.fragment_container_custom_barang, pilihCustomBarangFragment, getTag())
                                            .commit();
                                    dismiss();
                                } else if (bundle.getString("for") == "CustomBarang2") {
                                    List<ProdukItem> items = new ArrayList<>();
                                    items.add(new ProdukItem(
                                            produkItems.get(position).getFoto_barang(),
                                            produkItems.get(position).getTipeProduk(),
                                            produkItems.get(position).getSkuCode(),
                                            produkItems.get(position).getArtikelProduk(),
                                            produkItems.get(position).getNamaProduk(),
                                            "0"
                                    ));
                                    Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();

                                    Bundle barangCustomBundle = new Bundle();
                                    barangCustomBundle.putSerializable("barangCustom2Items", (Serializable) items);
                                    if (bundle.getSerializable("items1") != null) {
                                        barangCustomBundle.putSerializable("barangCustom1Items", bundle.getSerializable("items1"));
                                    }
                                    PilihCustomBarangFragment pilihCustomBarangFragment = new PilihCustomBarangFragment();
                                    pilihCustomBarangFragment.setArguments(barangCustomBundle);
                                    fragmentManager.beginTransaction().
                                            setReorderingAllowed(true).
                                            replace(R.id.fragment_container_custom_barang, pilihCustomBarangFragment, getTag())
                                            .commit();
                                    dismiss();
                                }
                            }
                        });
                        rvListBarang.setAdapter(adapter);
                    }
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
            Call<List<ProdukTersediaItem>> call_stock = penyimpananEndpoint.stockAvailPerStore(bundle.getString("token"), bundle.getInt("id_store"));
            //Produk Tersedia
            call_stock.enqueue(new Callback<List<ProdukTersediaItem>>() {
                @Override
                public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                    if (!response.isSuccessful()){
                        new SweetAlertDialog(getActivity().getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(String.valueOf(response.code()))
                                .setContentText(response.message())
                                .show();
                    }else{
                        produkItems = new ArrayList<>();
                        for (int i=0; i<response.body().size(); i++){
                            produkItems.add(new ProdukItem(
                                    response.body().get(i).getFoto_barang(),
                                    response.body().get(i).getTipeBarang(),
                                    response.body().get(i).getSkuCode(),
                                    response.body().get(i).getArtikelBarang(),
                                    response.body().get(i).getNamaBarang(),
                                    response.body().get(i).getJumlahStok()
                            ));
                        }
                        adapter = new PilihProdukAdapter(produkItems, new OnItemClickListener() {
                            @Override
                            public void onItemClickListener(View view, int position) {
                                Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();
                                if (bundle.getString("for") == "PemindahanBarang") {
                                    PilihPemindahanBarangFragment pilihPemindahanBarangFragment = new PilihPemindahanBarangFragment();
                                    Bundle bundlePemindahan = new Bundle();
                                    bundlePemindahan.putSerializable("barangPindahItems", (Serializable) produkItems.get(position));
                                    pilihPemindahanBarangFragment.setArguments(bundlePemindahan);

                                } else if (bundle.getString("for") == "CustomBarang1") {
                                    List<ProdukItem> items = new ArrayList<>();
                                    items.add(new ProdukItem(
                                            produkItems.get(position).getFoto_barang(),
                                            produkItems.get(position).getTipeProduk(),
                                            produkItems.get(position).getSkuCode(),
                                            produkItems.get(position).getArtikelProduk(),
                                            produkItems.get(position).getNamaProduk(),
                                            "0"
                                    ));
                                    Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();

                                    Bundle barangCustomBundle = new Bundle();
                                    barangCustomBundle.putSerializable("barangCustom1Items", (Serializable) items);
                                    if (bundle.getSerializable("items2") != null) {
                                        barangCustomBundle.putSerializable("barangCustom2Items", bundle.getSerializable("items2"));
                                    }
                                    PilihCustomBarangFragment pilihCustomBarangFragment = new PilihCustomBarangFragment();
                                    pilihCustomBarangFragment.setArguments(barangCustomBundle);
                                    fragmentManager.beginTransaction().
                                            setReorderingAllowed(true).
                                            replace(R.id.fragment_container_custom_barang, pilihCustomBarangFragment, getTag())
                                            .commit();
                                    dismiss();
                                } else if (bundle.getString("for") == "CustomBarang2") {
                                    List<ProdukItem> items = new ArrayList<>();
                                    items.add(new ProdukItem(
                                            produkItems.get(position).getFoto_barang(),
                                            produkItems.get(position).getTipeProduk(),
                                            produkItems.get(position).getSkuCode(),
                                            produkItems.get(position).getArtikelProduk(),
                                            produkItems.get(position).getNamaProduk(),
                                            "0"
                                    ));
                                    Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();

                                    Bundle barangCustomBundle = new Bundle();
                                    barangCustomBundle.putSerializable("barangCustom2Items", (Serializable) items);
                                    if (bundle.getSerializable("items1") != null) {
                                        barangCustomBundle.putSerializable("barangCustom1Items", bundle.getSerializable("items1"));
                                    }
                                    PilihCustomBarangFragment pilihCustomBarangFragment = new PilihCustomBarangFragment();
                                    pilihCustomBarangFragment.setArguments(barangCustomBundle);
                                    fragmentManager.beginTransaction().
                                            setReorderingAllowed(true).
                                            replace(R.id.fragment_container_custom_barang, pilihCustomBarangFragment, getTag())
                                            .commit();
                                    dismiss();
                                }
                            }
                        });
                        rvListBarang.setAdapter(adapter);
                    }
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
        Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();

    }

    private void SetupSearchView(String authToken, int id_store, int tipe){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (tipe != 0) {
                    Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchByType(authToken, id_store, tipe, query);
                    call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                        @Override
                        public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                            produkItems = new ArrayList<>();
                            for (int i = 0; i < response.body().size(); i++) {
                                produkItems.add(new ProdukItem(
                                        response.body().get(i).getFoto_barang(),
                                        response.body().get(i).getTipeBarang(),
                                        response.body().get(i).getSkuCode(),
                                        response.body().get(i).getArtikelBarang(),
                                        response.body().get(i).getNamaBarang(),
                                        response.body().get(i).getJumlahStok()
                                ));
                            }
                            adapter = new PilihProdukAdapter(produkItems, new OnItemClickListener() {
                                @Override
                                public void onItemClickListener(View view, int position) {
                                    Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            rvListBarang.setAdapter(adapter);
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
                    Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchStockStore(authToken, id_store, query);
                    call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                        @Override
                        public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                            produkItems = new ArrayList<>();
                            for (int i=0; i<response.body().size(); i++){
                                produkItems.add(new ProdukItem(
                                        response.body().get(i).getFoto_barang(),
                                        response.body().get(i).getTipeBarang(),
                                        response.body().get(i).getSkuCode(),
                                        response.body().get(i).getArtikelBarang(),
                                        response.body().get(i).getNamaBarang(),
                                        response.body().get(i).getJumlahStok()
                                ));
                            }
                            adapter = new PilihProdukAdapter(produkItems, new OnItemClickListener() {
                                @Override
                                public void onItemClickListener(View view, int position) {
                                    Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            rvListBarang.setAdapter(adapter);
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

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    if (tipe != 0) {
                        Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchByType(authToken, id_store, tipe, "");
                        call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                            @Override
                            public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                                produkItems = new ArrayList<>();
                                for (int i = 0; i < response.body().size(); i++) {
                                    produkItems.add(new ProdukItem(
                                            response.body().get(i).getFoto_barang(),
                                            response.body().get(i).getTipeBarang(),
                                            response.body().get(i).getSkuCode(),
                                            response.body().get(i).getArtikelBarang(),
                                            response.body().get(i).getNamaBarang(),
                                            response.body().get(i).getJumlahStok()
                                    ));
                                }
                                adapter = new PilihProdukAdapter(produkItems, new OnItemClickListener() {
                                    @Override
                                    public void onItemClickListener(View view, int position) {
                                        Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                rvListBarang.setAdapter(adapter);
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
                        Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchStockStore(authToken, id_store, "");
                        call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                            @Override
                            public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                                produkItems = new ArrayList<>();
                                for (int i=0; i<response.body().size(); i++){
                                    produkItems.add(new ProdukItem(
                                            response.body().get(i).getFoto_barang(),
                                            response.body().get(i).getTipeBarang(),
                                            response.body().get(i).getSkuCode(),
                                            response.body().get(i).getArtikelBarang(),
                                            response.body().get(i).getNamaBarang(),
                                            response.body().get(i).getJumlahStok()
                                    ));
                                }
                                adapter = new PilihProdukAdapter(produkItems, new OnItemClickListener() {
                                    @Override
                                    public void onItemClickListener(View view, int position) {
                                        Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                rvListBarang.setAdapter(adapter);
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
                } else {
                    if (tipe != 0) {
                        Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchByType(authToken, id_store, tipe, newText);
                        call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                            @Override
                            public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                                produkItems = new ArrayList<>();
                                for (int i = 0; i < response.body().size(); i++) {
                                    produkItems.add(new ProdukItem(
                                            response.body().get(i).getFoto_barang(),
                                            response.body().get(i).getTipeBarang(),
                                            response.body().get(i).getSkuCode(),
                                            response.body().get(i).getArtikelBarang(),
                                            response.body().get(i).getNamaBarang(),
                                            response.body().get(i).getJumlahStok()
                                    ));
                                }
                                adapter = new PilihProdukAdapter(produkItems, new OnItemClickListener() {
                                    @Override
                                    public void onItemClickListener(View view, int position) {
                                        Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                rvListBarang.setAdapter(adapter);
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
                        Call<List<ProdukTersediaItem>> call = penyimpananEndpoint.searchStockStore(authToken, id_store, newText);
                        call.enqueue(new Callback<List<ProdukTersediaItem>>() {
                            @Override
                            public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                                produkItems = new ArrayList<>();
                                for (int i=0; i<response.body().size(); i++){
                                    produkItems.add(new ProdukItem(
                                            response.body().get(i).getFoto_barang(),
                                            response.body().get(i).getTipeBarang(),
                                            response.body().get(i).getSkuCode(),
                                            response.body().get(i).getArtikelBarang(),
                                            response.body().get(i).getNamaBarang(),
                                            response.body().get(i).getJumlahStok()
                                    ));
                                }
                                adapter = new PilihProdukAdapter(produkItems, new OnItemClickListener() {
                                    @Override
                                    public void onItemClickListener(View view, int position) {
                                        Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                rvListBarang.setAdapter(adapter);
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
                }
                return false;
            }
        });
    }
}