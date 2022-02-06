package com.example.posrudyproject.ui.laporan.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class TipeTerlarisViewHolder extends RecyclerView.ViewHolder{

    public AppCompatTextView namaTipe;

    public TipeTerlarisViewHolder(@NonNull View itemView) {
        super(itemView);

        namaTipe = itemView.findViewById(R.id.tv_rangkuman_terlaris);
    }
}
