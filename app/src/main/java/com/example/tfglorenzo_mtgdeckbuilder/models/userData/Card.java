package com.example.tfglorenzo_mtgdeckbuilder.models.userData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("manacost")
    @Expose
    private String manacost;
    @SerializedName("cmc")
    @Expose
    private String cmc;
    @SerializedName("atributes")
    @Expose
    private String atributes;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("expansion")
    @Expose
    private String expansion;
    @SerializedName("imageUri")
    @Expose
    private String imageUri;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("num_cards")
    @Expose
    private int numCards;

    public Card(String name, String manacost, String cmc, String atributes, String text, String artist, String expansion, String imageUri, String id) {
        this.name = name;
        this.manacost = manacost;
        this.cmc = cmc;
        this.atributes = atributes;
        this.text = text;
        this.artist = artist;
        this.expansion = expansion;
        this.imageUri = imageUri;
        this.id = id;
        this.numCards = 1;
    }

    public String getName() {
        return name;
    }

    public String getManacost() {
        return manacost;
    }

    public String getCmc() {
        return cmc;
    }


    public String getAtributes() {
        return atributes;
    }

    public String getText() {
        return text;
    }

    public String getArtist() {
        return artist;
    }

    public String getExpansion() {
        return expansion;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getId() {
        return id;
    }

    public int getNumCards() {
        return numCards;
    }

    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }

    @Override
    public String toString() {
        return "formatedCard{\n" +
                "\tname=" + name + ",\n" +
                "\tmanacost=" + manacost + ",\n" +
                "\tcmc=" + cmc + ",\n" +
                "\tatributes=" + atributes + ",\n" +
                "\ttext=" + text + ",\n" +
                "\tartist=" + artist + ",\n" +
                "\texpansion=" + expansion + ",\n" +
                "\timageUri" + imageUri + ",\n" +
                "\tid=" + id + "\n" +
                "\tnumCards=" + numCards + "\n"+
                '}' + "\n";
    }
}

