package com.example.styleomega.Interfaces;

import com.example.styleomega.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL="http://10.3.4.193:8084/WebApi/";
    @GET("products")
    Call<List<Product>> getProducts();
}
