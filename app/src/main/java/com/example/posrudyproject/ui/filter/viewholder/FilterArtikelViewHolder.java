package com.example.posrudyproject.ui.filter.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class FilterArtikelViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView artikelBarang;

    public FilterArtikelViewHolder(@NonNull View itemView) {
        super(itemView);

        artikelBarang = itemView.findViewById(R.id.tv_item_botsheet);
    }
}
