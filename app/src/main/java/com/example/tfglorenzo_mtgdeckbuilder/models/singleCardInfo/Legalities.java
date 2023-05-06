package com.example.tfglorenzo_mtgdeckbuilder.models.singleCardInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Legalities {

    @SerializedName("standard")
    @Expose
    private String standard;
    @SerializedName("future")
    @Expose
    private String future;
    @SerializedName("historic")
    @Expose
    private String historic;
    @SerializedName("gladiator")
    @Expose
    private String gladiator;
    @SerializedName("pioneer")
    @Expose
    private String pioneer;
    @SerializedName("explorer")
    @Expose
    private String explorer;
    @SerializedName("modern")
    @Expose
    private String modern;
    @SerializedName("legacy")
    @Expose
    private String legacy;
    @SerializedName("pauper")
    @Expose
    private String pauper;
    @SerializedName("vintage")
    @Expose
    private String vintage;
    @SerializedName("penny")
    @Expose
    private String penny;
    @SerializedName("commander")
    @Expose
    private String commander;
    @SerializedName("brawl")
    @Expose
    private String brawl;
    @SerializedName("historicbrawl")
    @Expose
    private String historicbrawl;
    @SerializedName("alchemy")
    @Expose
    private String alchemy;
    @SerializedName("paupercommander")
    @Expose
    private String paupercommander;
    @SerializedName("duel")
    @Expose
    private String duel;
    @SerializedName("oldschool")
    @Expose
    private String oldschool;
    @SerializedName("premodern")
    @Expose
    private String premodern;

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getFuture() {
        return future;
    }

    public void setFuture(String future) {
        this.future = future;
    }

    public String getHistoric() {
        return historic;
    }

    public void setHistoric(String historic) {
        this.historic = historic;
    }

    public String getGladiator() {
        return gladiator;
    }

    public void setGladiator(String gladiator) {
        this.gladiator = gladiator;
    }

    public String getPioneer() {
        return pioneer;
    }

    public void setPioneer(String pioneer) {
        this.pioneer = pioneer;
    }

    public String getExplorer() {
        return explorer;
    }

    public void setExplorer(String explorer) {
        this.explorer = explorer;
    }

    public String getModern() {
        return modern;
    }

    public void setModern(String modern) {
        this.modern = modern;
    }

    public String getLegacy() {
        return legacy;
    }

    public void setLegacy(String legacy) {
        this.legacy = legacy;
    }

    public String getPauper() {
        return pauper;
    }

    public void setPauper(String pauper) {
        this.pauper = pauper;
    }

    public String getVintage() {
        return vintage;
    }

    public void setVintage(String vintage) {
        this.vintage = vintage;
    }

    public String getPenny() {
        return penny;
    }

    public void setPenny(String penny) {
        this.penny = penny;
    }

    public String getCommander() {
        return commander;
    }

    public void setCommander(String commander) {
        this.commander = commander;
    }

    public String getBrawl() {
        return brawl;
    }

    public void setBrawl(String brawl) {
        this.brawl = brawl;
    }

    public String getHistoricbrawl() {
        return historicbrawl;
    }

    public void setHistoricbrawl(String historicbrawl) {
        this.historicbrawl = historicbrawl;
    }

    public String getAlchemy() {
        return alchemy;
    }

    public void setAlchemy(String alchemy) {
        this.alchemy = alchemy;
    }

    public String getPaupercommander() {
        return paupercommander;
    }

    public void setPaupercommander(String paupercommander) {
        this.paupercommander = paupercommander;
    }

    public String getDuel() {
        return duel;
    }

    public void setDuel(String duel) {
        this.duel = duel;
    }

    public String getOldschool() {
        return oldschool;
    }

    public void setOldschool(String oldschool) {
        this.oldschool = oldschool;
    }

    public String getPremodern() {
        return premodern;
    }

    public void setPremodern(String premodern) {
        this.premodern = premodern;
    }

}
