package com.example.posrudyproject.ui.penyimpanan.model;

public class BarangMasukItem {
    private String tipeBarangMasuk;
    private String artikelBarangMasuk;
    private String namaBarangMasuk;
    private String waktuBarangMasuk;
    private String stokBarangMasuk;

    public BarangMasukItem(String tipeBarangMasuk, String artikelBarangMasuk, String namaBarangMasuk, String waktuBarangMasuk, String stokBarangMasuk) {
        this.tipeBarangMasuk = tipeBarangMasuk;
        this.artikelBarangMasuk = artikelBarangMasuk;
        this.namaBarangMasuk = namaBarangMasuk;
        this.waktuBarangMasuk = waktuBarangMasuk;
        this.stokBarangMasuk = stokBarangMasuk;
    }

    public String getTipeBarangMasuk() {
        return tipeBarangMasuk;
    }

    public void setTipeBarangMasuk(String tipeBarangMasuk) {
        this.tipeBarangMasuk = tipeBarangMasuk;
    }

    public String getArtikelBarangMasuk() {
        return artikelBarangMasuk;
    }

    public void setArtikelBarangMasuk(String artikelBarangMasuk) {
        this.artikelBarangMasuk = artikelBarangMasuk;
    }

    public String getNamaBarangMasuk() {
        return namaBarangMasuk;
    }

    public void setNamaBarangMasuk(String namaBarangMasuk) {
        this.namaBarangMasuk = namaBarangMasuk;
    }

    public String getWaktuBarangMasuk() {
        return waktuBarangMasuk;
    }

    public void setWaktuBarangMasuk(String waktuBarangMasuk) {
        this.waktuBarangMasuk = waktuBarangMasuk;
    }

    public String getStokBarangMasuk() {
        return stokBarangMasuk;
    }

    public void setStokBarangMasuk(String stokBarangMasuk) {
        this.stokBarangMasuk = stokBarangMasuk;
    }
}
