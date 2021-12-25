package com.example.posrudyproject.ui.filter.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;

public class FilterTipeViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView tipeBarang;

    public FilterTipeViewHolder(@NonNull View itemView) {
        super(itemView);

        tipeBarang = itemView.findViewById(R.id.tv_item_botsheet);
    }
}
