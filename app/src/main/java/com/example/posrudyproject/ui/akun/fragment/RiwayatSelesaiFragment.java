package com.example.posrudyproject.ui.akun.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.posrudyproject.Interface.OnItemClickListener;
import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.akun.activity.DetailTransaksiSelesaiActivity;
import com.example.posrudyproject.ui.akun.adapter.TransaksiSelesaiAdapter;
import com.example.posrudyproject.ui.akun.model.TransaksiSelesaiItem;

import java.util.ArrayList;
import java.util.List;

public class RiwayatSelesaiFragment extends Fragment implements OnItemClickListener {

    RecyclerView rvSelesaiTransaksi;
    TransaksiSelesaiAdapter adapter;
    List<TransaksiSelesaiItem> transaksiSelesaiItems;
    ConstraintLayout layoutEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_riwayat_selesai, container, false);

        //INIT VIEW
        rvSelesaiTransaksi = v.findViewById(R.id.rv_selesai_transaksi);
        layoutEmpty = v.findViewById(R.id.layout_ilustrasi_empty_transaksi_selesai);

        //Transaksi List
        transaksiSelesaiItems = new ArrayList<>();
        for (int i=0; i<50; i++){
            transaksiSelesaiItems.add(new TransaksiSelesaiItem(
                    "#INV001",
                    "Rp 10.000.000",
                    "Tunai",
                    "07 Aug 2021 | 10:09"
            ));
        }

        //Setup Adapter
        adapter = new TransaksiSelesaiAdapter(transaksiSelesaiItems, this);
        rvSelesaiTransaksi.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSelesaiTransaksi.setAdapter(adapter);
        rvSelesaiTransaksi.setHasFixedSize(true);

        //Jika ada list item ilustrasi hilang
        if (adapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
        }

        return v;
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(getActivity(), "Pilih " + transaksiSelesaiItems.get(position).getNoInvoice(), Toast.LENGTH_SHORT).show();
        Intent detailTransaksi = new Intent(getActivity(), DetailTransaksiSelesaiActivity.class);
        startActivity(detailTransaksi);
    }
}