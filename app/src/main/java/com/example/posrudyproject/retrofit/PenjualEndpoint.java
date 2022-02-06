package com.example.posrudyproject.retrofit;

import com.example.posrudyproject.ui.penjual.model.PenjualItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

public interface PenjualEndpoint {
    @GET("/karyawan/allByStore")
    Call<List<PenjualItem>> getAllByIdStore(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/karyawan/searchForStore")
    Call<List<PenjualItem>> searchForStore(@Header("AUTHORIZATION") String token,@Query("keyword") String keyword, @Query("id_store") int id_store);

    @POST("/karyawan/pindahStore")
    Call<Map> pindahStore(@Header("AUTHORIZATION") String token,@Body PenjualItem penjualItem);

}
