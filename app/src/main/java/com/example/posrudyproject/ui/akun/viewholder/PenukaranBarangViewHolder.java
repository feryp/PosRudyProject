package com.example.posrudyproject.ui.akun.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.makeramen.roundedimageview.RoundedImageView;

public class PenukaranBarangViewHolder extends RecyclerView.ViewHolder{

    public MaterialCheckBox checkBox;
    public AppCompatTextView tipeBarang;
    public AppCompatTextView artikelBarang;
    public AppCompatTextView namaBarang;
    public AppCompatEditText jumlahBarang;
    public MaterialButton btnPlus, btnMinus;

    public PenukaranBarangViewHolder(@NonNull View itemView) {
        super(itemView);

        checkBox = itemView.findViewById(R.id.checkbox_barang_tukar);
        tipeBarang = itemView.findViewById(R.id.tv_tipe_barang_penukaran);
        artikelBarang = itemView.findViewById(R.id.tv_artikel_barang_penukaran);
        namaBarang = itemView.findViewById(R.id.tv_nama_barang_penukaran);
        jumlahBarang = itemView.findViewById(R.id.et_jumlah_barang_penukaran);
        btnPlus = itemView.findViewById(R.id.btn_plus_penukaran);
        btnMinus = itemView.findViewById(R.id.btn_minus_penukaran);
    }
}
