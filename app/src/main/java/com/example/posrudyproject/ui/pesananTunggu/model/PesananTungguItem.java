package com.example.posrudyproject.ui.pesananTunggu.model;

import java.util.List;

public class PesananTungguItem {
    private String noPesanan;
    private String tglPesanan;
    private String jamPesanan;
    private String totalHargaPesanan;
    private String ketPesanan;
    private String pelangganPesanan;
    private List<BarangPesananTungguItem> barangPesanan;

    public PesananTungguItem(String noPesanan, String tglPesanan, String jamPesanan, String totalHargaPesanan, String ketPesanan, String pelangganPesanan, List<BarangPesananTungguItem> barangPesanan) {
        this.noPesanan = noPesanan;
        this.tglPesanan = tglPesanan;
        this.jamPesanan = jamPesanan;
        this.totalHargaPesanan = totalHargaPesanan;
        this.ketPesanan = ketPesanan;
        this.pelangganPesanan = pelangganPesanan;
        this.barangPesanan = barangPesanan;
    }

    public String getNoPesanan() {
        return noPesanan;
    }

    public void setNoPesanan(String noPesanan) {
        this.noPesanan = noPesanan;
    }

    public String getTglPesanan() {
        return tglPesanan;
    }

    public void setTglPesanan(String tglPesanan) {
        this.tglPesanan = tglPesanan;
    }

    public String getJamPesanan() {
        return jamPesanan;
    }

    public void setJamPesanan(String jamPesanan) {
        this.jamPesanan = jamPesanan;
    }

    public String getTotalHargaPesanan() {
        return totalHargaPesanan;
    }

    public void setTotalHargaPesanan(String totalHargaPesanan) {
        this.totalHargaPesanan = totalHargaPesanan;
    }

    public String getKetPesanan() {
        return ketPesanan;
    }

    public void setKetPesanan(String ketPesanan) {
        this.ketPesanan = ketPesanan;
    }

    public String getPelangganPesanan() {
        return pelangganPesanan;
    }

    public void setPelangganPesanan(String pelangganPesanan) {
        this.pelangganPesanan = pelangganPesanan;
    }

    public List<BarangPesananTungguItem> getBarangPesanan() {
        return barangPesanan;
    }

    public void setBarangPesanan(List<BarangPesananTungguItem> barangPesanan) {
        this.barangPesanan = barangPesanan;
    }
}
