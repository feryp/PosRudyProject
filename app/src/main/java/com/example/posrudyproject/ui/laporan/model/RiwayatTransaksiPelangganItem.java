package com.example.posrudyproject.ui.laporan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RiwayatTransaksiPelangganItem implements Serializable {

    @SerializedName("tanggal_transaksi")
    private String tglTransaksi;

    @SerializedName("total")
    private String totalTransaksi;

    @SerializedName("detailPesananList")
    private List<SubRiwayatTransaksiPelangganItem> subRiwayatTransaksiPelangganItems;

    private boolean expanded;

    public RiwayatTransaksiPelangganItem(String tglTransaksi, String totalTransaksi, List<SubRiwayatTransaksiPelangganItem> subRiwayatTransaksiPelangganItems) {
        this.tglTransaksi = tglTransaksi;
        this.totalTransaksi = totalTransaksi;
        this.subRiwayatTransaksiPelangganItems = subRiwayatTransaksiPelangganItems;
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

    public List<SubRiwayatTransaksiPelangganItem> getSubRiwayatTransaksiPelangganItems() {
        return subRiwayatTransaksiPelangganItems;
    }

    public void setSubRiwayatTransaksiPelangganItems(List<SubRiwayatTransaksiPelangganItem> subRiwayatTransaksiPelangganItems) {
        this.subRiwayatTransaksiPelangganItems = subRiwayatTransaksiPelangganItems;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
