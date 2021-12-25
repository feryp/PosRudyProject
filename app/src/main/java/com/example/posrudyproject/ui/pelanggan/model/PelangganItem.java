package com.example.posrudyproject.ui.pelanggan.model;

public class PelangganItem {
    private String namaPelanggan;
    private String nohpPelanggan;
    private String emailPelanggan;
    private String alamatPelanggan;
    private int totalKunjungan;
    private String kunjunganTerakhir;
    private String totalPoin;

    public PelangganItem(String namaPelanggan, String nohpPelanggan, String emailPelanggan, String alamatPelanggan, int totalKunjungan, String kunjunganTerakhir, String totalPoin) {
        this.namaPelanggan = namaPelanggan;
        this.nohpPelanggan = nohpPelanggan;
        this.emailPelanggan = emailPelanggan;
        this.alamatPelanggan = alamatPelanggan;
        this.totalKunjungan = totalKunjungan;
        this.kunjunganTerakhir = kunjunganTerakhir;
        this.totalPoin = totalPoin;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getNohpPelanggan() {
        return nohpPelanggan;
    }

    public void setNohpPelanggan(String nohpPelanggan) {
        this.nohpPelanggan = nohpPelanggan;
    }

    public String getEmailPelanggan() {
        return emailPelanggan;
    }

    public void setEmailPelanggan(String emailPelanggan) {
        this.emailPelanggan = emailPelanggan;
    }

    public String getAlamatPelanggan() {
        return alamatPelanggan;
    }

    public void setAlamatPelanggan(String alamatPelanggan) {
        this.alamatPelanggan = alamatPelanggan;
    }

    public int getTotalKunjungan() {
        return totalKunjungan;
    }

    public void setTotalKunjungan(int totalKunjungan) {
        this.totalKunjungan = totalKunjungan;
    }

    public String getKunjunganTerakhir() {
        return kunjunganTerakhir;
    }

    public void setKunjunganTerakhir(String kunjunganTerakhir) {
        this.kunjunganTerakhir = kunjunganTerakhir;
    }

    public String getTotalPoin() {
        return totalPoin;
    }

    public void setTotalPoin(String totalPoin) {
        this.totalPoin = totalPoin;
    }
}
