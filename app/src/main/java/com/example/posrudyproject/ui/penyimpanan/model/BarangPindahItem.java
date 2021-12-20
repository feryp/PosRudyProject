package com.example.posrudyproject.ui.penyimpanan.model;

public class BarangPindahItem {
    private String noDocBarang;
    private String waktuBarangPindah;
    private String jumlahBarangPindah;

    public BarangPindahItem(String noDocBarang, String waktuBarangPindah, String jumlahBarangPindah) {
        this.noDocBarang = noDocBarang;
        this.waktuBarangPindah = waktuBarangPindah;
        this.jumlahBarangPindah = jumlahBarangPindah;
    }

    public String getNoDocBarang() {
        return noDocBarang;
    }

    public void setNoDocBarang(String noDocBarang) {
        this.noDocBarang = noDocBarang;
    }

    public String getWaktuBarangPindah() {
        return waktuBarangPindah;
    }

    public void setWaktuBarangPindah(String waktuBarangPindah) {
        this.waktuBarangPindah = waktuBarangPindah;
    }

    public String getJumlahBarangPindah() {
        return jumlahBarangPindah;
    }

    public void setJumlahBarangPindah(String jumlahBarangPindah) {
        this.jumlahBarangPindah = jumlahBarangPindah;
    }
}
