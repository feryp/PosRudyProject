package com.example.posrudyproject.ui.laporan.model;

public class RiwayatTransaksiPenjualItem {

    private String waktuTransaksi, nominalTransaksi, namaToko, produkTerjual;

    public RiwayatTransaksiPenjualItem(String waktuTransaksi, String nominalTransaksi, String namaToko, String produkTerjual) {
        this.waktuTransaksi = waktuTransaksi;
        this.nominalTransaksi = nominalTransaksi;
        this.namaToko = namaToko;
        this.produkTerjual = produkTerjual;
    }

    public String getWaktuTransaksi() {
        return waktuTransaksi;
    }

    public void setWaktuTransaksi(String waktuTransaksi) {
        this.waktuTransaksi = waktuTransaksi;
    }

    public String getNominalTransaksi() {
        return nominalTransaksi;
    }

    public void setNominalTransaksi(String nominalTransaksi) {
        this.nominalTransaksi = nominalTransaksi;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getProdukTerjual() {
        return produkTerjual;
    }

    public void setProdukTerjual(String produkTerjual) {
        this.produkTerjual = produkTerjual;
    }
}
