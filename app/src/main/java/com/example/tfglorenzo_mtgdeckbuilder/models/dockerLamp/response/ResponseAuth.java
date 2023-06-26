package com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAuth {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("token")
    @Expose
    @Nullable
    private String token;
    @SerializedName("user_nick")
    @Expose
    @Nullable
    private String user_nick;
    @SerializedName("admin")
    @Expose
    @Nullable
    private String admin;
    @SerializedName("details")
    @Expose
    @Nullable
    private String details;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}