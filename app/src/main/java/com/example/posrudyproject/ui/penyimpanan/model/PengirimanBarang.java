package com.example.posrudyproject.ui.penyimpanan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PengirimanBarang implements Serializable {
    @SerializedName("id_store_asal")
    private int id_store_asal;

    @SerializedName("lokasi_store_asal")
    private String lokasi_store_asal;

    @SerializedName("id_store_tujuan")
    private int id_store_tujuan;

    @SerializedName("lokasi_store_tujuan")
    private String lokasi_store_tujuan;

    @SerializedName("id_karyawan")
    private int id_karyawan;

    @SerializedName("nama_karyawan")
    private String nama_karyawan;

    @SerializedName("keterangan")
    private String keterangan;

    @SerializedName("detailPengirimanList")
    private List<PemindahanBarangItem> detailPengirimanList;

    public PengirimanBarang(int id_store_asal, String lokasi_store_asal, int id_store_tujuan, String lokasi_store_tujuan, int id_karyawan, String nama_karyawan, String keterangan, List<PemindahanBarangItem> detailPengirimanList) {
        this.id_store_asal = id_store_asal;
        this.lokasi_store_asal = lokasi_store_asal;
        this.id_store_tujuan = id_store_tujuan;
        this.lokasi_store_tujuan = lokasi_store_tujuan;
        this.id_karyawan = id_karyawan;
        this.nama_karyawan = nama_karyawan;
        this.keterangan = keterangan;
        this.detailPengirimanList = detailPengirimanList;
    }

    public int getId_store_asal() {
        return id_store_asal;
    }

    public void setId_store_asal(int id_store_asal) {
        this.id_store_asal = id_store_asal;
    }

    public String getLokasi_store_asal() {
        return lokasi_store_asal;
    }

    public void setLokasi_store_asal(String lokasi_store_asal) {
        this.lokasi_store_asal = lokasi_store_asal;
    }

    public int getId_store_tujuan() {
        return id_store_tujuan;
    }

    public void setId_store_tujuan(int id_store_tujuan) {
        this.id_store_tujuan = id_store_tujuan;
    }

    public String getLokasi_store_tujuan() {
        return lokasi_store_tujuan;
    }

    public void setLokasi_store_tujuan(String lokasi_store_tujuan) {
        this.lokasi_store_tujuan = lokasi_store_tujuan;
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

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public List<PemindahanBarangItem> getDetailPengirimanList() {
        return detailPengirimanList;
    }

    public void setDetailPengirimanList(List<PemindahanBarangItem> detailPengirimanList) {
        this.detailPengirimanList = detailPengirimanList;
    }
}
