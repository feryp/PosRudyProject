package com.example.posrudyproject.ui.keranjang.model;

public class KeranjangItem {
    private int imBarang;
    private String tipeBarang;
    private String artikelBarang;
    private String namaBarang;
    private String hargaBarang;
    private String jumlahBarang;
    private String totalHargaBarang;
    private String kuantitasBarang;

    public KeranjangItem(int imBarang, String tipeBarang, String artikelBarang, String namaBarang, String hargaBarang, String jumlahBarang, String totalHargaBarang, String kuantitasBarang) {
        this.imBarang = imBarang;
        this.tipeBarang = tipeBarang;
        this.artikelBarang = artikelBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
        this.jumlahBarang = jumlahBarang;
        this.totalHargaBarang = totalHargaBarang;
        this.kuantitasBarang = kuantitasBarang;
    }

    public int getImBarang() {
        return imBarang;
    }

    public void setImBarang(int imBarang) {
        this.imBarang = imBarang;
    }

    public String getTipeBarang() {
        return tipeBarang;
    }

    public void setTipeBarang(String tipeBarang) {
        this.tipeBarang = tipeBarang;
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
