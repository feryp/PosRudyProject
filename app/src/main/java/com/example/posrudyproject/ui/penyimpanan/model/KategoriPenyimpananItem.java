package com.example.posrudyproject.ui.penyimpanan.model;

public class KategoriPenyimpananItem {

    private int idKategoriPenyimpanan;
    private String jmlBarangPenyimpanan;
    private String katBarangPenyimpanan;

    public KategoriPenyimpananItem(int idKategoriPenyimpanan, String jmlBarangPenyimpanan, String katBarangPenyimpanan) {
        this.idKategoriPenyimpanan = idKategoriPenyimpanan;
        this.jmlBarangPenyimpanan = jmlBarangPenyimpanan;
        this.katBarangPenyimpanan = katBarangPenyimpanan;
    }

    public int getIdKategoriPenyimpanan() {
        return idKategoriPenyimpanan;
    }

    public void setIdKategoriPenyimpanan(int idKategoriPenyimpanan) {
        this.idKategoriPenyimpanan = idKategoriPenyimpanan;
    }

    public String getJmlBarangPenyimpanan() {
        return jmlBarangPenyimpanan;
    }

    public void setJmlBarangPenyimpanan(String jmlBarangPenyimpanan) {
        this.jmlBarangPenyimpanan = jmlBarangPenyimpanan;
    }

    public String getKatBarangPenyimpanan() {
        return katBarangPenyimpanan;
    }

    public void setKatBarangPenyimpanan(String katBarangPenyimpanan) {
        this.katBarangPenyimpanan = katBarangPenyimpanan;
    }
}
