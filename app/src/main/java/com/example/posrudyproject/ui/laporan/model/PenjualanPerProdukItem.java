package com.example.posrudyproject.ui.laporan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PenjualanPerProdukItem implements Serializable {
    @SerializedName("nama_barang")
    private String namaProduk;

    @SerializedName("harga_jual")
    private String penjualanKotorProduk;

    @SerializedName("total_terjual")
    private String qtyProduk;

    @SerializedName("artikel")
    private String artikelProduk;

    @SerializedName("nama_kategori")
    private String kategoriProduk;

    @SerializedName("penjualan_kotor")
    private String totalPenjualanProduk;

    private boolean expanded;

    public PenjualanPerProdukItem(String namaProduk, String penjualanKotorProduk, String qtyProduk, String artikelProduk, String kategoriProduk, String totalPenjualanProduk) {
        this.namaProduk = namaProduk;
        this.penjualanKotorProduk = penjualanKotorProduk;
        this.qtyProduk = qtyProduk;
        this.artikelProduk = artikelProduk;
        this.kategoriProduk = kategoriProduk;
        this.totalPenjualanProduk = totalPenjualanProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getPenjualanKotorProduk() {
        return penjualanKotorProduk;
    }

    public void setPenjualanKotorProduk(String penjualanKotorProduk) {
        this.penjualanKotorProduk = penjualanKotorProduk;
    }

    public String getQtyProduk() {
        return qtyProduk;
    }

    public void setQtyProduk(String qtyProduk) {
        this.qtyProduk = qtyProduk;
    }

    public String getArtikelProduk() {
        return artikelProduk;
    }

    public void setArtikelProduk(String artikelProduk) {
        this.artikelProduk = artikelProduk;
    }

    public String getKategoriProduk() {
        return kategoriProduk;
    }

    public void setKategoriProduk(String kategoriProduk) {
        this.kategoriProduk = kategoriProduk;
    }


    public String getTotalPenjualanProduk() {
        return totalPenjualanProduk;
    }

    public void setTotalPenjualanProduk(String totalPenjualanProduk) {
        this.totalPenjualanProduk = totalPenjualanProduk;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
