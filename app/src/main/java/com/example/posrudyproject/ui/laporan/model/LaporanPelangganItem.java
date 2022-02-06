package com.example.posrudyproject.ui.laporan.model;

public class LaporanPelangganItem {

    private String namaPelanggan,
            noHpPelanggan,
            totKunjunganPelanggan,
            transaksiPelanggan;

    private boolean expanded;

    public LaporanPelangganItem(String namaPelanggan, String noHpPelanggan, String totKunjunganPelanggan, String transaksiPelanggan) {
        this.namaPelanggan = namaPelanggan;
        this.noHpPelanggan = noHpPelanggan;
        this.totKunjunganPelanggan = totKunjunganPelanggan;
        this.transaksiPelanggan = transaksiPelanggan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getNoHpPelanggan() {
        return noHpPelanggan;
    }

    public void setNoHpPelanggan(String noHpPelanggan) {
        this.noHpPelanggan = noHpPelanggan;
    }

    public String getTotKunjunganPelanggan() {
        return totKunjunganPelanggan;
    }

    public void setTotKunjunganPelanggan(String totKunjunganPelanggan) {
        this.totKunjunganPelanggan = totKunjunganPelanggan;
    }

    public String getTransaksiPelanggan() {
        return transaksiPelanggan;
    }

    public void setTransaksiPelanggan(String transaksiPelanggan) {
        this.transaksiPelanggan = transaksiPelanggan;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
