package com.example.posrudyproject.ui.akun.model;

public class PenukaranBarangItem {
    private boolean isChecked;
    private String tipeBarang;
    private String artikelBarang;
    private String namaBarang;
    private String jumlahBarang;

    public PenukaranBarangItem(boolean isChecked, String tipeBarang, String artikelBarang, String namaBarang, String jumlahBarang) {
        this.isChecked = isChecked;
        this.tipeBarang = tipeBarang;
        this.artikelBarang = artikelBarang;
        this.namaBarang = namaBarang;
        this.jumlahBarang = jumlahBarang;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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

    public String getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(String jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }
}
