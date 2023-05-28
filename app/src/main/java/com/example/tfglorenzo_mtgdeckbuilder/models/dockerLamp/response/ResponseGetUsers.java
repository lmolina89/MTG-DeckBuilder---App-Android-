package com.example.tfglorenzo_mtgdeckbuilder.models.dockerLamp.response;

import com.example.tfglorenzo_mtgdeckbuilder.models.userData.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetUsers {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("usuarios")
    @Expose
    private List<User> usuarios;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<User> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<User> usuarios) {
        this.usuarios = usuarios;
    }
}
