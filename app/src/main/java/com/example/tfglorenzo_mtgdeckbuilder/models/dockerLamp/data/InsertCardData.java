package com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertCardData {
    @SerializedName("id")
    @Expose
    private String id;
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


    public InsertCardData(String id, String name, String manacost, String cmc, String atributes, String text, String artist, String expansion, String imageUri) {
        this.id = id;
        this.name = name;
        this.manacost = manacost;
        this.cmc = cmc;
        this.atributes = atributes;
        this.text = text;
        this.artist = artist;
        this.expansion = expansion;
        this.imageUri = imageUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManacost() {
        return manacost;
    }

    public void setManacost(String manacost) {
        this.manacost = manacost;
    }

    public String getCmc() {
        return cmc;
    }

    public void setCmc(String cmc) {
        this.cmc = cmc;
    }

    public String getAtributes() {
        return atributes;
    }

    public void setAtributes(String atributes) {
        this.atributes = atributes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getExpansion() {
        return expansion;
    }

    public void setExpansion(String expansion) {
        this.expansion = expansion;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
