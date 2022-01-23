package com.example.posrudyproject.ui.laporan.model;

public class PenjualanPerProdukItem {
    private String namaProduk,
            penjualanKotorProduk,
            qtyProduk,
            artikelProduk,
            kategoriProduk,
            pajakProduk,
            totalPenjualanProduk;

    private boolean expanded;

    public PenjualanPerProdukItem(String namaProduk, String penjualanKotorProduk, String qtyProduk, String artikelProduk, String kategoriProduk, String pajakProduk, String totalPenjualanProduk) {
        this.namaProduk = namaProduk;
        this.penjualanKotorProduk = penjualanKotorProduk;
        this.qtyProduk = qtyProduk;
        this.artikelProduk = artikelProduk;
        this.kategoriProduk = kategoriProduk;
        this.pajakProduk = pajakProduk;
        this.totalPenjualanProduk = totalPenjualanProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getPenjualanKotorProduk() {
        return penjualanKotorProduk;
    }

    public void setPenjualanKotorProduk(String penjualanKotorProduk) {
        this.penjualanKotorProduk = penjualanKotorProduk;
    }

    public String getQtyProduk() {
        return qtyProduk;
    }

    public void setQtyProduk(String qtyProduk) {
        this.qtyProduk = qtyProduk;
    }

    public String getArtikelProduk() {
        return artikelProduk;
    }

    public void setArtikelProduk(String artikelProduk) {
        this.artikelProduk = artikelProduk;
    }

    public String getKategoriProduk() {
        return kategoriProduk;
    }

    public void setKategoriProduk(String kategoriProduk) {
        this.kategoriProduk = kategoriProduk;
    }

    public String getPajakProduk() {
        return pajakProduk;
    }

    public void setPajakProduk(String pajakProduk) {
        this.pajakProduk = pajakProduk;
    }

    public String getTotalPenjualanProduk() {
        return totalPenjualanProduk;
    }

    public void setTotalPenjualanProduk(String totalPenjualanProduk) {
        this.totalPenjualanProduk = totalPenjualanProduk;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
