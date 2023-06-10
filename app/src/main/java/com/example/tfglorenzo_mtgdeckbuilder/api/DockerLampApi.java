package com.example.tfglorenzo_mtgdeckbuilder.api;

import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data.DeckData;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data.InsertCardData;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data.LoginData;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data.RegisterData;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data.UpdateDeckData;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data.UpdateNumCardsData;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseAuth;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseDeleteCard;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseDeleteDeck;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseGetDeck;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseGetDeckCards;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseGetUsers;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseInsertCard;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseNewDeck;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseRegister;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseUpdateDeck;
import com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response.ResponseUpdateNumCards;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface DockerLampApi {
    //      user
    @GET("user")
    Call<ResponseGetUsers> getUserList(@Header("api-key") String token);

    @POST("auth")
    Call<ResponseAuth> userAuth(@Body LoginData loginData);

    @POST("register")
    Call<ResponseRegister> userRegister(@Body RegisterData registerData);

    //      deck
    @GET("deck")
    Call<ResponseGetDeck> getUserDeckList(@Header("api-key") String token);

    @POST("deck")
    Call<ResponseNewDeck> insertDeck(@Body DeckData deckData, @Header("api-key") String token);

    @DELETE("deck")
    Call<ResponseDeleteDeck> deleteDeck(@Header("api-key") String token, @Query("deck_id") int deckId);

    @PUT("deck")
    Call<ResponseUpdateDeck> updateDeck(@Body UpdateDeckData updateDeckData, @Header("api-key") String token, @Query("id") int deckId);

    //      card
    @GET("card")
    Call<ResponseGetDeckCards> getDeckCards(@Header("api-key") String token, @Query("deck_id") int deckId);

    @DELETE("card")
    Call<ResponseDeleteCard> deleteCard(@Query("card_id") String cardId, @Header("api-key") String token, @Query("deck_id") int deckId);

    @PUT("card")
    Call<ResponseUpdateNumCards> updateNumCards(@Query("deck_id") int deckId, @Header("api-key") String token, @Body UpdateNumCardsData updateNumCardsData);

    @POST("card")
    Call<ResponseInsertCard> insertCardOnDeck(@Query("deck_id") int deckId, @Header("api-key") String token, @Body InsertCardData insertCardData);
}


