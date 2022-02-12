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
import com.example.posrudyproject.ui.filter.adapter.NothingSelectedSpinnerAdapter;
import com.example.posrudyproject.ui.keranjang.adapter.KeranjangAdapter;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.example.posrudyproject.ui.penyimpanan.adapter.PemindahanBarangAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.PemindahanBarangItem;
import com.example.posrudyproject.ui.produk.fragment.BotSheetProdukFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class PilihPemindahanBarangFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    AppCompatSpinner spinTipe;
    MaterialButton btnPilihBarang;
    RecyclerView rvListBarang;
    ConstraintLayout layoutEmpty;

    List<PemindahanBarangItem> pemindahanBarangItems;
    PemindahanBarangAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pilih_pemindahan_barang, container, false);

        //INIT VIEW
        spinTipe = v.findViewById(R.id.spinner_tipe_pemindahan_barang);
        btnPilihBarang = v.findViewById(R.id.btn_pilih_barang_pemindahan);
        rvListBarang = v.findViewById(R.id.rv_list_pemindahan_barang);
        layoutEmpty = v.findViewById(R.id.layout_ilustrasi_empty_pemindahan_barang);

        //SPINNER TIPE
        // Spinner Drop down elements
        List<TipeItem> tipeItems = new ArrayList<>();
        tipeItems.add(new TipeItem("CUTLINE"));
        tipeItems.add(new TipeItem("BOOST PRO"));
        tipeItems.add(new TipeItem("DEFENDER"));
        tipeItems.add(new TipeItem("KEYBLADE"));
        tipeItems.add(new TipeItem("RYDON"));
        tipeItems.add(new TipeItem("SINTRYX"));
        tipeItems.add(new TipeItem("TRALYX"));

        //Update object to string
        List<String> tipeItem = new ArrayList<String>();
        for (TipeItem item : tipeItems){
            tipeItem.add(item.getNamaTipe());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> tipeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, tipeItem);
        tipeAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        spinTipe.setAdapter(new NothingSelectedSpinnerAdapter(tipeAdapter, getActivity(), R.layout.spinner_text_nothing_selected, getString(R.string.hint_spinner_pilih_tipe)));


        //Barang List;
        pemindahanBarangItems = new ArrayList<>();
        for (int i=0; i<10; i++){
            pemindahanBarangItems.add(new PemindahanBarangItem(
                    R.drawable.im_example,
                    "CUTLINE",
                    "SP633846-0011",
                    "Mandarin Fade/Coral Matte - RP Optics Multilaser Red",
                    "50"

            ));
        }

        //Setup Adapter
        adapter = new PemindahanBarangAdapter(pemindahanBarangItems, getActivity());
        rvListBarang.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvListBarang.setAdapter(adapter);
        rvListBarang.setHasFixedSize(true);

        //Jika ada list item ilustrasi hilang
        if (adapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
        }

        //SET LISTENER
        btnPilihBarang.setOnClickListener(PilihPemindahanBarangFragment.this);

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
        BotSheetProdukFragment botSheetProduk = new BotSheetProdukFragment();
        botSheetProduk.setCancelable(false);
        botSheetProduk.show(getChildFragmentManager(), botSheetProduk.getTag());
    }
}