package com.example.posrudyproject.ui.laporan.model;

public class PenjualanPerArtikelItem {

    private String namaArtikel,
            penjualanKotorArtikel,
            jmlTerjualArtikel,
            totPenjualanArtikel,
            pajakArtikel;

    private boolean expanded;

    public PenjualanPerArtikelItem(String namaArtikel, String penjualanKotorArtikel, String jmlTerjualArtikel, String totPenjualanArtikel, String pajakArtikel) {
        this.namaArtikel = namaArtikel;
        this.penjualanKotorArtikel = penjualanKotorArtikel;
        this.jmlTerjualArtikel = jmlTerjualArtikel;
        this.totPenjualanArtikel = totPenjualanArtikel;
        this.pajakArtikel = pajakArtikel;
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

    public String getPajakArtikel() {
        return pajakArtikel;
    }

    public void setPajakArtikel(String pajakArtikel) {
        this.pajakArtikel = pajakArtikel;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
