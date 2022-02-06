package com.example.posrudyproject.ui.penjualan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KategoriItem implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("kategori_name")
    private String namaKetegori;

    public KategoriItem(int id, String namaKetegori) {
        this.id = id;
        this.namaKetegori = namaKetegori;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public KategoriItem(String namaKetegori) {
        this.namaKetegori = namaKetegori;
    }

    public String getNamaKetegori() {
        return namaKetegori;
    }

    public void setNamaKetegori(String namaKetegori) {
        this.namaKetegori = namaKetegori;
    }
}
