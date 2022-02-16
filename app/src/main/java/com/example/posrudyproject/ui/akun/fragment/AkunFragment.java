package com.example.posrudyproject.ui.akun.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.posrudyproject.R;
import com.example.posrudyproject.ui.akun.activity.BantuanActivity;
import com.example.posrudyproject.ui.akun.activity.PengaturanActivity;
import com.example.posrudyproject.ui.akun.activity.PrivacyPolicyActivity;
import com.example.posrudyproject.ui.akun.activity.RiwayatTransaksiActivity;
import com.example.posrudyproject.ui.akun.activity.TermsConditionActivity;
import com.example.posrudyproject.ui.akun.activity.UbahAkunActivity;
import com.example.posrudyproject.ui.login.LoginActivity;
import com.example.posrudyproject.ui.notifikasi.activity.NotifikasiActivity;
import com.example.posrudyproject.ui.penjualan.activity.KategoriActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;


public class AkunFragment extends Fragment implements View.OnClickListener{

    MaterialToolbar mToolbar;
    MaterialButton btnSignOut;
    AppCompatTextView tvNamaToko, tvAlamatToko;
    MaterialCardView menuUbahAkun, menuRiwayatTransaksi, menuPengaturan, menuBantuan, menuKebijakanPribadi, menuSyaratKetentuan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_akun, container, false);

        //INIT VIEW
        mToolbar = v.findViewById(R.id.toolbar_akun);
        btnSignOut = v.findViewById(R.id.btn_sign_out);
        tvNamaToko = v.findViewById(R.id.tv_nama_toko_akun);
        tvAlamatToko = v.findViewById(R.id.tv_alamat_toko_akun);
        menuUbahAkun = v.findViewById(R.id.menu_ubah_akun);
        menuRiwayatTransaksi = v.findViewById(R.id.menu_riwayat_transaksi);
        menuPengaturan =v.findViewById(R.id.menu_pengaturan);
        menuBantuan = v.findViewById(R.id.menu_bantuan);
        menuKebijakanPribadi = v.findViewById(R.id.menu_privacy_policy);
        menuSyaratKetentuan = v.findViewById(R.id.menu_terms_and_condition);

        initToolbar();

        //SET LISTENER
        btnSignOut.setOnClickListener(AkunFragment.this);
        menuUbahAkun.setOnClickListener(AkunFragment.this);
        menuRiwayatTransaksi.setOnClickListener(AkunFragment.this);
        menuPengaturan.setOnClickListener(AkunFragment.this);
        menuBantuan.setOnClickListener(AkunFragment.this);
        menuKebijakanPribadi.setOnClickListener(AkunFragment.this);
        menuSyaratKetentuan.setOnClickListener(AkunFragment.this);

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_out:
                Toast.makeText(getActivity(), "Keluar", Toast.LENGTH_SHORT).show();
                Intent signOut = new Intent(getActivity(), LoginActivity.class);
                startActivity(signOut);
                break;
            case R.id.menu_ubah_akun:
                Intent ubahAkun = new Intent(getActivity(), UbahAkunActivity.class);
                startActivity(ubahAkun);
                break;
            case R.id.menu_riwayat_transaksi:
                Intent riwayatTransaksi = new Intent(getActivity(), RiwayatTransaksiActivity.class);
                startActivity(riwayatTransaksi);
                break;
            case R.id.menu_pengaturan:
                Intent pengaturan = new Intent(getActivity(), PengaturanActivity.class);
                startActivity(pengaturan);
                break;
            case R.id.menu_bantuan:
                Intent bantuan = new Intent(getActivity(), BantuanActivity.class);
                startActivity(bantuan);
                break;
            case R.id.menu_privacy_policy:
                Intent kebijakanPribadi = new Intent(getActivity(), PrivacyPolicyActivity.class);
                startActivity(kebijakanPribadi);
                break;
            case R.id.menu_terms_and_condition:
                Intent syaratKetentuan = new Intent(getActivity(), TermsConditionActivity.class);
                startActivity(syaratKetentuan);
                break;
        }
    }
}