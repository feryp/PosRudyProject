package com.example.posrudyproject.ui.akun.fragment;

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
import com.example.posrudyproject.ui.pesananTunggu.adapter.PesananTungguAdapter;
import com.example.posrudyproject.ui.pesananTunggu.model.BarangPesananTungguItem;
import com.example.posrudyproject.ui.pesananTunggu.model.PesananTungguItem;

import java.util.ArrayList;
import java.util.List;

public class RiwayatProsesFragment extends Fragment implements OnItemClickListener {

    RecyclerView rvProsesTransaksi;
    PesananTungguAdapter pesananTungguAdapter;
    ConstraintLayout layoutEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_riwayat_proses, container, false);

        //INIT VIEW
        rvProsesTransaksi = v.findViewById(R.id.rv_proses_transaksi);
        layoutEmpty = v.findViewById(R.id.layout_ilustrasi_empty_transaksi_proses);

        //Setup Adapter
        pesananTungguAdapter = new PesananTungguAdapter(buildItemList(), this);
        rvProsesTransaksi.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvProsesTransaksi.setAdapter(pesananTungguAdapter);
        rvProsesTransaksi.setHasFixedSize(true);

        //Jika ada list item ilustrasi hilang
        if (pesananTungguAdapter.getItemCount() > 0){
            layoutEmpty.setVisibility(View.GONE);
        }

        return v;
    }

    private List<PesananTungguItem> buildItemList() {
        List<PesananTungguItem> pesananTungguItems = new ArrayList<>();
        for (int i=0; i<20; i++){
            PesananTungguItem item = new PesananTungguItem(
                    "#INV01",
                    "31 Okt 2021",
                    "10:15 AM",
                    "Rp 9.503.000",
                    "Masih mencari barang yg ingin dibeli",
                    "Bambang", buildSubItemList());
            pesananTungguItems.add(item);
        }

        return pesananTungguItems;

    }

    private List<BarangPesananTungguItem> buildSubItemList() {
        List<BarangPesananTungguItem> barangPesananTungguItems = new ArrayList<>();
        for (int i=0; i<2; i++){
            BarangPesananTungguItem subItem = new BarangPesananTungguItem(2,"Mandarin Fade/Coral Matte - RP Optics Multilaser Red");
            barangPesananTungguItems.add(subItem);
        }
        return barangPesananTungguItems;
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Toast.makeText(getActivity(), "Pilih " + buildItemList().get(position).getNoPesanan(), Toast.LENGTH_SHORT).show();
    }
}