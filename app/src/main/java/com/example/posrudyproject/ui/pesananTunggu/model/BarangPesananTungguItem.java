package com.example.posrudyproject.ui.pesananTunggu.model;

public class BarangPesananTungguItem {
    private int kuantitasBarang;
    private String namaBarang;

    public BarangPesananTungguItem(int kuantitasBarang, String namaBarang) {
        this.kuantitasBarang = kuantitasBarang;
        this.namaBarang = namaBarang;
    }

    public int getKuantitasBarang() {
        return kuantitasBarang;
    }

    public void setKuantitasBarang(int kuantitasBarang) {
        this.kuantitasBarang = kuantitasBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }
}
