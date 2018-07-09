package com.example.mahmoudsamy.task.network;

import com.example.mahmoudsamy.task.model.CarsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CarsApiServices {
    @GET("/v2/carsonline")
    Call<CarsResponse> getCarsList();
}
