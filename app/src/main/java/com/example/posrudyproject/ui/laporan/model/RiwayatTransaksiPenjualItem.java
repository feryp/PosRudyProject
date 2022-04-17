package com.example.posrudyproject.ui.laporan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RiwayatTransaksiPenjualItem implements Serializable {

    @SerializedName("tanggal_transaksi")
    private String waktuTransaksi;

    @SerializedName("total")
    private String nominalTransaksi;

    @SerializedName("lokasi_store")
    private String namaToko;

    @SerializedName("kuantitas")
    private String produkTerjual;

    public RiwayatTransaksiPenjualItem(String waktuTransaksi, String nominalTransaksi, String namaToko, String produkTerjual) {
        this.waktuTransaksi = waktuTransaksi;
        this.nominalTransaksi = nominalTransaksi;
        this.namaToko = namaToko;
        this.produkTerjual = produkTerjual;
    }

    public String getWaktuTransaksi() {
        return waktuTransaksi;
    }

    public void setWaktuTransaksi(String waktuTransaksi) {
        this.waktuTransaksi = waktuTransaksi;
    }

    public String getNominalTransaksi() {
        return nominalTransaksi;
    }

    public void setNominalTransaksi(String nominalTransaksi) {
        this.nominalTransaksi = nominalTransaksi;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getProdukTerjual() {
        return produkTerjual;
    }

    public void setProdukTerjual(String produkTerjual) {
        this.produkTerjual = produkTerjual;
    }
}
