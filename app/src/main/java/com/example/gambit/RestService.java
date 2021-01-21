package com.example.gambit;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestService {
    @GET("category/39?page=1")
    Call<List<Foods>> getFoods();
}
