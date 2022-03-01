package com.example.posrudyproject.ui.produk.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.penyimpanan.model.ProdukTersediaItem;
import com.example.posrudyproject.ui.produk.adapter.PilihProdukAdapter;
import com.example.posrudyproject.ui.produk.model.ProdukItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
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
    PenyimpananEndpoint penyimpananEndpoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_produk, container, false);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);
        //INIT VIEW
        btnClose = v.findViewById(R.id.btn_close_botsheet);
        searchView = v.findViewById(R.id.search_pilih_barang);
        rvListBarang = v.findViewById(R.id.rv_list_pilih_barang);
        rvListBarang.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvListBarang.setHasFixedSize(true);

        Bundle bundle = getArguments();

        Call<List<ProdukTersediaItem>> call_stock = penyimpananEndpoint.stockAvailPerStore(bundle.getString("token"), bundle.getInt("id_store"));
        //Produk Tersedia
        call_stock.enqueue(new Callback<List<ProdukTersediaItem>>() {
            @Override
            public void onResponse(Call<List<ProdukTersediaItem>> call, Response<List<ProdukTersediaItem>> response) {
                if (!response.isSuccessful()){
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(String.valueOf(response.code()))
                            .setContentText(response.message())
                            .show();
                }else{
                    produkItems = new ArrayList<>();
                    for (int i=0; i<response.body().size(); i++){
                        produkItems.add(new ProdukItem(
                                response.body().get(i).getFoto_barang(),
                                response.body().get(i).getTipeBarang(),
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
            }

            @Override
            public void onFailure(Call<List<ProdukTersediaItem>> call, Throwable t) {
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
        Toast.makeText(getActivity(), "Pilih " + produkItems.get(position).getNamaProduk(), Toast.LENGTH_SHORT).show();
    }
}