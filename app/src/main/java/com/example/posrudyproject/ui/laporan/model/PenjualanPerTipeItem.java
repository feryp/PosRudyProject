package com.example.posrudyproject.ui.laporan.model;

public class PenjualanPerTipeItem {

    private String namaTipe,
            penjualanKotorTipe,
            jmlTerjualTipe,
            totPenjualanTipe,
            pajakTipe;

    private boolean expanded;

    public PenjualanPerTipeItem(String namaTipe, String penjualanKotorTipe, String jmlTerjualTipe, String totPenjualanTipe, String pajakTipe) {
        this.namaTipe = namaTipe;
        this.penjualanKotorTipe = penjualanKotorTipe;
        this.jmlTerjualTipe = jmlTerjualTipe;
        this.totPenjualanTipe = totPenjualanTipe;
        this.pajakTipe = pajakTipe;
    }

    public String getNamaTipe() {
        return namaTipe;
    }

    public void setNamaTipe(String namaTipe) {
        this.namaTipe = namaTipe;
    }

    public String getPenjualanKotorTipe() {
        return penjualanKotorTipe;
    }

    public void setPenjualanKotorTipe(String penjualanKotorTipe) {
        this.penjualanKotorTipe = penjualanKotorTipe;
    }

    public String getJmlTerjualTipe() {
        return jmlTerjualTipe;
    }

    public void setJmlTerjualTipe(String jmlTerjualTipe) {
        this.jmlTerjualTipe = jmlTerjualTipe;
    }

    public String getTotPenjualanTipe() {
        return totPenjualanTipe;
    }

    public void setTotPenjualanTipe(String totPenjualanTipe) {
        this.totPenjualanTipe = totPenjualanTipe;
    }

    public String getPajakTipe() {
        return pajakTipe;
    }

    public void setPajakTipe(String pajakTipe) {
        this.pajakTipe = pajakTipe;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
