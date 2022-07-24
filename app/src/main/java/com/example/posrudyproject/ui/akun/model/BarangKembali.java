package com.example.posrudyproject.ui.akun.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BarangKembali implements Serializable {
    @SerializedName("id_transaksi")
    private String id_transaksi;

    @SerializedName("id_store")
    private int id_store;

    @SerializedName("sku_code")
    private String sku_code;

    @SerializedName("artikel")
    private String artikel;

    @SerializedName("kuantitas")
    private Double kuantitas;

    @SerializedName("harga_jual")
    private Double harga_jual;

    @SerializedName("keterangan")
    private String keterangan;

    public BarangKembali(String id_transaksi, int id_store, String sku_code, String artikel, Double kuantitas, Double harga_jual, String keterangan) {
        this.id_transaksi = id_transaksi;
        this.id_store = id_store;
        this.sku_code = sku_code;
        this.artikel = artikel;
        this.kuantitas = kuantitas;
        this.harga_jual = harga_jual;
        this.keterangan = keterangan;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public int getId_store() {
        return id_store;
    }

    public void setId_store(int id_store) {
        this.id_store = id_store;
    }

    public String getSku_code() {
        return sku_code;
    }

    public void setSku_code(String sku_code) {
        this.sku_code = sku_code;
    }

    public String getArtikel() {
        return artikel;
    }

    public void setArtikel(String artikel) {
        this.artikel = artikel;
    }

    public Double getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(Double kuantitas) {
        this.kuantitas = kuantitas;
    }

    public Double getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(Double harga_jual) {
        this.harga_jual = harga_jual;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
