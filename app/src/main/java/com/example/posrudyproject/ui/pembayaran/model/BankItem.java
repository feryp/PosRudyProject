package com.example.posrudyproject.ui.pembayaran.model;

public class BankItem {
    private int logoBank;
    private String namaBank;
    private String noRekening;

    public BankItem(int logoBank, String namaBank, String noRekening) {
        this.logoBank = logoBank;
        this.namaBank = namaBank;
        this.noRekening = noRekening;
    }

    public int getLogoBank() {
        return logoBank;
    }

    public void setLogoBank(int logoBank) {
        this.logoBank = logoBank;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }
}
