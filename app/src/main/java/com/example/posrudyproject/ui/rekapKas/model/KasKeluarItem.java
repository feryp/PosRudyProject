package com.example.posrudyproject.ui.rekapKas.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KasKeluarItem implements Serializable {

    @SerializedName("nominalKasKeluar")
    private String nominalKasKeluar;

    @SerializedName("waktuKeluar")
    private String waktuKasKeluar;

    @SerializedName("id_store")
    private int idToko;

    @SerializedName("lokasi_store")
    private String namaToko;

    @SerializedName("id_karyawan")
    private int idPenjual;

    @SerializedName("nama_karyawan")
    private String penjualKasKeluar;
    @SerializedName("catatan")
    private String catatanKasKeluar;

    public String getNominalKasKeluar() {
        return nominalKasKeluar;
    }

    public void setNominalKasKeluar(String nominalKasKeluar) {
        this.nominalKasKeluar = nominalKasKeluar;
    }

    public String getWaktuKasKeluar() {
        return waktuKasKeluar;
    }

    public void setWaktuKasKeluar(String waktuKasKeluar) {
        this.waktuKasKeluar = waktuKasKeluar;
    }

    public int getIdToko() {
        return idToko;
    }

    public void setIdToko(int idToko) {
        this.idToko = idToko;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public int getIdPenjual() {
        return idPenjual;
    }

    public void setIdPenjual(int idPenjual) {
        this.idPenjual = idPenjual;
    }

    public String getPenjualKasKeluar() {
        return penjualKasKeluar;
    }

    public void setPenjualKasKeluar(String penjualKasKeluar) {
        this.penjualKasKeluar = penjualKasKeluar;
    }

    public String getCatatanKasKeluar() {
        return catatanKasKeluar;
    }

    public void setCatatanKasKeluar(String catatanKasKeluar) {
        this.catatanKasKeluar = catatanKasKeluar;
    }
}
