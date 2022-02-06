package com.example.posrudyproject.ui.penjual.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TokoItem implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("store_name")
    private String namaToko;

    public TokoItem(int id, String namaToko) {
        this.id = id;
        this.namaToko = namaToko;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getNamaToko() {

        return namaToko;
    }

    public void setNamaToko(String namaToko) {

        this.namaToko = namaToko;
    }
}
