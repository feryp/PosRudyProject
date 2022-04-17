package com.example.posrudyproject.ui.laporan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PenjualanPerKategoriItem implements Serializable {

    @SerializedName("nama_kategori")
    private String namaKategori;

    @SerializedName("harga_jual")
    private String penjualanKotorKategori;

    @SerializedName("total_terjual")
    private String jmlTerjualKategori;

    @SerializedName("penjualan_kotor")
    private String totPenjualanKategori;

    private boolean expanded;

    public PenjualanPerKategoriItem(String namaKategori, String penjualanKotorKategori, String jmlTerjualKategori, String totPenjualanKategori) {
        this.namaKategori = namaKategori;
        this.penjualanKotorKategori = penjualanKotorKategori;
        this.jmlTerjualKategori = jmlTerjualKategori;
        this.totPenjualanKategori = totPenjualanKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public String getPenjualanKotorKategori() {
        return penjualanKotorKategori;
    }

    public void setPenjualanKotorKategori(String penjualanKotorKategori) {
        this.penjualanKotorKategori = penjualanKotorKategori;
    }

    public String getJmlTerjualKategori() {
        return jmlTerjualKategori;
    }

    public void setJmlTerjualKategori(String jmlTerjualKategori) {
        this.jmlTerjualKategori = jmlTerjualKategori;
    }

    public String getTotPenjualanKategori() {
        return totPenjualanKategori;
    }

    public void setTotPenjualanKategori(String totPenjualanKategori) {
        this.totPenjualanKategori = totPenjualanKategori;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
