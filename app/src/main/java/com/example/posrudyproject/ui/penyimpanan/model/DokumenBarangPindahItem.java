package com.example.posrudyproject.ui.penyimpanan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DokumenBarangPindahItem implements Serializable {
    @SerializedName("pengiriman_code")
    private String noDocBarang;

    @SerializedName("tanggal_pengiriman")
    private String waktuBarangPindah;

    @SerializedName("total_pindah")
    private String jumlahBarangPindah;

    @SerializedName("id_store_asal")
    private int id_store_asal;

    @SerializedName("id_store_tujuan")
    private int id_store_tujuan;

    @SerializedName("lokasi_store_asal")
    private String lokasi_store_asal;

    @SerializedName("lokasi_store_tujuan")
    private String lokasi_store_tujuan;

    public DokumenBarangPindahItem(String noDocBarang, String waktuBarangPindah, String jumlahBarangPindah) {
        this.noDocBarang = noDocBarang;
        this.waktuBarangPindah = waktuBarangPindah;
        this.jumlahBarangPindah = jumlahBarangPindah;
    }

    public String getNoDocBarang() {
        return noDocBarang;
    }

    public void setNoDocBarang(String noDocBarang) {
        this.noDocBarang = noDocBarang;
    }

    public String getWaktuBarangPindah() {
        return waktuBarangPindah;
    }

    public void setWaktuBarangPindah(String waktuBarangPindah) {
        this.waktuBarangPindah = waktuBarangPindah;
    }

    public String getJumlahBarangPindah() {
        return jumlahBarangPindah;
    }

    public void setJumlahBarangPindah(String jumlahBarangPindah) {
        this.jumlahBarangPindah = jumlahBarangPindah;
    }

    public int getId_store_asal() {
        return id_store_asal;
    }

    public void setId_store_asal(int id_store_asal) {
        this.id_store_asal = id_store_asal;
    }

    public int getId_store_tujuan() {
        return id_store_tujuan;
    }

    public void setId_store_tujuan(int id_store_tujuan) {
        this.id_store_tujuan = id_store_tujuan;
    }

    public String getLokasi_store_asal() {
        return lokasi_store_asal;
    }

    public void setLokasi_store_asal(String lokasi_store_asal) {
        this.lokasi_store_asal = lokasi_store_asal;
    }

    public String getLokasi_store_tujuan() {
        return lokasi_store_tujuan;
    }

    public void setLokasi_store_tujuan(String lokasi_store_tujuan) {
        this.lokasi_store_tujuan = lokasi_store_tujuan;
    }
}
