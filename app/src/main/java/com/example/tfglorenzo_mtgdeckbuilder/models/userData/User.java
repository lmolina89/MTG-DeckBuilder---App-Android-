package com.example.tfglorenzo_mtgdeckbuilder.models.userData;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    @Expose
    private String id;
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
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("deckList")
    @Expose
    private String deckList;

    public User(String nick, String email, String passwd, String imageUri) {
        this.email = email;
        this.passwd = passwd;
        this.nick = nick;
        this.imageUri = imageUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DeckList getDeckList() {
        DeckList list;
        Gson gson = new Gson();
        list = gson.fromJson(deckList, DeckList.class);
        return list;
    }

    public String getDecklist() {
        return deckList;
    }

    public void setDeckList(DeckList list) {
        Gson gson = new Gson();
        deckList = gson.toJson(list, DeckList.class);
    }
}
