package com.example.posrudyproject.retrofit;

import com.example.posrudyproject.ui.penjualan.model.KategoriItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KategoriEndpoint {
    @GET("/master/kategori/all")
    Call<List<KategoriItem>> getAll(@Header("AUTHORIZATION") String token);

    @GET("/master/kategori/search")
    Call<List<KategoriItem>> search(@Header("AUTHORIZATION") String token, @Query("keyword") String keyword);
}
