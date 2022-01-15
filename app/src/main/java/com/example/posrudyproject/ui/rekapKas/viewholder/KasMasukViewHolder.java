package com.example.posrudyproject.ui.rekapKas.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.rekapKas.model.KasMasukItem;

public class KasMasukViewHolder extends RecyclerView.ViewHolder{

    public AppCompatTextView nominalKas, waktu, namaPenjual, catatan;

    public KasMasukViewHolder(@NonNull View itemView) {
        super(itemView);

        nominalKas = itemView.findViewById(R.id.tv_nominal_kas_masuk);
        waktu = itemView.findViewById(R.id.tv_waktu_kas_masuk);
        namaPenjual = itemView.findViewById(R.id.tv_nama_penjual_kas_masuk);
        catatan = itemView.findViewById(R.id.tv_catatan_kas_masuk);
    }
    public void Bind(KasMasukItem item){
        nominalKas.setText(item.getNominalKasMasuk());
        waktu.setText(item.getWaktuKasMasuk());
        namaPenjual.setText(item.getPenjualKasMasuk());
        catatan.setText(item.getCatatanKasMasuk());
    }
}
