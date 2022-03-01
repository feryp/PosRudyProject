package com.example.posrudyproject.ui.penyimpanan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BarangKeluarItem implements Serializable {
    @SerializedName("type_name")
    private String tipeBarangKeluar;

    @SerializedName("sku_code")
    private String skuCode;

    @SerializedName("artikel")
    private String artikelBarangKeluar;

    @SerializedName("nama_barang")
    private String namaBarangKeluar;

    @SerializedName("tanggal_keluar")
    private String waktuBarangKeluark;

    @SerializedName("kuantitas")
    private double stokBarangKeluar;

    public BarangKeluarItem(String tipeBarangKeluar, String skuCode, String artikelBarangKeluar, String namaBarangKeluar, String waktuBarangKeluark, double stokBarangKeluar) {
        this.tipeBarangKeluar = tipeBarangKeluar;
        this.skuCode = skuCode;
        this.artikelBarangKeluar = artikelBarangKeluar;
        this.namaBarangKeluar = namaBarangKeluar;
        this.waktuBarangKeluark = waktuBarangKeluark;
        this.stokBarangKeluar = stokBarangKeluar;
    }

    public String getTipeBarangKeluar() {
        return tipeBarangKeluar;
    }

    public void setTipeBarangKeluar(String tipeBarangKeluar) {
        this.tipeBarangKeluar = tipeBarangKeluar;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getArtikelBarangKeluar() {
        return artikelBarangKeluar;
    }

    public void setArtikelBarangKeluar(String artikelBarangKeluar) {
        this.artikelBarangKeluar = artikelBarangKeluar;
    }

    public String getNamaBarangKeluar() {
        return namaBarangKeluar;
    }

    public void setNamaBarangKeluar(String namaBarangKeluar) {
        this.namaBarangKeluar = namaBarangKeluar;
    }

    public String getWaktuBarangKeluark() {
        return waktuBarangKeluark;
    }

    public void setWaktuBarangKeluark(String waktuBarangKeluark) {
        this.waktuBarangKeluark = waktuBarangKeluark;
    }

    public double getStokBarangKeluar() {
        return stokBarangKeluar;
    }

    public void setStokBarangKeluar(double stokBarangKeluar) {
        this.stokBarangKeluar = stokBarangKeluar;
    }
}
