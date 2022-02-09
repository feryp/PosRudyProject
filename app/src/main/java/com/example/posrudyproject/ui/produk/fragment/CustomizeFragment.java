package com.example.posrudyproject.ui.produk.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posrudyproject.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

public class CustomizeFragment extends Fragment {

    MaterialRadioButton rdLensaProduk1, rdLensaProduk2, rdFrameProduk1, rdFrameProduk2;
    AppCompatTextView tvLensaProduk1, tvLensaProduk2, tvFrameProduk1, tvFrameProduk2;
    TextInputEditText etArtikelBaru, etCustomKode, etNamaBarangBaru, etTipeBaru, etKuantitas, etHargaJual, etCatatan;
    MaterialCardView cardUploadImage;
    RoundedImageView imItemCustom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_customize, container, false);

        //INIT VIEW
        rdLensaProduk1 = v.findViewById(R.id.rd_lens_produk_1);
        rdLensaProduk2 = v.findViewById(R.id.rd_lens_produk_2);
        rdFrameProduk1 = v.findViewById(R.id.rd_frame_produk_1);
        rdFrameProduk2 = v.findViewById(R.id.rd_frame_produk_2);
        tvLensaProduk1 = v.findViewById(R.id.tv_rd_lens_produk_1);
        tvLensaProduk2 = v.findViewById(R.id.tv_rd_lens_produk_2);
        tvFrameProduk1 = v.findViewById(R.id.tv_rd_frame_produk_1);
        tvFrameProduk2 = v.findViewById(R.id.tv_rd_frame_produk_2);
        etArtikelBaru = v.findViewById(R.id.et_artikel_baru);
        etCustomKode = v.findViewById(R.id.et_custom_kode);
        etNamaBarangBaru = v.findViewById(R.id.et_nama_barang_baru);
        etTipeBaru = v.findViewById(R.id.et_tipe_baru);
        etKuantitas = v.findViewById(R.id.et_kuantitas_baru);
        etHargaJual = v.findViewById(R.id.et_harga_jual_baru);
        etCatatan = v.findViewById(R.id.et_catatan_custom);
        cardUploadImage = v.findViewById(R.id.card_upload_image_custom);
        imItemCustom = v.findViewById(R.id.im_barang_baru_custom);

        cardUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FUNCTION UPLOAD
            }
        });

        return v;
    }
}