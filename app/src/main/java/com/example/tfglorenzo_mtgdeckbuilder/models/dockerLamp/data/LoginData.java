package com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("passwd")
    @Expose
    private String passwd;

    public LoginData(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
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
}
