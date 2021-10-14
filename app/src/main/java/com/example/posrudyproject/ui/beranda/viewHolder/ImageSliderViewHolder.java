package com.example.posrudyproject.ui.beranda.viewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.beranda.model.ImageSliderItem;
import com.makeramen.roundedimageview.RoundedImageView;

public class ImageSliderViewHolder extends RecyclerView.ViewHolder {

    public RoundedImageView imSlider;

    public ImageSliderViewHolder(@NonNull View itemView) {
        super(itemView);

        imSlider = itemView.findViewById(R.id.image_slide);
    }


    //Jika ingin menampilkan gambar dari internet
    //Dapat menggunakan glide atau picasso.
    void setImage(ImageSliderItem sliderItem){
        imSlider.setImageResource(sliderItem.getImage());
    }
}
