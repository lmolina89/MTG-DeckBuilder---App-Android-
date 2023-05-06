package com.example.tfglorenzo_mtgdeckbuilder.models.userData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deck {


    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("numCards")
    @Expose
    private int numCards;
    @SerializedName("userDeck")
    @Expose
    private String userDeck;
    @SerializedName("deckImage")
    @Expose
    private String deckImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumCards() {
        return numCards;
    }

    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }

    public String getUserDeck() {
        return userDeck;
    }



    public void setUserDeck(String userDeck) {
        this.userDeck = userDeck;
    }

    public String getDeckImage() {
        return deckImage;
    }

    public void setDeckImage(String deckImage) {
        this.deckImage = deckImage;
    }


}
