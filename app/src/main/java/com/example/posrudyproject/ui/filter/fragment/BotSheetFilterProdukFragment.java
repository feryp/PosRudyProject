package com.example.posrudyproject.ui.filter.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.filter.adapter.NothingSelectedSpinnerAdapter;
import com.example.posrudyproject.ui.penjualan.model.ArtikelItem;
import com.example.posrudyproject.ui.penjualan.model.KategoriItem;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public class BotSheetFilterProdukFragment extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    AppCompatImageButton btnClose;
    AppCompatSpinner spinKategori, spinTipe, spinArtikel;
    MaterialButton btnBersihkan, btnTerapkan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bot_sheet_filter_produk, container, false);

        //INIT VIEW
        btnClose = v.findViewById(R.id.btn_close_botsheet);
        spinKategori = v.findViewById(R.id.spinner_kategori);
        spinTipe = v.findViewById(R.id.spinner_tipe);
        spinArtikel = v.findViewById(R.id.spinner_artikel);
        btnBersihkan = v.findViewById(R.id.btn_bersihkan_filter);
        btnTerapkan = v.findViewById(R.id.btn_terapkan_filter_produk);

        //SET LISTENER
        btnBersihkan.setOnClickListener(BotSheetFilterProdukFragment.this);
        btnTerapkan.setOnClickListener(BotSheetFilterProdukFragment.this);

        //SPINNER KATEGORI
        // Spinner Drop down elements
        List<KategoriItem> kategoriItems = new ArrayList<>();
        kategoriItems.add(new KategoriItem("EYEWEAR"));
        kategoriItems.add(new KategoriItem("HEALMETS"));
        kategoriItems.add(new KategoriItem("SPAREPART"));
        kategoriItems.add(new KategoriItem("LENSES"));
        kategoriItems.add(new KategoriItem("APPAREAL"));
        kategoriItems.add(new KategoriItem("BIKE"));
        kategoriItems.add(new KategoriItem("ACCESSORIES"));

        //Update object to string
        List<String> katItem = new ArrayList<String>();
        for (KategoriItem item : kategoriItems){
            katItem.add(item.getNamaKetegori());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> katAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, katItem);
        katAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        spinKategori.setAdapter(new NothingSelectedSpinnerAdapter(katAdapter, getActivity(), R.layout.spinner_text_nothing_selected, getString(R.string.hint_spinner_pilih_kategori)));

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

        //SPINNER ARTIKEL
        // Spinner Drop down elements
        List<ArtikelItem> artikelItems = new ArrayList<>();
        for (int i=0; i<50; i++){
            artikelItems.add(new ArtikelItem("SP633846-0011"));
        }

        //Update object to string
        List<String> artikelItem = new ArrayList<String>();
        for (ArtikelItem item : artikelItems){
            artikelItem.add(item.getNamaArtikel());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> artikelAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, artikelItem);
        artikelAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        spinArtikel.setAdapter(new NothingSelectedSpinnerAdapter(artikelAdapter, getActivity(), R.layout.spinner_text_nothing_selected, getString(R.string.hint_spinner_pilih_artikel)));

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_bersihkan_filter:
                //FUNCTION CLEAR FILTER
                break;
            case R.id.btn_terapkan_filter_produk:
                //FUNCTION TERAPKAN FILTER
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }
}