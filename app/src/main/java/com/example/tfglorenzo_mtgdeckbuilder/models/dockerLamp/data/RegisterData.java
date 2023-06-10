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



    public RegisterData(String email, String passwd, String nick) {
        this.email = email;
        this.passwd = passwd;
        this.nick = nick;
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

}
