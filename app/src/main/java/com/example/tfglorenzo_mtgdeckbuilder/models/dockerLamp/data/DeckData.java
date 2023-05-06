package com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeckData {
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("deckImage")
    @Expose
    private String deckImage;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeckImage() {
        return deckImage;
    }

    public void setDeckImage(String deckImage) {
        this.deckImage = deckImage;
    }
}
