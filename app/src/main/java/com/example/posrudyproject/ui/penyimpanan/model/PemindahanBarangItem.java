package com.example.posrudyproject.ui.penyimpanan.model;

public class PemindahanBarangItem {
    private int imProduk;
    private String tipeProduk;
    private String artikelProduk;
    private String namaProduk;
    private String jumlahProduk;

    public PemindahanBarangItem(int imProduk, String tipeProduk, String artikelProduk, String namaProduk, String jumlahProduk) {
        this.imProduk = imProduk;
        this.tipeProduk = tipeProduk;
        this.artikelProduk = artikelProduk;
        this.namaProduk = namaProduk;
        this.jumlahProduk = jumlahProduk;
    }

    public int getImProduk() {
        return imProduk;
    }

    public void setImProduk(int imProduk) {
        this.imProduk = imProduk;
    }

    public String getTipeProduk() {
        return tipeProduk;
    }

    public void setTipeProduk(String tipeProduk) {
        this.tipeProduk = tipeProduk;
    }

    public String getArtikelProduk() {
        return artikelProduk;
    }

    public void setArtikelProduk(String artikelProduk) {
        this.artikelProduk = artikelProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getJumlahProduk() {
        return jumlahProduk;
    }

    public void setJumlahProduk(String jumlahProduk) {
        this.jumlahProduk = jumlahProduk;
    }
}
