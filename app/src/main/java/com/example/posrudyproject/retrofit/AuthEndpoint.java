package com.example.posrudyproject.retrofit;

import com.example.posrudyproject.ui.login.ResponseItem;
import com.example.posrudyproject.ui.login.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthEndpoint {
    @POST("/api/auth/login")
    Call<ResponseItem> login(@Body User user);
}
