package com.example.posrudyproject.ui.pembayaran.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BankItem implements Serializable {
    @SerializedName("image")
    private String logoBank;

    @SerializedName("bank_name")
    private String namaBank;

    @SerializedName("acc_number")
    private String noRekening;

    public BankItem(String logoBank, String namaBank, String noRekening) {
        this.logoBank = logoBank;
        this.namaBank = namaBank;
        this.noRekening = noRekening;
    }

    public String getLogoBank() {
        return logoBank;
    }

    public void setLogoBank(String logoBank) {
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
