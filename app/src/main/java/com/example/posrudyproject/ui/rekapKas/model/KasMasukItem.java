package com.example.posrudyproject.ui.rekapKas.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KasMasukItem implements Serializable {

    @SerializedName("nominalKasMasuk")
    private String nominalKasMasuk;

    @SerializedName("waktuMasuk")
    private String waktuKasMasuk;

    @SerializedName("id_store")
    private int idToko;

    @SerializedName("lokasi_store")
    private String namaToko;

    @SerializedName("id_karyawan")
    private int idPenjual;

    @SerializedName("nama_karyawan")
    private String PenjualKasMasuk;

    @SerializedName("catatan")
    private String catatanKasMasuk;

    public String getNominalKasMasuk() {
        return nominalKasMasuk;
    }

    public void setNominalKasMasuk(String nominalKasMasuk) {
        this.nominalKasMasuk = nominalKasMasuk;
    }

    public int getIdToko() {
        return idToko;
    }

    public void setIdToko(int idToko) {
        this.idToko = idToko;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public int getIdPenjual() {
        return idPenjual;
    }

    public void setIdPenjual(int idPenjual) {
        this.idPenjual = idPenjual;
    }

    public String getWaktuKasMasuk() {
        return waktuKasMasuk;
    }

    public void setWaktuKasMasuk(String waktuKasMasuk) {
        this.waktuKasMasuk = waktuKasMasuk;
    }

    public String getPenjualKasMasuk() {
        return PenjualKasMasuk;
    }

    public void setPenjualKasMasuk(String penjualKasMasuk) {
        PenjualKasMasuk = penjualKasMasuk;
    }

    public String getCatatanKasMasuk() {
        return catatanKasMasuk;
    }

    public void setCatatanKasMasuk(String catatanKasMasuk) {
        this.catatanKasMasuk = catatanKasMasuk;
    }
}
