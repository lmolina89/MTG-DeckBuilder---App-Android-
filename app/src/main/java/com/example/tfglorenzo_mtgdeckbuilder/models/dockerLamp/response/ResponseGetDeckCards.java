package com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response;

import com.example.proyectoconmenu.models.userData.Card;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetDeckCards {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("cards")
    @Expose
    private List<Card> cards;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
