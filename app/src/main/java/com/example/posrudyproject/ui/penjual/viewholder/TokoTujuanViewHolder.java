package com.example.posrudyproject.ui.penjual.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class TokoTujuanViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView namaToko;

    public TokoTujuanViewHolder(@NonNull View itemView) {
        super(itemView);

        namaToko = itemView.findViewById(R.id.tv_item_botsheet);
    }
}
