package com.example.tfglorenzo_mtgdeckbuilder.models.singleCardInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PurchaseUris {

    @SerializedName("tcgplayer")
    @Expose
    private String tcgplayer;
    @SerializedName("cardmarket")
    @Expose
    private String cardmarket;
    @SerializedName("cardhoarder")
    @Expose
    private String cardhoarder;

    public String getTcgplayer() {
        return tcgplayer;
    }

    public void setTcgplayer(String tcgplayer) {
        this.tcgplayer = tcgplayer;
    }

    public String getCardmarket() {
        return cardmarket;
    }

    public void setCardmarket(String cardmarket) {
        this.cardmarket = cardmarket;
    }

    public String getCardhoarder() {
        return cardhoarder;
    }

    public void setCardhoarder(String cardhoarder) {
        this.cardhoarder = cardhoarder;
    }

}
