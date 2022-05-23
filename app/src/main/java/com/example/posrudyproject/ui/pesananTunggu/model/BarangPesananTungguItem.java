package com.example.posrudyproject.ui.pesananTunggu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BarangPesananTungguItem implements Serializable {
    @SerializedName("image")
    private String image;

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
    private String namaBarang;

    @SerializedName("harga")
    private Double harga;

    @SerializedName("harga_baru")
    private Double harga_baru;

    @SerializedName("harga_baru_remark")
    private String harga_baru_remark;

    @SerializedName("kuantitas")
    private Double kuantitasBarang;

    @SerializedName("total")
    private Double total;

    public BarangPesananTungguItem(String image,int id_store, String lokasi_store,
                                   String sku_code, String type_name, String nama_kategori,
                                   String artikel, String namaBarang, Double harga, Double harga_baru,
                                   String harga_baru_remark, Double kuantitasBarang, Double total) {
        this.image = image;
        this.id_store = id_store;
        this.lokasi_store = lokasi_store;
        this.sku_code = sku_code;
        this.type_name = type_name;
        this.nama_kategori = nama_kategori;
        this.artikel = artikel;
        this.namaBarang = namaBarang;
        this.harga = harga;
        this.harga_baru = harga_baru;
        this.harga_baru_remark = harga_baru_remark;
        this.kuantitasBarang = kuantitasBarang;
        this.total = total;
    }

    public Double getKuantitasBarang() {
        return kuantitasBarang;
    }

    public void setKuantitasBarang(Double kuantitasBarang) {
        this.kuantitasBarang = kuantitasBarang;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }
}
