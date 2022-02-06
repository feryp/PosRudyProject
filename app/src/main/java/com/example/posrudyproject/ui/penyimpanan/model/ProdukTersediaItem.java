package com.example.posrudyproject.ui.penyimpanan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProdukTersediaItem implements Serializable {
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
    private String jumlahStok;

    public ProdukTersediaItem(String foto_barang, String tipeBarang, String artikelBarang, String namaBarang, String hargaBarang, String jumlahStok) {
        this.foto_barang = foto_barang;
        this.tipeBarang = tipeBarang;
        this.artikelBarang = artikelBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
        this.jumlahStok = jumlahStok;
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

    public String getJumlahStok() {
        return jumlahStok;
    }

    public void setJumlahStok(String jumlahStok) {
        this.jumlahStok = jumlahStok;
    }
}
