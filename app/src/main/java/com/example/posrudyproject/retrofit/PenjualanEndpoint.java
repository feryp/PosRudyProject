package com.example.posrudyproject.retrofit;

import com.example.posrudyproject.ui.penjualan.model.PenjualanItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PenjualanEndpoint {
    @GET("/store/penjualan")
    Call<List<PenjualanItem>> getAll(@Header("AUTHORIZATION") String token);

    @GET("/store/search")
    Call<List<PenjualanItem>> search(@Header("AUTHORIZATION") String token, @Query("keyword") String keyword);

    @POST("/store/add")
    Call<PenjualanItem> savePenjualan(@Header("AUTHORIZATION") String token, @Body PenjualanItem penjualanItem);

    @POST("/store/update")
    Call<PenjualanItem> update(@Header("AUTHORIZATION") String token, @Body PenjualanItem penjualanItem);

    @POST("/store/delete")
    Call<PenjualanItem> deletePenjualan(@Header("AUTHORIZATION") String token, @Query("id") Long id);
}
