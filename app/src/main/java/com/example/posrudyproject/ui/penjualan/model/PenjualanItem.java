package com.example.posrudyproject.ui.penjualan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PenjualanItem implements Serializable {
    @SerializedName("foto_barang")
    private String foto_barang;

    @SerializedName("type_name")
    private String tipeBarang;

    @SerializedName("artikel")
    private String artikelBarang;

    @SerializedName("nama_barang")
    private String namaBarang;

    @SerializedName("harga_jual")
    private String hargaBarang;

    @SerializedName("kuantitas")
    private String kuantitasBarang;

    public PenjualanItem(String foto_barang, String tipeBarang, String artikelBarang, String namaBarang, String hargaBarang, String kuantitasBarang) {
        this.foto_barang = foto_barang;
        this.tipeBarang = tipeBarang;
        this.artikelBarang = artikelBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
        this.kuantitasBarang = kuantitasBarang;
    }

    public String getFoto_barang() {
        return foto_barang;
    }

    public void setFoto_barang(String foto_barang) {
        this.foto_barang = foto_barang;
    }

    public String getTipeBarang() {
        return tipeBarang;
    }

    public void setTipeBarang(String tipeBarang) {
        this.tipeBarang = tipeBarang;
    }

    public String getArtikelBarang() {
        return artikelBarang;
    }

    public void setArtikelBarang(String artikelBarang) {
        this.artikelBarang = artikelBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(String hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public String getKuantitasBarang() {
        return kuantitasBarang;
    }

    public void setKuantitasBarang(String kuantitasBarang) {
        this.kuantitasBarang = kuantitasBarang;
    }
}
