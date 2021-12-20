package com.example.posrudyproject.ui.penyimpanan.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.penyimpanan.adapter.BarangKeluarAdapter;
import com.example.posrudyproject.ui.penyimpanan.adapter.BarangPindahAdapter;
import com.example.posrudyproject.ui.penyimpanan.model.BarangKeluarItem;
import com.example.posrudyproject.ui.penyimpanan.model.BarangPindahItem;

import java.util.ArrayList;
import java.util.List;

public class BarangPindahFragment extends Fragment implements OnItemClickListener {

    ConstraintLayout layoutEmpty;
    SearchView cariBarang;
    RecyclerView rvBarangPindah;
    BarangPindahAdapter adapter;
    List<BarangPindahItem> barangPindahItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_barang_pindah, container, false);

        //INIT VIEW
        layoutEmpty = v.findViewById(R.id.layout_ilustrasi_empty_barang_pindah);
        cariBarang = v.findViewById(R.id.search_barang_pindah);
        rvBarangPindah = v.findViewById(R.id.rv_barang_pindah);


        //Barang Keluar List
        barangPindahItems = new ArrayList<>();
        for (int i=0; i<50; i++){
            barangPindahItems.add(new BarangPindahItem(
                    "DOC-0001",
                    "10-08-2021, 15:5",
                    "200 Barang"
            ));
        }

        //Setup adapter
        adapter = new BarangPindahAdapter(barangPindahItems, this);
        rvBarangPindah.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBarangPindah.setAdapter(adapter);
        rvBarangPindah.setHasFixedSize(true);

        //Jika ada list item ilustrasi hilang
        if (adapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
        }

        return v;
    }

    @Override
    public void onItemClickListener(View view, int position) {

    }
}