package com.example.posrudyproject.ui.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseItem implements Serializable {
    @SerializedName("token")
    private String token;

    @SerializedName("id_pengguna")
    private int id_pengguna;

    @SerializedName("namaPengguna")
    private String namaPengguna;

    @SerializedName("id_office")
    private int id_office;

    @SerializedName("lokasi_office")
    private String lokasi_office;

    @SerializedName("id_store")
    private int id_store;

    @SerializedName("lokasi_store")
    private String lokasi_store;

    @SerializedName("akses_modul")
    private String[] akses_modul;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId_pengguna() {
        return id_pengguna;
    }

    public void setId_pengguna(int id_pengguna) {
        this.id_pengguna = id_pengguna;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        this.namaPengguna = namaPengguna;
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

    public String[] getAkses_modul() {
        return akses_modul;
    }

    public void setAkses_modul(String[] akses_modul) {
        this.akses_modul = akses_modul;
    }
}
