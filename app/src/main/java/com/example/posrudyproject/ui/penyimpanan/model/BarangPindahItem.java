package com.example.posrudyproject.ui.penyimpanan.model;

public class BarangPindahItem {
    private int imBarang;
    private String tipeBarangPindah;
    private String artikelBarangPindah;
    private String namaBarangPindah;
    private String kuantitasBarangPindah;

    public BarangPindahItem(int imBarang, String tipeBarangPindah, String artikelBarangPindah, String namaBarangPindah, String kuantitasBarangPindah) {
        this.imBarang = imBarang;
        this.tipeBarangPindah = tipeBarangPindah;
        this.artikelBarangPindah = artikelBarangPindah;
        this.namaBarangPindah = namaBarangPindah;
        this.kuantitasBarangPindah = kuantitasBarangPindah;
    }

    public int getImBarang() {
        return imBarang;
    }

    public void setImBarang(int imBarang) {
        this.imBarang = imBarang;
    }

    public String getTipeBarangPindah() {
        return tipeBarangPindah;
    }

    public void setTipeBarangPindah(String tipeBarangPindah) {
        this.tipeBarangPindah = tipeBarangPindah;
    }

    public String getArtikelBarangPindah() {
        return artikelBarangPindah;
    }

    public void setArtikelBarangPindah(String artikelBarangPindah) {
        this.artikelBarangPindah = artikelBarangPindah;
    }

    public String getNamaBarangPindah() {
        return namaBarangPindah;
    }

    public void setNamaBarangPindah(String namaBarangPindah) {
        this.namaBarangPindah = namaBarangPindah;
    }

    public String getKuantitasBarangPindah() {
        return kuantitasBarangPindah;
    }

    public void setKuantitasBarangPindah(String kuantitasBarangPindah) {
        this.kuantitasBarangPindah = kuantitasBarangPindah;
    }
}
