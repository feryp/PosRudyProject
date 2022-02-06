package com.example.posrudyproject.retrofit;

import com.example.posrudyproject.ui.penjual.model.TokoItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

public interface TokoEndpoint {
    @GET("/master/store/all")
    Call<List<TokoItem>> getAll(@Header("AUTHORIZATION") String token);

    @GET("/master/store/search")
    Call<List<TokoItem>> search(@Header("AUTHORIZATION") String token, @Query("keyword") String keyword);

    @POST("/master/store/add")
    Call<Map> saveMaster_store(@Header("AUTHORIZATION") String token,@Body TokoItem tokoItem );

    @POST("/master/store/update")
    Call<Map> update(@Header("AUTHORIZATION") String token,@Body TokoItem tokoItem );
    @GET("/master/store/delete")
    Call<String> deleteTipe(@Header("AUTHORIZATION") String token,@Part("id") Long id);
}
