package com.example.posrudyproject.ui.pembayaran.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Penjualan implements Serializable {
    @SerializedName("tanggal_transaksi")
    private String tanggal_transaksi;

    @SerializedName("id_transaksi")
    private String id_transaksi;

    @SerializedName("id_store")
    private int id_store;

    @SerializedName("lokasi_store")
    private String lokasi_store;

    @SerializedName("no_hp_pelanggan")
    private String no_hp_pelanggan;

    @SerializedName("nama_pelanggan")
    private String nama_pelanggan;

    @SerializedName("id_karyawan")
    private int id_karyawan;

    @SerializedName("nama_karyawan")
    private String nama_karyawan;

    @SerializedName("diskon")
    private double diskon;

    @SerializedName("metode_bayar")
    private String metode_bayar;

    @SerializedName("bank_name")
    private String bankName;

    @SerializedName("no_rek")
    private String noRek;

    @SerializedName("ekspedisi")
    private String ekspedisi;

    @SerializedName("ongkir")
    private Double ongkir;

    @SerializedName("total")
    private Double total;

    @SerializedName("kembalian")
    private Double kembalian;

    @SerializedName("detailPesananList")
    private List<DetailPesanan> detailPesananList;

    public Penjualan(String tanggal_transaksi,String id_transaksi,int id_store, String lokasi_store, String no_hp_pelanggan, String nama_pelanggan, int id_karyawan, String nama_karyawan, double diskon,
                     String metode_bayar, String bankName, String noRek,
                     String ekspedisi, Double ongkir, Double total, Double kembalian, List<DetailPesanan> detailPesananList) {
        this.tanggal_transaksi = tanggal_transaksi;
        this.id_transaksi = id_transaksi;
        this.id_store = id_store;
        this.lokasi_store = lokasi_store;
        this.no_hp_pelanggan = no_hp_pelanggan;
        this.nama_pelanggan = nama_pelanggan;
        this.id_karyawan = id_karyawan;
        this.nama_karyawan = nama_karyawan;
        this.diskon = diskon;
        this.metode_bayar = metode_bayar;
        this.bankName = bankName;
        this.noRek = noRek;
        this.ekspedisi = ekspedisi;
        this.ongkir = ongkir;
        this.total = total;
        this.kembalian = kembalian;
        this.detailPesananList = detailPesananList;
    }

    public String getTanggal_transaksi() {
        return tanggal_transaksi;
    }

    public void setTanggal_transaksi(String tanggal_transaksi) {
        this.tanggal_transaksi = tanggal_transaksi;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getNoRek() {
        return noRek;
    }

    public void setNoRek(String noRek) {
        this.noRek = noRek;
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

    public String getLokasi_store() {
        return lokasi_store;
    }

    public void setLokasi_store(String lokasi_store) {
        this.lokasi_store = lokasi_store;
    }

    public String getNo_hp_pelanggan() {
        return no_hp_pelanggan;
    }

    public void setNo_hp_pelanggan(String no_hp_pelanggan) {
        this.no_hp_pelanggan = no_hp_pelanggan;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public void setNama_pelanggan(String nama_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
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

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    public String getMetode_bayar() {
        return metode_bayar;
    }

    public void setMetode_bayar(String metode_bayar) {
        this.metode_bayar = metode_bayar;
    }

    public String getEkspedisi() {
        return ekspedisi;
    }

    public void setEkspedisi(String ekspedisi) {
        this.ekspedisi = ekspedisi;
    }

    public Double getOngkir() {
        return ongkir;
    }

    public void setOngkir(Double ongkir) {
        this.ongkir = ongkir;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getKembalian() {
        return kembalian;
    }

    public void setKembalian(Double kembalian) {
        this.kembalian = kembalian;
    }

    public List<DetailPesanan> getDetailPesananList() {
        return detailPesananList;
    }

    public void setDetailPesananList(List<DetailPesanan> detailPesananList) {
        this.detailPesananList = detailPesananList;
    }
}
