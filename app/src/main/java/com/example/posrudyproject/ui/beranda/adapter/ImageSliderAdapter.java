package com.example.posrudyproject.ui.beranda.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.beranda.model.ImageSliderItem;
import com.example.posrudyproject.ui.beranda.viewholder.ImageSliderViewHolder;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderViewHolder> {

    private List<ImageSliderItem> sliderItems;
    private ViewPager2 viewPager2;

    public ImageSliderAdapter(List<ImageSliderItem> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_slider, parent,false);
        return new ImageSliderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.imSlider.setImageResource(sliderItems.get(position).getImage());
        if (position == sliderItems.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    //runnable infinite slide facility
    private final Runnable runnable = new Runnable() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };
}
