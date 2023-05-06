package com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterData {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("passwd")
    @Expose
    private String passwd;
    @SerializedName("nick")
    @Expose
    private String nick;
    @SerializedName("imageUri")
    @Expose
    private String imageUri;
    @SerializedName("deckList")
    @Expose
    private String deckList;

    public RegisterData(String email, String passwd, String nick, String imageUri, String deckList) {
        this.email = email;
        this.passwd = passwd;
        this.nick = nick;
        this.imageUri = imageUri;
        this.deckList = deckList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getDeckList() {
        return deckList;
    }

    public void setDeckList(String deckList) {
        this.deckList = deckList;
    }
}
