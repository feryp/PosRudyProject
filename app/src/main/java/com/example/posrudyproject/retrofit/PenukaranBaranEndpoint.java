package com.example.posrudyproject.retrofit;

import com.example.posrudyproject.ui.akun.model.BarangKembali;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PenukaranBaranEndpoint {
    @GET("/penukaranBarang/penukaran")
    Call<List<BarangKembali>> getAllPerStore(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/penukaranBarang/search")
    Call<List<BarangKembali>> searchPerStore(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store, @Query("keyword") String keyword);

    @POST("/penukaranBarang/add")
    Call<Map> savePenukaran(@Header("AUTHORIZATION") String token, @Body List<BarangKembali> barangKembali);
}
