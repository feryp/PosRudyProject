package com.example.posrudyproject.ui.laporan.model;

public class LaporanPenjualItem {

    private String namaPenjual, nominalTransaksiPenjual, totalTransaksiPenjual;

    public LaporanPenjualItem(String namaPenjual, String nominalTransaksiPenjual, String totalTransaksiPenjual) {
        this.namaPenjual = namaPenjual;
        this.nominalTransaksiPenjual = nominalTransaksiPenjual;
        this.totalTransaksiPenjual = totalTransaksiPenjual;
    }

    public String getNamaPenjual() {
        return namaPenjual;
    }

    public void setNamaPenjual(String namaPenjual) {
        this.namaPenjual = namaPenjual;
    }

    public String getNominalTransaksiPenjual() {
        return nominalTransaksiPenjual;
    }

    public void setNominalTransaksiPenjual(String nominalTransaksiPenjual) {
        this.nominalTransaksiPenjual = nominalTransaksiPenjual;
    }

    public String getTotalTransaksiPenjual() {
        return totalTransaksiPenjual;
    }

    public void setTotalTransaksiPenjual(String totalTransaksiPenjual) {
        this.totalTransaksiPenjual = totalTransaksiPenjual;
    }
}
