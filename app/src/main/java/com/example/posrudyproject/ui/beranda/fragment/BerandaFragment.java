package com.example.posrudyproject.ui.beranda.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.beranda.adapter.ImageSliderAdapter;
import com.example.posrudyproject.ui.beranda.model.ImageSliderItem;
import com.example.posrudyproject.ui.notifikasi.activity.NotifikasiActivity;
import com.example.posrudyproject.ui.penjual.activity.PenjualActivity;
import com.example.posrudyproject.ui.laporan.activity.LaporanActivity;
import com.example.posrudyproject.ui.pelanggan.activity.PelangganActivity;
import com.example.posrudyproject.ui.penjualan.activity.KategoriActivity;
import com.example.posrudyproject.ui.penyimpanan.activity.PenyimpananActivity;
import com.example.posrudyproject.ui.produk.activity.ProdukActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BerandaFragment extends Fragment implements View.OnClickListener {

    private ViewPager2 vp2;
    private ImageSliderAdapter imageSliderAdapter;
    private final Handler sliderHandler = new Handler();
    MaterialCardView cardPenjualan, cardPenyimpanan, cardPelanggan, cardPenjual, cardProduk, cardLaporan;
    MaterialToolbar mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_beranda, container, false);

        //INIT VIEW
        mToolbar = v.findViewById(R.id.toolbar_beranda);
        vp2 = v.findViewById(R.id.vp_image_slider);
        cardPenjualan = v.findViewById(R.id.card_penjualan);
        cardPenyimpanan = v.findViewById(R.id.card_penyimpanan);
        cardPelanggan = v.findViewById(R.id.card_pelanggan);
        cardPenjual = v.findViewById(R.id.card_penjual);
        cardProduk = v.findViewById(R.id.card_produk);
        cardLaporan = v.findViewById(R.id.card_laporan);

        initToolbar();

        List<ImageSliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new ImageSliderItem(R.drawable.im_slide_1));
        sliderItems.add(new ImageSliderItem(R.drawable.im_slide_2));
        sliderItems.add(new ImageSliderItem(R.drawable.im_slide_3));

        vp2.setAdapter(new ImageSliderAdapter(sliderItems, vp2));
        vp2.setCurrentItem(2, true); //Set currentItem index : 2
        vp2.setClipToPadding(false);
        vp2.setClipChildren(false);
        vp2.setOffscreenPageLimit(3);
        vp2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        vp2.setPageTransformer(compositePageTransformer);

        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000); //slide duration 3 seconds
            }
        });

        cardPenjualan.setOnClickListener(BerandaFragment.this);
        cardPenyimpanan.setOnClickListener(BerandaFragment.this);
        cardPelanggan.setOnClickListener(BerandaFragment.this);
        cardPenjual.setOnClickListener(BerandaFragment.this);
        cardProduk.setOnClickListener(BerandaFragment.this);
        cardLaporan.setOnClickListener(BerandaFragment.this);


        return v;
    }

    private void initToolbar() {
//        mToolbar.getMenu().getItem(0).setIcon(R.drawable.ic_bell);  // Untuk kasih kondisi menampilkan icon bell jika ada notif masuk

        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_notifikasi){
                Intent notifikasi = new Intent(getActivity(), NotifikasiActivity.class);
                startActivity(notifikasi);
                return true;
            }

            return false;
        });
    }


    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            vp2.setCurrentItem(vp2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_penjualan:
                Intent penjualan = new Intent(getActivity(), KategoriActivity.class);
                startActivity(penjualan);
                break;
            case R.id.card_penyimpanan:
                Intent penyimpanan = new Intent(getActivity(), PenyimpananActivity.class);
                startActivity(penyimpanan);
                break;
            case R.id.card_pelanggan:
                Intent pelanggan = new Intent(getActivity(), PelangganActivity.class);
                startActivity(pelanggan);
                break;
            case R.id.card_penjual:
                Intent penjual =  new Intent(getActivity(), PenjualActivity.class);
                startActivity(penjual);
                break;
            case R.id.card_produk:
                Intent produk = new Intent(getActivity(), ProdukActivity.class);
                startActivity(produk);
                break;
            case R.id.card_laporan:
                Intent laporan = new Intent(getActivity(), LaporanActivity.class);
                startActivity(laporan);
                break;
        }
    }
}