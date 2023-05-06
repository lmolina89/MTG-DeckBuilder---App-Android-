package com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUpdateDeck {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("details")
    @Expose
    private String details;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
