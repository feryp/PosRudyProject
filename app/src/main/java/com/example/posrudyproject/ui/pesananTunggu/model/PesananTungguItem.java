package com.example.posrudyproject.ui.pesananTunggu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PesananTungguItem implements Serializable {
    @SerializedName("id")
    private Long id;

    @SerializedName("no_pesanan")
    private String noPesanan;

    @SerializedName("tanggal_pesanan")
    private String tglPesanan;

    @SerializedName("id_store")
    private int id_store;

    @SerializedName("lokasi_store")
    private String lokasi_store;

    @SerializedName("no_hp_pelanggan")
    private String no_hp_pelanggan;

    @SerializedName("nama_pelanggan")
    private String pelangganPesanan;

    @SerializedName("total")
    private String totalHargaPesanan;

    @SerializedName("ket_pesanan")
    private String ketPesanan;

    @SerializedName("barang_pesanan")
    private List<BarangPesananTungguItem> barangPesanan;

    public PesananTungguItem(Long id, String noPesanan, String tglPesanan, int id_store, String lokasi_store, String no_hp_pelanggan, String pelangganPesanan, String totalHargaPesanan, String ketPesanan, List<BarangPesananTungguItem> barangPesanan) {
        this.id = id;
        this.noPesanan = noPesanan;
        this.tglPesanan = tglPesanan;
        this.id_store = id_store;
        this.lokasi_store = lokasi_store;
        this.no_hp_pelanggan = no_hp_pelanggan;
        this.pelangganPesanan = pelangganPesanan;
        this.totalHargaPesanan = totalHargaPesanan;
        this.ketPesanan = ketPesanan;
        this.barangPesanan = barangPesanan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoPesanan() {
        return noPesanan;
    }

    public void setNoPesanan(String noPesanan) {
        this.noPesanan = noPesanan;
    }

    public String getTglPesanan() {
        return tglPesanan;
    }

    public void setTglPesanan(String tglPesanan) {
        this.tglPesanan = tglPesanan;
    }

    public String getTotalHargaPesanan() {
        return totalHargaPesanan;
    }

    public void setTotalHargaPesanan(String totalHargaPesanan) {
        this.totalHargaPesanan = totalHargaPesanan;
    }

    public int getId_store() {
        return id_store;
    }

    public void setId_store(int id_store) {
        this.id_store = id_store;
    }

    public String getLokasi_store() {
        return lokasi_store;
    }

    public void setLokasi_store(String lokasi_store) {
        this.lokasi_store = lokasi_store;
    }

    public String getNo_hp_pelanggan() {
        return no_hp_pelanggan;
    }

    public void setNo_hp_pelanggan(String no_hp_pelanggan) {
        this.no_hp_pelanggan = no_hp_pelanggan;
    }

    public String getKetPesanan() {
        return ketPesanan;
    }

    public void setKetPesanan(String ketPesanan) {
        this.ketPesanan = ketPesanan;
    }

    public String getPelangganPesanan() {
        return pelangganPesanan;
    }

    public void setPelangganPesanan(String pelangganPesanan) {
        this.pelangganPesanan = pelangganPesanan;
    }

    public List<BarangPesananTungguItem> getBarangPesanan() {
        return barangPesanan;
    }

    public void setBarangPesanan(List<BarangPesananTungguItem> barangPesanan) {
        this.barangPesanan = barangPesanan;
    }
}
