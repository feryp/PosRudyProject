package com.example.posrudyproject.ui.notifikasi.model;

public class NotifikasiItem {

    private String judulNotif;
    private String pesanNotif;
    private String waktuNotif;

    public NotifikasiItem(String judulNotif, String pesanNotif, String waktuNotif) {
        this.judulNotif = judulNotif;
        this.pesanNotif = pesanNotif;
        this.waktuNotif = waktuNotif;
    }

    public String getJudulNotif() {
        return judulNotif;
    }

    public void setJudulNotif(String judulNotif) {
        this.judulNotif = judulNotif;
    }

    public String getPesanNotif() {
        return pesanNotif;
    }

    public void setPesanNotif(String pesanNotif) {
        this.pesanNotif = pesanNotif;
    }

    public String getWaktuNotif() {
        return waktuNotif;
    }

    public void setWaktuNotif(String waktuNotif) {
        this.waktuNotif = waktuNotif;
    }
}
