package com.example.posrudyproject.ui.penyimpanan.model;

public class BarangKeluarItem {
    private String tipeBarangKeluar;
    private String artikelBarangKeluar;
    private String namaBarangKeluar;
    private String waktuBarangKeluark;
    private String stokBarangKeluar;

    public BarangKeluarItem(String tipeBarangKeluar, String artikelBarangKeluar, String namaBarangKeluar, String waktuBarangKeluark, String stokBarangKeluar) {
        this.tipeBarangKeluar = tipeBarangKeluar;
        this.artikelBarangKeluar = artikelBarangKeluar;
        this.namaBarangKeluar = namaBarangKeluar;
        this.waktuBarangKeluark = waktuBarangKeluark;
        this.stokBarangKeluar = stokBarangKeluar;
    }

    public String getTipeBarangKeluar() {
        return tipeBarangKeluar;
    }

    public void setTipeBarangKeluar(String tipeBarangKeluar) {
        this.tipeBarangKeluar = tipeBarangKeluar;
    }

    public String getArtikelBarangKeluar() {
        return artikelBarangKeluar;
    }

    public void setArtikelBarangKeluar(String artikelBarangKeluar) {
        this.artikelBarangKeluar = artikelBarangKeluar;
    }

    public String getNamaBarangKeluar() {
        return namaBarangKeluar;
    }

    public void setNamaBarangKeluar(String namaBarangKeluar) {
        this.namaBarangKeluar = namaBarangKeluar;
    }

    public String getWaktuBarangKeluark() {
        return waktuBarangKeluark;
    }

    public void setWaktuBarangKeluark(String waktuBarangKeluark) {
        this.waktuBarangKeluark = waktuBarangKeluark;
    }

    public String getStokBarangKeluar() {
        return stokBarangKeluar;
    }

    public void setStokBarangKeluar(String stokBarangKeluar) {
        this.stokBarangKeluar = stokBarangKeluar;
    }
}
