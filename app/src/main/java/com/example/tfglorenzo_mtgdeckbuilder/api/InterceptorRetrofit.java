package com.example.tfglorenzo_mtgdeckbuilder.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//clase para instanciar retrofit que muestra el result en string por consola antes de que GSON lo convierta a objeto java
//para buscar errores en las respuestas de retrofit
public class InterceptorRetrofit {
    private static final String BASE_URL = "http://10.0.2.2:80/mtg-deckbuilder/endp/";
//    private static final String BASE_URL = "http://mtgdeckbuilderapi.redirectme.net:80/mtg-deckbuilder/endp/";

    public static Retrofit getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);

                // Obtener la respuesta como texto
                ResponseBody responseBody = response.body();
                String responseBodyString = responseBody.string();

                // Imprimir la respuesta en el log
                System.out.println("Respuesta de la API: " + responseBodyString);

                // Reconstruir la respuesta para que Retrofit pueda procesarla
                response = response.newBuilder()
                        .body(ResponseBody.create(responseBody.contentType(), responseBodyString))
                        .build();
                return response;
            }
        });

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

