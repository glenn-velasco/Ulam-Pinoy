package com.pat.ulampinoy.RetroProfit;

import com.pat.ulampinoy.Dish.Dish;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("foods")
    Call<List<Dish>> getAllDishes();

}
