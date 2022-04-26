package com.example.posrudyproject.retrofit;

import com.example.posrudyproject.ui.pelanggan.model.Pelanggan;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

public interface PelangganEndpoint {
    @GET("/pelanggan/all")
    Call<List<Pelanggan>> getAll(@Header("AUTHORIZATION") String token);

    @GET("/pelanggan/search")
    Call<List<Pelanggan>> search(@Header("AUTHORIZATION") String token, @Query("keyword") String keyword);

    @POST("/pelanggan/addMobile")
    Call<Map> savePelanggan(@Header("AUTHORIZATION") String token, @Body Pelanggan pelanggan);

    @POST(value = "/pelanggan/updateMobile")
    Call<Map> update(@Header("AUTHORIZATION") String token, @Body Pelanggan pelanggan);

    @GET("/pelanggan/totalPoin")
    Call<Double> totalPoin(@Header("AUTHORIZATION") String token, @Query("nama_pelanggan") String nama_pelanggan, @Query("no_hp_pelanggan") String no_hp_pelanggan);

    @GET("/pelanggan/delete")
    Call<String> deletePelanggan(@Path("id") Long id);
}
