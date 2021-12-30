package com.example.posrudyproject.ui.akun.model;

public class TransaksiSelesaiItem {
    private String noInvoice;
    private String nominal;
    private String metodePembayaran;
    private String waktuTransaksil;

    public TransaksiSelesaiItem(String noInvoice, String nominal, String metodePembayaran, String waktuTransaksil) {
        this.noInvoice = noInvoice;
        this.nominal = nominal;
        this.metodePembayaran = metodePembayaran;
        this.waktuTransaksil = waktuTransaksil;
    }

    public String getNoInvoice() {
        return noInvoice;
    }

    public void setNoInvoice(String noInvoice) {
        this.noInvoice = noInvoice;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public String getWaktuTransaksil() {
        return waktuTransaksil;
    }

    public void setWaktuTransaksil(String waktuTransaksil) {
        this.waktuTransaksil = waktuTransaksil;
    }
}
