package com.example.posrudyproject.retrofit;

import com.example.posrudyproject.ui.rekapKas.model.KasKeluarItem;
import com.example.posrudyproject.ui.rekapKas.model.KasMasukItem;
import com.example.posrudyproject.ui.rekapKas.model.TutupKasirItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.Map;

public interface RekapKasEndpoint {

    @POST("/rekapKas/addKasMasuk")
    Call<Map> saveKasMasuk(@Header("AUTHORIZATION") String token, @Body KasMasukItem kasMasukItem);

    @POST("/rekapKas/addKasKeluar")
    Call<Map> saveKasKeluar(@Header("AUTHORIZATION") String token, @Body KasKeluarItem kasKeluarItem);

    @POST("/rekapKas/tutupKasir")
    Call<Map> tutupKasir(@Header("AUTHORIZATION") String token, @Body TutupKasirItem tutupKasirItem);

    @GET("/rekapKas/allKas")
    Call<Map> allKas(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);

    @GET("/rekapKas/chart")
    Call<Map> allChart(@Header("AUTHORIZATION") String token, @Query("id_store") int id_store);
}
