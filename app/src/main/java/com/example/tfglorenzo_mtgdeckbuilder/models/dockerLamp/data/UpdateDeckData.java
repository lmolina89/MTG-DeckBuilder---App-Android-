package com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateDeckData {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("deckImage")
    @Expose
    private Object deckImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDeckImage() {
        return deckImage;
    }

    public void setDeckImage(Object deckImage) {
        this.deckImage = deckImage;
    }
}
