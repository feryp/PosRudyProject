package com.example.posrudyproject.ui.laporan.model;

public class TransaksiTerakhirItem {

    private String nominalTransaksi, invoiceTransaksi, statusTransaksi, waktuTransaksi;

    public TransaksiTerakhirItem(String nominalTransaksi, String invoiceTransaksi, String statusTransaksi, String waktuTransaksi) {
        this.nominalTransaksi = nominalTransaksi;
        this.invoiceTransaksi = invoiceTransaksi;
        this.statusTransaksi = statusTransaksi;
        this.waktuTransaksi = waktuTransaksi;
    }

    public String getNominalTransaksi() {
        return nominalTransaksi;
    }

    public void setNominalTransaksi(String nominalTransaksi) {
        this.nominalTransaksi = nominalTransaksi;
    }

    public String getInvoiceTransaksi() {
        return invoiceTransaksi;
    }

    public void setInvoiceTransaksi(String invoiceTransaksi) {
        this.invoiceTransaksi = invoiceTransaksi;
    }

    public String getStatusTransaksi() {
        return statusTransaksi;
    }

    public void setStatusTransaksi(String statusTransaksi) {
        this.statusTransaksi = statusTransaksi;
    }

    public String getWaktuTransaksi() {
        return waktuTransaksi;
    }

    public void setWaktuTransaksi(String waktuTransaksi) {
        this.waktuTransaksi = waktuTransaksi;
    }
}
