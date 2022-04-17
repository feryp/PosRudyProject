package com.example.posrudyproject.ui.produk.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

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
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.example.posrudyproject.ui.produk.adapter.PilihProdukAdapter;
import com.example.posrudyproject.ui.produk.model.ProdukItem;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.Serializable;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PilihCustomBarangFragment extends Fragment  implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    AppCompatSpinner spinTipe;
    ConstraintLayout layoutItemProduk1, layoutItemProduk2;
    ConstraintLayout btnPilihProduk1, btnPilihProduk2;
    RoundedImageView imProduk1, imProduk2;
    AppCompatTextView tipeProduk1, artikelProduk1, namaProduk1, lensaProduk1, frameProduk1;
    AppCompatTextView tipeProduk2, artikelProduk2, namaProduk2, lensaProduk2, frameProduk2;
    PenyimpananEndpoint penyimpananEndpoint;
    List<ProdukItem> produkItems1,produkItems2;
    PilihProdukAdapter adapter;
    String auth_token;
    int id_store;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pilih_custom_barang, container, false);
        penyimpananEndpoint = ApiClient.getClient().create(PenyimpananEndpoint.class);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token","");
        auth_token = ("Bearer ").concat(token);
        id_store = preferences.getInt("id_store", 0);

        //INIT VIEW
        spinTipe = v.findViewById(R.id.spinner_tipe_custom_barang);
        layoutItemProduk1 = v.findViewById(R.id.layout_item_produk_1);
        layoutItemProduk2 = v.findViewById(R.id.layout_item_produk_2);
        btnPilihProduk1 = v.findViewById(R.id.btn_pilih_produk_custom_1);
        btnPilihProduk2 = v.findViewById(R.id.btn_pilih_produk_custom_2);
        imProduk1 = v.findViewById(R.id.im_produk_custom_1);
        imProduk2 = v.findViewById(R.id.im_produk_custom_2);
        tipeProduk1 = v.findViewById(R.id.tv_tipe_custom_1);
        tipeProduk2 = v.findViewById(R.id.tv_tipe_custom_2);
        artikelProduk1 = v.findViewById(R.id.tv_artikel_custom_1);
        artikelProduk2 = v.findViewById(R.id.tv_artikel_custom_2);
        namaProduk1 = v.findViewById(R.id.tv_nama_barang_custom_1);
        namaProduk2 = v.findViewById(R.id.tv_nama_barang_custom_2);
        lensaProduk1 = v.findViewById(R.id.tv_produk_lensa_custom_1);
        lensaProduk2 = v.findViewById(R.id.tv_produk_lensa_custom_2);
        frameProduk1 = v.findViewById(R.id.tv_produk_frame_custom_1);
        frameProduk2 = v.findViewById(R.id.tv_produk_frame_custom_2);
        int tipePrev = (int) (spinTipe.getSelectedItemId() + 1);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getSerializable("barangCustom1Items") != null){
                layoutItemProduk1.setVisibility(View.VISIBLE);
                btnPilihProduk1.setVisibility(View.GONE);
                produkItems1 = ((List<ProdukItem>)bundle.getSerializable("barangCustom1Items"));
                imProduk1.setImageResource(0);
                tipeProduk1.setText(((List<ProdukItem>)bundle.getSerializable("barangCustom1Items")).get(0).getTipeProduk());
                artikelProduk1.setText(((List<ProdukItem>)bundle.getSerializable("barangCustom1Items")).get(0).getArtikelProduk());
                namaProduk1.setText(((List<ProdukItem>)bundle.getSerializable("barangCustom1Items")).get(0).getNamaProduk());

            }

            if (bundle.getSerializable("barangCustom2Items") != null){
                layoutItemProduk2.setVisibility(View.VISIBLE);
                btnPilihProduk2.setVisibility(View.GONE);
                produkItems2 = ((List<ProdukItem>)bundle.getSerializable("barangCustom2Items"));
                imProduk2.setImageResource(0);
                tipeProduk2.setText(((List<ProdukItem>)bundle.getSerializable("barangCustom2Items")).get(0).getTipeProduk());
                artikelProduk2.setText(((List<ProdukItem>)bundle.getSerializable("barangCustom2Items")).get(0).getArtikelProduk());
                namaProduk2.setText(((List<ProdukItem>)bundle.getSerializable("barangCustom2Items")).get(0).getNamaProduk());
            }

        }

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

            }

            @Override
            public void onFailure(Call<List<TipeItem>> call, Throwable t) {
                List<TipeItem> tipeItems = new ArrayList<>();
                tipeItems.add(new TipeItem(0,"Master Tipe Kosong"));
            }
        });

        //SET LISTENER
        btnPilihProduk1.setOnClickListener(PilihCustomBarangFragment.this);
        btnPilihProduk2.setOnClickListener(PilihCustomBarangFragment.this);

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
        Bundle bundle = new Bundle();
        bundle.putString("token", auth_token);
        bundle.putInt("id_store",id_store);

        if (view == btnPilihProduk1){
            if (artikelProduk1.getText().toString() != ""){
                bundle.putSerializable("items1",(Serializable) produkItems1);
            }

            if (artikelProduk2.getText().toString() != ""){
                bundle.putSerializable("items2",(Serializable) produkItems2);
            }
            BotSheetProdukFragment botSheetProduk = new BotSheetProdukFragment();
            bundle.putString("for", "CustomBarang1");
            botSheetProduk.setArguments(bundle);
            botSheetProduk.setCancelable(false);
            botSheetProduk.show(getChildFragmentManager(), botSheetProduk.getTag());

        } else if (view == btnPilihProduk2){
            if (artikelProduk1.getText().toString() != ""){
                bundle.putSerializable("items1",(Serializable) produkItems1);
            }

            if (artikelProduk2.getText().toString() != ""){
                bundle.putSerializable("items2",(Serializable) produkItems2);
            }
            BotSheetProdukFragment botSheetProduk = new BotSheetProdukFragment();
            bundle.putString("for", "CustomBarang2");
            botSheetProduk.setArguments(bundle);
            botSheetProduk.setCancelable(false);
            botSheetProduk.show(getChildFragmentManager(), botSheetProduk.getTag());
        }
    }
}