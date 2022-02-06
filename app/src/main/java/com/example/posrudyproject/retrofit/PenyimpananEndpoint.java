package com.example.posrudyproject.retrofit;

import com.example.posrudyproject.ui.penyimpanan.model.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

public interface PenyimpananEndpoint {
    @GET("/penyimpananMobile/stockStore")
    Call<Double> totalStockStore(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/penyimpananMobile/stockStoreMasukAndKeluar")
    Call<Map> totalQtyMasukAndKeluar(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/penyimpananMobile/availStockStore")
    Call<List<ProdukTersediaItem>> stockAvailPerStore(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/penyimpananMobile/stockMovement")
    Call<Map> getAllPerStore(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/penyimpananMobile/riwayatPemindahan")
    Call<List<BarangPindahItem>>allPindah(@Header("AUTHORIZATION") String token, @Query("pengiriman_code") String pengiriman_code);

    @GET("/penyimpananMobile/searchBarangMasuk")
    Call<Map> searchBarangMasuk(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("keyword") String keyword);

    @GET("/penyimpananMobile/searchBarangKeluar")
    Call<Map> searchBarangKeluar(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("keyword") String keyword);

    @GET("/penyimpananMobile/searchBarangPindah")
    Call<Map> searchBarangPindah(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("keyword") String keyword);
}
