package com.example.tfglorenzo_mtgdeckbuilder.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConexionRetrofitDeckbuilder {
    private final String URL = "http://mtgdeckbuilder.redirectme.net:80/api-users/endp/";
    private static ConexionRetrofitDeckbuilder instance;
    private Retrofit retrofit;
    private DockerLampApi dockerLampApi;
    private ScryfallApi scryfallApi;

    public ConexionRetrofitDeckbuilder() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dockerLampApi = retrofit.create(DockerLampApi.class);
    }


    public DockerLampApi getDockerLampApi() {
        return dockerLampApi;
    }
}



