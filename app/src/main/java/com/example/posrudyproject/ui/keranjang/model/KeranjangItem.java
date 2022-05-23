package com.example.posrudyproject.ui.keranjang.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KeranjangItem implements Serializable {
    @SerializedName("foto_barang")
    private String foto_barang;

    @SerializedName("type_name")
    private String tipeBarang;

    @SerializedName("sku_code")
    private String skuCode;

    @SerializedName("artikel")
    private String artikelBarang;

    @SerializedName("nama_barang")
    private String namaBarang;

    @SerializedName("harga_jual")
    private String hargaBarang;

    @SerializedName("harga_baru")
    private String harga_baru;

    @SerializedName("harga_baru_remark")
    private String harga_baru_remark;

    @SerializedName("kuantitas")
    private String jumlahBarang;

    @SerializedName("total_harga_barang")
    private String totalHargaBarang;

    @SerializedName("kuantitas")
    private String kuantitasBarang;

    public KeranjangItem(String foto_barang, String tipeBarang,String skuCode,
                         String artikelBarang, String namaBarang, String hargaBarang,
                         String harga_baru,String harga_baru_remark, String jumlahBarang,
                         String totalHargaBarang, String kuantitasBarang) {
        this.foto_barang = foto_barang;
        this.tipeBarang = tipeBarang;
        this.skuCode = skuCode;
        this.artikelBarang = artikelBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
        this.harga_baru = harga_baru;
        this.harga_baru_remark = harga_baru_remark;
        this.jumlahBarang = jumlahBarang;
        this.totalHargaBarang = totalHargaBarang;
        this.kuantitasBarang = kuantitasBarang;
    }

    public String getImBarang() {
        return foto_barang;
    }

    public void setImBarang(String imBarang) {
        this.foto_barang = foto_barang;
    }

    public String getTipeBarang() {
        return tipeBarang;
    }

    public void setTipeBarang(String tipeBarang) {
        this.tipeBarang = tipeBarang;
    }

    public String getFoto_barang() {
        return foto_barang;
    }

    public void setFoto_barang(String foto_barang) {
        this.foto_barang = foto_barang;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getArtikelBarang() {
        return artikelBarang;
    }

    public void setArtikelBarang(String artikelBarang) {
        this.artikelBarang = artikelBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(String hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public String getHarga_baru() {
        return harga_baru;
    }

    public void setHarga_baru(String harga_baru) {
        this.harga_baru = harga_baru;
    }

    public String getHarga_baru_remark() {
        return harga_baru_remark;
    }

    public void setHarga_baru_remark(String harga_baru_remark) {
        this.harga_baru_remark = harga_baru_remark;
    }

    public String getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(String jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public String getTotalHargaBarang() {
        return totalHargaBarang;
    }

    public void setTotalHargaBarang(String totalHargaBarang) {
        this.totalHargaBarang = totalHargaBarang;
    }

    public String getKuantitasBarang() {
        return kuantitasBarang;
    }

    public void setKuantitasBarang(String kuantitasBarang) {
        this.kuantitasBarang = kuantitasBarang;
    }
}
