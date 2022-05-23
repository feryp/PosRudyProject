package com.example.posrudyproject.ui.pembayaran.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetailPesanan implements Serializable {
    @SerializedName("id_store")
    private int id_store;

    @SerializedName("lokasi_store")
    private String lokasi_store;

    @SerializedName("sku_code")
    private String sku_code;

    @SerializedName("type_name")
    private String type_name;

    @SerializedName("nama_kategori")
    private String nama_kategori;

    @SerializedName("artikel")
    private String artikel;

    @SerializedName("nama_barang")
    private String nama_barang;

    @SerializedName("harga")
    private Double harga;

    @SerializedName("harga_baru")
    private Double harga_baru;

    @SerializedName("harga_baru_remark")
    private String harga_baru_remark;

    @SerializedName("kuantitas")
    private Double kuantitas;

    @SerializedName("total")
    private Double total;

    public DetailPesanan(int id_store, String lokasi_store, String sku_code,
                         String type_name, String nama_kategori, String artikel,
                         String nama_barang, Double harga, Double harga_baru,
                         String harga_baru_remark, Double kuantitas, Double total) {
        this.id_store = id_store;
        this.lokasi_store = lokasi_store;
        this.sku_code = sku_code;
        this.type_name = type_name;
        this.nama_kategori = nama_kategori;
        this.artikel = artikel;
        this.nama_barang = nama_barang;
        this.harga = harga;
        this.harga_baru = harga_baru;
        this.harga_baru_remark = harga_baru_remark;
        this.kuantitas = kuantitas;
        this.total = total;
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

    public String getSku_code() {
        return sku_code;
    }

    public void setSku_code(String sku_code) {
        this.sku_code = sku_code;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getArtikel() {
        return artikel;
    }

    public void setArtikel(String artikel) {
        this.artikel = artikel;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public Double getHarga_baru() {
        return harga_baru;
    }

    public void setHarga_baru(Double harga_baru) {
        this.harga_baru = harga_baru;
    }

    public String getHarga_baru_remark() {
        return harga_baru_remark;
    }

    public void setHarga_baru_remark(String harga_baru_remark) {
        this.harga_baru_remark = harga_baru_remark;
    }

    public Double getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(Double kuantitas) {
        this.kuantitas = kuantitas;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
