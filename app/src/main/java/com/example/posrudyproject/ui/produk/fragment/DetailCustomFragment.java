package com.example.posrudyproject.ui.produk.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class DetailCustomFragment extends Fragment {

    AppCompatTextView tvLensa, tvFrame, tvArtikel, tvNamaBarang, tvTipe, tvKuantitas, tvHargaJual;
    RoundedImageView imBarang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_custom, container, false);

        //INIT VIEW
        tvLensa = v.findViewById(R.id.tv_lensa_detaiL_custom);
        tvFrame = v.findViewById(R.id.tv_frame_detaiL_custom);
        tvArtikel = v.findViewById(R.id.tv_artikel_baru_detaiL_custom);
        tvNamaBarang = v.findViewById(R.id.tv_nama_barang_baru_detaiL_custom);
        tvTipe = v.findViewById(R.id.tv_tipe_detaiL_custom);
        tvKuantitas = v.findViewById(R.id.tv_kuantitas_detaiL_custom);
        tvHargaJual = v.findViewById(R.id.tv_harga_jual_detaiL_custom);
        imBarang = v.findViewById(R.id.im_barang_baru_detail_custom);

        return v;
    }
}