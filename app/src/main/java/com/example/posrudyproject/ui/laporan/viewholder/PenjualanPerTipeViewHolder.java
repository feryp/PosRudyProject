package com.example.posrudyproject.ui.laporan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.laporan.model.PenjualanPerTipeItem;

public class PenjualanPerTipeViewHolder extends RecyclerView.ViewHolder{

    public AppCompatTextView namaTipe, penjualanKotorTipe, jmlTerjualTipe, totalPenjualanTipe, pajakTipe;
    public AppCompatImageView imArrow;
    public LinearLayoutCompat subItem;

    public PenjualanPerTipeViewHolder(@NonNull View itemView) {
        super(itemView);

        namaTipe = itemView.findViewById(R.id.tv_nama_tipe_penjualan_laporan);
        penjualanKotorTipe = itemView.findViewById(R.id.tv_penjualan_kotor_tipe_laporan);
        jmlTerjualTipe = itemView.findViewById(R.id.tv_terjual_penjualan_tipe_laporan);
        totalPenjualanTipe = itemView.findViewById(R.id.tv_total_terjual_tipe_laporan);
        pajakTipe = itemView.findViewById(R.id.tv_pajak_penjualan_tipe_laporan);
        imArrow = itemView.findViewById(R.id.btn_expand_arrow);
        subItem = itemView.findViewById(R.id.sub_item);
    }

    public void bind(PenjualanPerTipeItem item) {
        // Get the state
        boolean expanded = item.isExpanded();

        // Set the visibility based on state
        subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
        imArrow.setRotation(expanded ? 180 : 0);

        namaTipe.setText(item.getNamaTipe());
        penjualanKotorTipe.setText(item.getPenjualanKotorTipe());
        jmlTerjualTipe.setText(item.getJmlTerjualTipe());
        totalPenjualanTipe.setText(item.getTotPenjualanTipe());
        pajakTipe.setText(item.getPajakTipe());
    }
}
