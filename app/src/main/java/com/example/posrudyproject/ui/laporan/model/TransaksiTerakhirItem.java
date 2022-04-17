package com.example.posrudyproject.ui.laporan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransaksiTerakhirItem implements Serializable {

    @SerializedName("total")
    private String nominalTransaksi;

    @SerializedName("id_transaksi")
    private String invoiceTransaksi;

    @SerializedName("metode_bayar")
    private String statusTransaksi;

    @SerializedName("tanggal_transaksi")
    private String waktuTransaksi;

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
