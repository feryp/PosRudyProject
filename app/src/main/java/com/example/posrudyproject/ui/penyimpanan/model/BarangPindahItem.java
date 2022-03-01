package com.example.posrudyproject.ui.penyimpanan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BarangPindahItem implements Serializable {
    private int imBarang;

    @SerializedName("type_name")
    private String tipeBarangPindah;

    @SerializedName("sku_code")
    private String skuCode;

    @SerializedName("artikel")
    private String artikelBarangPindah;

    @SerializedName("nama_barang")
    private String namaBarangPindah;

    @SerializedName("kuantitas")
    private String kuantitasBarangPindah;


    public BarangPindahItem(int imBarang, String tipeBarangPindah, String skuCode, String artikelBarangPindah, String namaBarangPindah, String kuantitasBarangPindah) {
        this.imBarang = imBarang;
        this.tipeBarangPindah = tipeBarangPindah;
        this.skuCode = skuCode;
        this.artikelBarangPindah = artikelBarangPindah;
        this.namaBarangPindah = namaBarangPindah;
        this.kuantitasBarangPindah = kuantitasBarangPindah;
    }

    public int getImBarang() {
        return imBarang;
    }

    public void setImBarang(int imBarang) {
        this.imBarang = imBarang;
    }

    public String getTipeBarangPindah() {
        return tipeBarangPindah;
    }

    public void setTipeBarangPindah(String tipeBarangPindah) {
        this.tipeBarangPindah = tipeBarangPindah;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getArtikelBarangPindah() {
        return artikelBarangPindah;
    }

    public void setArtikelBarangPindah(String artikelBarangPindah) {
        this.artikelBarangPindah = artikelBarangPindah;
    }

    public String getNamaBarangPindah() {
        return namaBarangPindah;
    }

    public void setNamaBarangPindah(String namaBarangPindah) {
        this.namaBarangPindah = namaBarangPindah;
    }

    public String getKuantitasBarangPindah() {
        return kuantitasBarangPindah;
    }

    public void setKuantitasBarangPindah(String kuantitasBarangPindah) {
        this.kuantitasBarangPindah = kuantitasBarangPindah;
    }

}
