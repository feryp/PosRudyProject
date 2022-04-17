package com.example.posrudyproject.ui.laporan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubRiwayatTransaksiItem implements Serializable {
    @SerializedName("total")
    private String nominalTransaksi;

    @SerializedName("id_transaksi")
    private String noInvTransaksi;

    @SerializedName("tanggal_transaksi")
    private String jamTransaksi;

    public SubRiwayatTransaksiItem(String nominalTransaksi, String noInvTransaksi, String jamTransaksi) {
        this.nominalTransaksi = nominalTransaksi;
        this.noInvTransaksi = noInvTransaksi;
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

    public String getJamTransaksi() {
        return jamTransaksi;
    }

    public void setJamTransaksi(String jamTransaksi) {
        this.jamTransaksi = jamTransaksi;
    }
}
