package com.example.posrudyproject.ui.produk.model;

public class ProdukItem {
    private int imProduk;
    private String tipeProduk;
    private String artikelProduk;
    private String namaProduk;
    private String stokProduk;

    public ProdukItem(int imProduk, String tipeProduk, String artikelProduk, String namaProduk, String stokProduk) {
        this.imProduk = imProduk;
        this.tipeProduk = tipeProduk;
        this.artikelProduk = artikelProduk;
        this.namaProduk = namaProduk;
        this.stokProduk = stokProduk;
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

    public String getStokProduk() {
        return stokProduk;
    }

    public void setStokProduk(String stokProduk) {
        this.stokProduk = stokProduk;
    }
}
