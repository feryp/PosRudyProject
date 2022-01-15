package com.example.posrudyproject.ui.rekapKas.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.rekapKas.model.KasKeluarItem;
import com.example.posrudyproject.ui.rekapKas.model.KasMasukItem;

public class KasKeluarViewHolder extends RecyclerView.ViewHolder{

    public AppCompatTextView  nominalKas, waktu, namaPenjual, catatan;

    public KasKeluarViewHolder(@NonNull View itemView) {
        super(itemView);

        nominalKas = itemView.findViewById(R.id.tv_nominal_kas_keluar);
        waktu = itemView.findViewById(R.id.tv_waktu_kas_keluar);
        namaPenjual = itemView.findViewById(R.id.tv_nama_penjual_kas_keluar);
        catatan = itemView.findViewById(R.id.tv_catatan_kas_keluar);
    }

    public void Bind(KasKeluarItem item){
        nominalKas.setText(item.getNominalKasKeluar());
        waktu.setText(item.getWaktuKasKeluar());
        namaPenjual.setText(item.getPenjualKasKeluar());
        catatan.setText(item.getCatatanKasKeluar());
    }
}
