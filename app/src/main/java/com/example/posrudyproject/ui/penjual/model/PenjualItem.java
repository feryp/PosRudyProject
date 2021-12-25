package com.example.posrudyproject.ui.penjual.model;

public class PenjualItem {
    private int imPenjual;
    private String namaPenjual;
    private String jabatanPenjual;

    public PenjualItem(int imPenjual, String namaPenjual, String jabatanPenjual) {
        this.imPenjual = imPenjual;
        this.namaPenjual = namaPenjual;
        this.jabatanPenjual = jabatanPenjual;
    }

    public int getImPenjual() {
        return imPenjual;
    }

    public void setImPenjual(int imPenjual) {
        this.imPenjual = imPenjual;
    }

    public String getNamaPenjual() {
        return namaPenjual;
    }

    public void setNamaPenjual(String namaPenjual) {
        this.namaPenjual = namaPenjual;
    }

    public String getJabatanPenjual() {
        return jabatanPenjual;
    }

    public void setJabatanPenjual(String jabatanPenjual) {
        this.jabatanPenjual = jabatanPenjual;
    }
}
