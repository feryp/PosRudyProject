package com.example.posrudyproject.ui.laporan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LaporanPenjualItem implements Serializable {

    @SerializedName("id_karyawan")
    private int id_karyawan;

    @SerializedName("nama_karyawan")
    private String namaPenjual;

    @SerializedName("total")
    private String nominalTransaksiPenjual;

    @SerializedName("jml_transaksi")
    private String totalTransaksiPenjual;

    public LaporanPenjualItem(int id_karyawan, String namaPenjual, String nominalTransaksiPenjual, String totalTransaksiPenjual) {
        this.id_karyawan = id_karyawan;
        this.namaPenjual = namaPenjual;
        this.nominalTransaksiPenjual = nominalTransaksiPenjual;
        this.totalTransaksiPenjual = totalTransaksiPenjual;
    }

    public int getId_karyawan() {
        return id_karyawan;
    }

    public void setId_karyawan(int id_karyawan) {
        this.id_karyawan = id_karyawan;
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
