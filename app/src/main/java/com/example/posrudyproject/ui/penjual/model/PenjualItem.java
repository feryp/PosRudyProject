package com.example.posrudyproject.ui.penjual.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class PenjualItem implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("tanggal_join")
    private Date tanggal_join;

    @SerializedName("nama_karyawan")
    private String nama_karyawan;

    @SerializedName("id_office")
    private int id_office;

    @SerializedName("lokasi_office")
    private String lokasi_office;

    @SerializedName("id_store")
    private int id_store;

    @SerializedName("lokasi_store")
    private String lokasi_store;

    @SerializedName("jabatan")
    private String jabatan;

    @SerializedName("no_hp")
    private String no_hp;

    @SerializedName("email")
    private String email;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("total_transaksi")
    private double total_transaksi;

    @SerializedName("rowstatus")
    private int rowstatus;

    @SerializedName("image")
    private String image;

    public PenjualItem(int id, Date tanggal_join, String nama_karyawan, int id_office, String lokasi_office, int id_store, String lokasi_store, String jabatan, String no_hp, String email, String alamat, double total_transaksi, int rowstatus, String image) {
        this.id = id;
        this.tanggal_join = tanggal_join;
        this.nama_karyawan = nama_karyawan;
        this.id_office = id_office;
        this.lokasi_office = lokasi_office;
        this.id_store = id_store;
        this.lokasi_store = lokasi_store;
        this.jabatan = jabatan;
        this.no_hp = no_hp;
        this.email = email;
        this.alamat = alamat;
        this.total_transaksi = total_transaksi;
        this.rowstatus = rowstatus;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTanggal_join() {
        return tanggal_join;
    }

    public void setTanggal_join(Date tanggal_join) {
        this.tanggal_join = tanggal_join;
    }

    public String getNama_karyawan() {
        return nama_karyawan;
    }

    public void setNama_karyawan(String nama_karyawan) {
        this.nama_karyawan = nama_karyawan;
    }

    public int getId_office() {
        return id_office;
    }

    public void setId_office(int id_office) {
        this.id_office = id_office;
    }

    public String getLokasi_office() {
        return lokasi_office;
    }

    public void setLokasi_office(String lokasi_office) {
        this.lokasi_office = lokasi_office;
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

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
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

    public double getTotal_transaksi() {
        return total_transaksi;
    }

    public void setTotal_transaksi(double total_transaksi) {
        this.total_transaksi = total_transaksi;
    }

    public int getRowstatus() {
        return rowstatus;
    }

    public void setRowstatus(int rowstatus) {
        this.rowstatus = rowstatus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
