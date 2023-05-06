package com.example.tfglorenzo_mtgdeckbuilder.models.singleCardInfo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prices {

    @SerializedName("usd")
    @Expose
    private String usd;
    @SerializedName("usd_foil")
    @Expose
    private String usdFoil;
    @SerializedName("usd_etched")
    @Expose
    private Object usdEtched;
    @SerializedName("eur")
    @Expose
    private String eur;
    @SerializedName("eur_foil")
    @Expose
    private String eurFoil;
    @SerializedName("tix")
    @Expose
    private String tix;

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getUsdFoil() {
        return usdFoil;
    }

    public void setUsdFoil(String usdFoil) {
        this.usdFoil = usdFoil;
    }

    public Object getUsdEtched() {
        return usdEtched;
    }

    public void setUsdEtched(Object usdEtched) {
        this.usdEtched = usdEtched;
    }

    public String getEur() {
        return eur;
    }

    public void setEur(String eur) {
        this.eur = eur;
    }

    public String getEurFoil() {
        return eurFoil;
    }

    public void setEurFoil(String eurFoil) {
        this.eurFoil = eurFoil;
    }

    public String getTix() {
        return tix;
    }

    public void setTix(String tix) {
        this.tix = tix;
    }

}
