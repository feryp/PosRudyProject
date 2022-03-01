package com.example.posrudyproject.ui.penjualan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TipeItem implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("type_name")
    private String namaTipe;

    public TipeItem(int id, String namaTipe) {
        this.id = id;
        this.namaTipe = namaTipe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaTipe() {

        return namaTipe;
    }

    public void setNamaTipe(String namaTipe) {

        this.namaTipe = namaTipe;
    }
}
