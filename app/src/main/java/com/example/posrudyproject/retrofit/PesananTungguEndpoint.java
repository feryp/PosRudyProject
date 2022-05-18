package com.example.posrudyproject.retrofit;

import com.example.posrudyproject.ui.pesananTunggu.model.PesananTungguItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PesananTungguEndpoint {
    @GET("/pesananTunggu/all")
    Call<List<PesananTungguItem>> getAll(@Header("AUTHORIZATION") String token);

    @GET("/pesananTunggu/getItem")
    Call<PesananTungguItem> getPesanan(@Header("AUTHORIZATION") String token, @Query("no_pesanan") String no_pesanan);

    @POST("/pesananTunggu/add")
    Call<List<PesananTungguItem>> savePesananTunggu(@Header("AUTHORIZATION") String token, @Body PesananTungguItem pesananTungguItem);

    @GET("/pesananTunggu/deletePesanan")
    Call<Map> delete(@Header("AUTHORIZATION") String token, @Query("id") Long id);
}
