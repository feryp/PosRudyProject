package com.example.posrudyproject.ui.rekapKas.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TutupKasirItem implements Serializable {
    @SerializedName("uangMasukTunai")
    private Double uangMasukTunai;

    @SerializedName("uangMasukNonTunai")
    private Double uangMasukNonTunai;

    @SerializedName("id_store")
    private int id_store;

    @SerializedName("lokasi_store")
    private String lokasi_store;

    @SerializedName("id_karyawan")
    private int id_karyawan;

    @SerializedName("nama_karyawan")
    private String nama_karyawan;

    @SerializedName("catatan")
    private String catatan;

    public Double getUangMasukTunai() {
        return uangMasukTunai;
    }
    public void setUangMasukTunai(Double uangMasukTunai) {
        this.uangMasukTunai = uangMasukTunai;
    }
    public Double getUangMasukNonTunai() {
        return uangMasukNonTunai;
    }
    public void setUangMasukNonTunai(Double uangMasukNonTunai) {
        this.uangMasukNonTunai = uangMasukNonTunai;
    }

    public int getId_store() {
        return id_store;
    }
    public void setId_store(int id_store) {
        this.id_store = id_store;
    }
    public String getLokasi_store() {
        return lokasi_store;
    }
    public void setLokasi_store(String lokasi_store) {
        this.lokasi_store = lokasi_store;
    }
    public int getId_karyawan() {
        return id_karyawan;
    }
    public void setId_karyawan(int id_karyawan) {
        this.id_karyawan = id_karyawan;
    }
    public String getNama_karyawan() {
        return nama_karyawan;
    }
    public void setNama_karyawan(String nama_karyawan) {
        this.nama_karyawan = nama_karyawan;
    }
    public String getCatatan() {
        return catatan;
    }
    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}
