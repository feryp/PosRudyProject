package com.example.posrudyproject.ui.penyimpanan.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.retrofit.ApiClient;
import com.example.posrudyproject.retrofit.PenyimpananEndpoint;
import com.example.posrudyproject.ui.filter.adapter.NothingSelectedSpinnerAdapter;
import com.example.posrudyproject.ui.keranjang.adapter.KeranjangAdapter;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.example.posrudyproject.ui.penyimpanan.adapter.PemindahanBarangAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.PemindahanBarangItem;
import com.example.posrudyproject.ui.produk.fragment.BotSheetProdukFragment;
import com.google.android.material.button.MaterialButton;

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
        Bundle bundle = getArguments();
        String token = bundle.getString("token");
        int id_store = bundle.getInt("id_store");

        //INIT VIEW
        spinTipe = v.findViewById(R.id.spinner_tipe_pemindahan_barang);
        btnPilihBarang = v.findViewById(R.id.btn_pilih_barang_pemindahan);
        rvListBarang = v.findViewById(R.id.rv_list_pemindahan_barang);
        layoutEmpty = v.findViewById(R.id.layout_ilustrasi_empty_pemindahan_barang);
        int tipePrev = (int) (spinTipe.getSelectedItemId() + 1);
        //SPINNER TIPE
        // Spinner Drop down elements
        Call<List<TipeItem>> call = penyimpananEndpoint.getAllTipe(token);
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
                if (((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")) != null) {
                    spinTipe.setSelection(bundle.getInt("tipe"));
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
        if (((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")) != null) {

            for (int i=0; i< ((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")).size(); i++){
                pemindahanBarangItems.add(new PemindahanBarangItem(
                        ((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")).get(i).getFoto_barang(),
                        ((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")).get(i).getTipeProduk(),
                        ((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")).get(i).getSkuCode(),
                        ((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")).get(i).getArtikelProduk(),
                        ((List<PemindahanBarangItem>)bundle.getSerializable("barangPindahItems")).get(i).getNamaProduk(),
                        "0"

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
                if (tipe != tipePrev && tipe > 0) {
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
                            bundle.putString("token", token);
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
                            sweetAlertDialog.dismiss();
                        }
                    });
                    sweetAlertDialog.show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("tipe", tipe);
                    bundle.putString("token", token);
                    bundle.putInt("id_store", id_store);
                    bundle.putString("for", "PemindahanBarang");
                    BotSheetProdukFragment botSheetProduk = new BotSheetProdukFragment();
                    botSheetProduk.setCancelable(false);
                    botSheetProduk.setArguments(bundle);
                    botSheetProduk.show(getChildFragmentManager(), botSheetProduk.getTag());
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