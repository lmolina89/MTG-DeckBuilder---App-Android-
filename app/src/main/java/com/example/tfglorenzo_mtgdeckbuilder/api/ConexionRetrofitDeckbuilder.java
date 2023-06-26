package com.example.tfglorenzo_mtgdeckbuilder.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConexionRetrofitDeckbuilder {
//        private final String URL = "http://mtgdeckbuilderapi.redirectme.net:80/mtg-deckbuilder/endp/";
    private static final String URL = "http://10.0.2.2:80/mtg-deckbuilder/endp/";
    private Retrofit retrofit;
    private DockerLampApi dockerLampApi;

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



