package com.example.posrudyproject.ui.laporan.model;

public class PenjualanPerKategoriItem {

    private String namaKategori,
            penjualanKotorKategori,
            jmlTerjualKategori,
            totPenjualanKategori,
            pajakKategori;

    private boolean expanded;

    public PenjualanPerKategoriItem(String namaKategori, String penjualanKotorKategori, String jmlTerjualKategori, String totPenjualanKategori, String pajakKategori) {
        this.namaKategori = namaKategori;
        this.penjualanKotorKategori = penjualanKotorKategori;
        this.jmlTerjualKategori = jmlTerjualKategori;
        this.totPenjualanKategori = totPenjualanKategori;
        this.pajakKategori = pajakKategori;
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

    public String getPajakKategori() {
        return pajakKategori;
    }

    public void setPajakKategori(String pajakKategori) {
        this.pajakKategori = pajakKategori;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
