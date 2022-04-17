package com.example.posrudyproject.ui.laporan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RiwayatTransaksiItem implements Serializable {
    @SerializedName("tanggal_transaksi")
    private String tglTransaksi;

    @SerializedName("total")
    private String totalTransaksi;

    @SerializedName("detailPesananList")
    private List<SubRiwayatTransaksiItem> subRiwayatTransaksiItems;

    private boolean expanded;

    public RiwayatTransaksiItem(String tglTransaksi, String totalTransaksi, List<SubRiwayatTransaksiItem> subRiwayatTransaksiItems) {
        this.tglTransaksi = tglTransaksi;
        this.totalTransaksi = totalTransaksi;
        this.subRiwayatTransaksiItems = subRiwayatTransaksiItems;
    }

    public String getTglTransaksi() {
        return tglTransaksi;
    }

    public void setTglTransaksi(String tglTransaksi) {
        this.tglTransaksi = tglTransaksi;
    }

    public String getTotalTransaksi() {
        return totalTransaksi;
    }

    public void setTotalTransaksi(String totalTransaksi) {
        this.totalTransaksi = totalTransaksi;
    }

    public List<SubRiwayatTransaksiItem> getSubRiwayatTransaksiItems() {
        return subRiwayatTransaksiItems;
    }

    public void setSubRiwayatTransaksiItems(List<SubRiwayatTransaksiItem> subRiwayatTransaksiItems) {
        this.subRiwayatTransaksiItems = subRiwayatTransaksiItems;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
