package com.example.posrudyproject.ui.produk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProdukItem implements Serializable {
    @SerializedName("foto_barang")
    private String foto_barang;

    @SerializedName("type_name")
    private String tipeProduk;

    @SerializedName("artikel")
    private String artikelProduk;

    @SerializedName("nama_barang")
    private String namaProduk;

    @SerializedName("kuantitas")
    private String stokProduk;

    public ProdukItem(String foto_barang, String tipeProduk, String artikelProduk, String namaProduk, String stokProduk) {
        this.foto_barang = foto_barang;
        this.tipeProduk = tipeProduk;
        this.artikelProduk = artikelProduk;
        this.namaProduk = namaProduk;
        this.stokProduk = stokProduk;
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

    public String getStokProduk() {
        return stokProduk;
    }

    public void setStokProduk(String stokProduk) {
        this.stokProduk = stokProduk;
    }
}
