package com.example.posrudyproject.ui.penyimpanan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PemindahanBarangItem implements Serializable {
    @SerializedName("foto_barang")
    private String foto_barang;

    @SerializedName("type_name")
    private String tipeProduk;

    @SerializedName("sku_code")
    private String skuCode;

    @SerializedName("artikel")
    private String artikelProduk;

    @SerializedName("nama_barang")
    private String namaProduk;

    @SerializedName("kuantitas")
    private String jumlahProduk;

    public PemindahanBarangItem(String foto_barang, String tipeProduk, String skuCode, String artikelProduk, String namaProduk, String jumlahProduk) {
        this.foto_barang = foto_barang;
        this.tipeProduk = tipeProduk;
        this.skuCode = skuCode;
        this.artikelProduk = artikelProduk;
        this.namaProduk = namaProduk;
        this.jumlahProduk = jumlahProduk;
    }

    public String getFoto_barang() {
        return foto_barang;
    }

    public void setFoto_barang(String foto_barang) {
        this.foto_barang = foto_barang;
    }

    public String getTipeProduk() {
        return tipeProduk;
    }

    public void setTipeProduk(String tipeProduk) {
        this.tipeProduk = tipeProduk;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getArtikelProduk() {
        return artikelProduk;
    }

    public void setArtikelProduk(String artikelProduk) {
        this.artikelProduk = artikelProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getJumlahProduk() {
        return jumlahProduk;
    }

    public void setJumlahProduk(String jumlahProduk) {
        this.jumlahProduk = jumlahProduk;
    }
}
