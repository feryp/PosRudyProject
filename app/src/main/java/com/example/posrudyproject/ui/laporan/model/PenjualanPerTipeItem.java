package com.example.posrudyproject.ui.laporan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PenjualanPerTipeItem implements Serializable {

    @SerializedName("type_name")
    private String namaTipe;

    @SerializedName("harga_jual")
    private String penjualanKotorTipe;

    @SerializedName("total_terjual")
    private String jmlTerjualTipe;

    @SerializedName("penjualan_kotor")
    private String totPenjualanTipe;

    private boolean expanded;

    public PenjualanPerTipeItem(String namaTipe, String penjualanKotorTipe, String jmlTerjualTipe, String totPenjualanTipe) {
        this.namaTipe = namaTipe;
        this.penjualanKotorTipe = penjualanKotorTipe;
        this.jmlTerjualTipe = jmlTerjualTipe;
        this.totPenjualanTipe = totPenjualanTipe;
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

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
