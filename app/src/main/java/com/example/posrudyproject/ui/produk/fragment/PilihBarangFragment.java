package com.example.posrudyproject.ui.produk.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
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

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.filter.adapter.NothingSelectedSpinnerAdapter;
import com.example.posrudyproject.ui.filter.fragment.BotSheetFilterPelangganFragment;
import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.example.posrudyproject.ui.produk.activity.ProdukActivity;
import com.example.posrudyproject.ui.produk.adapter.ProdukAdapter;
import com.example.posrudyproject.ui.produk.adapter.ProdukCustomAdapter;
import com.example.posrudyproject.ui.produk.model.ProdukItem;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;


public class PilihBarangFragment extends Fragment  implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    AppCompatSpinner spinTipe;
    ConstraintLayout layoutItemProduk1, layoutItemProduk2;
    ConstraintLayout btnPilihProduk1, btnPilihProduk2;
    RoundedImageView imProduk1, imProduk2;
    AppCompatTextView tipeProduk1, artikelProduk1, namaProduk1, lensaProduk1, frameProduk1;
    AppCompatTextView tipeProduk2, artikelProduk2, namaProduk2, lensaProduk2, frameProduk2;

    List<ProdukItem> produkItems;
    ProdukCustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pilih_barang, container, false);

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


        //SET LISTENER
        btnPilihProduk1.setOnClickListener(PilihBarangFragment.this);
        btnPilihProduk2.setOnClickListener(PilihBarangFragment.this);

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
        if (view == btnPilihProduk1){
            BotSheetProdukFragment botSheetProduk = new BotSheetProdukFragment();
            botSheetProduk.setCancelable(false);
            botSheetProduk.show(getChildFragmentManager(), botSheetProduk.getTag());

        } else if (view == btnPilihProduk2){
            BotSheetProdukFragment botSheetProduk = new BotSheetProdukFragment();
            botSheetProduk.setCancelable(false);
            botSheetProduk.show(getChildFragmentManager(), botSheetProduk.getTag());
        }
    }
}