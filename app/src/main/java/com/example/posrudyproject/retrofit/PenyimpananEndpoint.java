package com.example.posrudyproject.retrofit;

import com.example.posrudyproject.ui.penjualan.model.TipeItem;
import com.example.posrudyproject.ui.penyimpanan.model.*;
import com.example.posrudyproject.ui.produk.model.ProdukItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PenyimpananEndpoint {
    @GET("/penyimpananMobile/stockStore")
    Call<Double> totalStockStore(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/penyimpananMobile/stockStoreMasukAndKeluar")
    Call<Map> totalQtyMasukAndKeluar(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/penyimpananMobile/availStockStore")
    Call<List<ProdukTersediaItem>> stockAvailPerStore(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/penyimpananMobile/stockAllItemPerStore")
    Call<List<ProdukTersediaItem>> stockAllItemPerStore(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/penyimpananMobile/stockMovement")
    Call<Map> getAllPerStore(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/penyimpananMobile/notifikasi")
    Call<Map> notifikasi(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/penyimpananMobile/riwayatPemindahan")
    Call<List<BarangPindahItem>>allPindah(@Header("AUTHORIZATION") String token, @Query("pengiriman_code") String pengiriman_code);

    @GET("/penyimpananMobile/searchBarangMasuk")
    Call<Map> searchBarangMasuk(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("keyword") String keyword);

    @GET("/penyimpananMobile/searchBarangKeluar")
    Call<Map> searchBarangKeluar(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("keyword") String keyword);

    @GET("/penyimpananMobile/searchBarangPindah")
    Call<Map> searchBarangPindah(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("keyword") String keyword);

    @GET("/penyimpananMobile/searchStockStoreByCategory")
    Call<List<ProdukTersediaItem>> searchByCategory(@Header("AUTHORIZATION") String token,@Query("id_store") int id_store, @Query("kategori") String kategori, @Query("keyword") String keyword);

    @GET("/penyimpananMobile/availStockStoreByCategory")
    Call<List<ProdukTersediaItem>> stockAvailPerStoreByCategory(@Header("AUTHORIZATION") String token,@Query("id_store") int id_store, @Query("kategori") String kategori);


    @GET("/penyimpananMobile/searchStockStoreByType")
    Call<List<ProdukTersediaItem>> searchByType(@Header("AUTHORIZATION") String token,@Query("id_store") int id_store, @Query("type") int type, @Query("keyword") String keyword);

    @GET("/penyimpananMobile/availStockStoreByType")
    Call<List<ProdukTersediaItem>> stockAvailPerStoreByType(@Header("AUTHORIZATION") String token,@Query("id_store") int id_store, @Query("type") int type);

    @GET("/master/tipe/all")
    Call<List<TipeItem>> getAllTipe(@Header("AUTHORIZATION") String token);

    @GET("/master/tipe/search")
    Call<List<TipeItem>> searchTipe(@Header("AUTHORIZATION") String token,@Query("keyword") String keyword);

    @GET("/penyimpananMobile/searchStockStore")
    Call<List<ProdukTersediaItem>> searchStockStore(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("keyword") String keyword);

    @GET("/penyimpananMobile/searchAllStock")
    Call<List<ProdukTersediaItem>> searchAllStock(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("keyword") String keyword);

    @POST("/pengirimanStore/add")
    Call<Map> saveProduct(@Header("AUTHORIZATION") String token, @Body PengirimanBarang pengirimanBarang);

    @GET("/master/product/all")
    Call<List<ProdukItem>> getAllProduct(@Header("AUTHORIZATION") String token);

    @GET("/master/product/search")
    Call<List<ProdukItem>> searchProduct(@Header("AUTHORIZATION") String token,@Query("keyword") String keyword);
}
