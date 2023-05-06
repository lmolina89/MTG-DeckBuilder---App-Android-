package com.example.tfglorenzo_mtgdeckbuilder.api;

import com.example.tfglorenzo_mtgdeckbuilder.models.api.ResponseFromApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ScryfallApi {
    @GET("cards/search")
    Call<ResponseFromApi> searchCards(@Query("q") String query);
}
