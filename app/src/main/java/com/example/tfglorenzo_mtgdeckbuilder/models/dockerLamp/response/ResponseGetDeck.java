package com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response;

import com.example.proyectoconmenu.models.userData.Deck;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetDeck {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("decks")
    @Expose
    private List<Deck> decks;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }
}
