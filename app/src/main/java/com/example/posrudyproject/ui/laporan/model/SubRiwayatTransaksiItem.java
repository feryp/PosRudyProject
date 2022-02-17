package com.example.posrudyproject.ui.laporan.model;

public class SubRiwayatTransaksiItem {
    private String nominalTransaksi, noInvTransaksi, metodePembayaranTransaksi, jamTransaksi;

    public SubRiwayatTransaksiItem(String nominalTransaksi, String noInvTransaksi, String metodePembayaranTransaksi, String jamTransaksi) {
        this.nominalTransaksi = nominalTransaksi;
        this.noInvTransaksi = noInvTransaksi;
        this.metodePembayaranTransaksi = metodePembayaranTransaksi;
        this.jamTransaksi = jamTransaksi;
    }

    public String getNominalTransaksi() {
        return nominalTransaksi;
    }

    public void setNominalTransaksi(String nominalTransaksi) {
        this.nominalTransaksi = nominalTransaksi;
    }

    public String getNoInvTransaksi() {
        return noInvTransaksi;
    }

    public void setNoInvTransaksi(String noInvTransaksi) {
        this.noInvTransaksi = noInvTransaksi;
    }

    public String getMetodePembayaranTransaksi() {
        return metodePembayaranTransaksi;
    }

    public void setMetodePembayaranTransaksi(String metodePembayaranTransaksi) {
        this.metodePembayaranTransaksi = metodePembayaranTransaksi;
    }

    public String getJamTransaksi() {
        return jamTransaksi;
    }

    public void setJamTransaksi(String jamTransaksi) {
        this.jamTransaksi = jamTransaksi;
    }
}
