package com.example.posrudyproject.ui.laporan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PenjualanPerArtikelItem implements Serializable {

    @SerializedName("artikel")
    private String namaArtikel;

    @SerializedName("harga_jual")
    private String penjualanKotorArtikel;

    @SerializedName("total_terjual")
    private String jmlTerjualArtikel;

    @SerializedName("penjualan_kotor")
    private String totPenjualanArtikel;

    private boolean expanded;

    public PenjualanPerArtikelItem(String namaArtikel, String penjualanKotorArtikel, String jmlTerjualArtikel, String totPenjualanArtikel) {
        this.namaArtikel = namaArtikel;
        this.penjualanKotorArtikel = penjualanKotorArtikel;
        this.jmlTerjualArtikel = jmlTerjualArtikel;
        this.totPenjualanArtikel = totPenjualanArtikel;
    }

    public String getNamaArtikel() {
        return namaArtikel;
    }

    public void setNamaArtikel(String namaArtikel) {
        this.namaArtikel = namaArtikel;
    }

    public String getPenjualanKotorArtikel() {
        return penjualanKotorArtikel;
    }

    public void setPenjualanKotorArtikel(String penjualanKotorArtikel) {
        this.penjualanKotorArtikel = penjualanKotorArtikel;
    }

    public String getJmlTerjualArtikel() {
        return jmlTerjualArtikel;
    }

    public void setJmlTerjualArtikel(String jmlTerjualArtikel) {
        this.jmlTerjualArtikel = jmlTerjualArtikel;
    }

    public String getTotPenjualanArtikel() {
        return totPenjualanArtikel;
    }

    public void setTotPenjualanArtikel(String totPenjualanArtikel) {
        this.totPenjualanArtikel = totPenjualanArtikel;
    }


    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
