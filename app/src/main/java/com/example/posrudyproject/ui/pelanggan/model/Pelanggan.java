package com.example.posrudyproject.ui.pelanggan.model;

import  com.google.gson.annotations.Expose;
import  com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pelanggan implements Serializable {
    @SerializedName("id")
    private Long id;

    @SerializedName("nama_pelanggan")
    private String nama_pelanggan;

    @SerializedName("no_hp")
    private String no_hp;

    @SerializedName("email")
    private String email;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("total_kunjungan")
    private double total_kunjungan;

    @SerializedName("kuantitas")
    private double kuantitas;

    @SerializedName("poin")
    private double poin;

    @SerializedName("total_pembelian")
    private double total_pembelian;

    public Pelanggan(Long id, String nama_pelanggan, String no_hp, String email, String alamat, double total_kunjungan, double kuantitas, double poin, double total_pembelian) {
        this.id = id;
        this.nama_pelanggan = nama_pelanggan;
        this.no_hp = no_hp;
        this.email = email;
        this.alamat = alamat;
        this.total_kunjungan = total_kunjungan;
        this.kuantitas = kuantitas;
        this.poin = poin;
        this.total_pembelian = total_pembelian;
    }

    public Pelanggan() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public void setNama_pelanggan(String nama_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public double getTotal_kunjungan() {
        return total_kunjungan;
    }

    public void setTotal_kunjungan(double total_kunjungan) {
        this.total_kunjungan = total_kunjungan;
    }

    public double getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(double kuantitas) {
        this.kuantitas = kuantitas;
    }

    public double getPoin() {
        return poin;
    }

    public void setPoin(double poin) {
        this.poin = poin;
    }

    public double getTotal_pembelian() {
        return total_pembelian;
    }

    public void setTotal_pembelian(double total_pembelian) {
        this.total_pembelian = total_pembelian;
    }

    @Override
    public String toString() {
        return "Pelanggan{" +
                "nama_pelanggan='" + nama_pelanggan + '\'' +
                ", no_hp='" + no_hp + '\'' +
                ", email='" + email + '\'' +
                ", alamat='" + alamat + '\'' +
                ", total_kunjungan=" + total_kunjungan +
                ", kuantitas=" + kuantitas +
                ", poin=" + poin +
                ", total_pembelian=" + total_pembelian +
                '}';
    }
}
